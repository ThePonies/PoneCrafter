package org.theponies.ponecrafter.view.editor

import javafx.beans.property.Property
import javafx.event.EventTarget
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import org.theponies.ponecrafter.Icons
import org.theponies.ponecrafter.Styles
import org.theponies.ponecrafter.component.ModelViewer
import org.theponies.ponecrafter.controller.FurnitureEditorController
import org.theponies.ponecrafter.model.CatalogCategory
import org.theponies.ponecrafter.model.Furniture
import org.theponies.ponecrafter.model.FurnitureModel
import org.theponies.ponecrafter.model.PlacementType
import org.theponies.ponecrafter.util.NumberStringConverter
import org.theponies.ponecrafter.view.MenuView
import tornadofx.*

class FurnitureEditorView(furniture: Furniture = Furniture()) : TabEditorView("Create an Object", Icons.objects) {
    private val controller: FurnitureEditorController by inject()
    private val model: FurnitureModel = FurnitureModel(furniture)

    override val root = createRoot()

    override fun addFooter(parent: Parent) = with(parent) {
        hbox(0, Pos.BOTTOM_LEFT) {
            vgrow = Priority.ALWAYS
            vbox(0, Pos.BOTTOM_LEFT) {
                hgrow = Priority.ALWAYS
                button("Back") {
                    action {
                        replaceWith<MenuView>()
                    }
                }
            }
            vbox(0, Pos.BOTTOM_RIGHT) {
                button("Save") {
                    enableWhen(model.valid)
                    action {
                        if (model.commit()) {
                            controller.saveDialog(model.item)
                        }
                    }
                }
            }
        }
    }

    override fun createTabs(): Map<String, Parent> = mapOf(
        "Catalog" to VBox().fieldset {
            padding = insets(0)
            field("Name") {
                textfield(model.name).validator {
                    if (it.isNullOrBlank()) error("The name field is required") else null
                }
                labelContainer.alignment = Pos.TOP_LEFT
            }
            numberField(model.price)
            field("Category") {
                combobox(model.category, CatalogCategory.values().toList())
            }
            field("Description") {
                textarea(model.description) {
                    prefHeight = 160.0
                }
            }
            hbox {
                field("Stats") {
                    hgrow = Priority.ALWAYS
                    fieldset("Needs") {
                        addClass(Styles.editorFieldsSection)
                        prefWidth = 330.0
                        prefHeight = 120.0
                        hbox {
                            vbox {
                                padding = insets(0, 20, 0, 0)
                                statNumberField(model.hungerStat)
                                statNumberField(model.energyStat)
                                statNumberField(model.comfortStat)
                                statNumberField(model.funStat)
                            }
                            vbox {
                                padding = insets(0, 20, 0, 0)
                                statNumberField(model.hygieneStat)
                                statNumberField(model.socialStat)
                                statNumberField(model.bladderStat)
                                statNumberField(model.roomStat)
                            }
                        }
                    }
                    fieldset("Skills") {
                        addClass(Styles.editorFieldsSection)
                        prefWidth = 330.0
                        prefHeight = 120.0
                        hbox {
                            vbox {
                                padding = insets(0, 20, 0, 0)
                                statCheckbox(model.cookingStat)
                                statCheckbox(model.mechanicalStat)
                                statCheckbox(model.charismaStat)
                            }
                            vbox {
                                padding = insets(0, 20, 0, 0)
                                statCheckbox(model.bodyStat)
                                statCheckbox(model.logicStat)
                                statCheckbox(model.creativityStat)
                            }
                        }
                    }
                }
            }
        },
        "Model" to VBox().fieldset {
            hbox {
                vbox {
                    hgrow = Priority.ALWAYS
                    spacing = 10.0
                    button("Load model") {
                        prefWidth = 160.0
                        action {
                            controller.chooseMeshDialog(model.item.getTypeName())?.let {
                                model.meshData.value = it
                            }
                        }
                    }
                    model.addValidator(this, model.meshData) {
                        if (model.meshData.value != null) success() else error()
                    }
                    button("Edit tiles") {
                        prefWidth = 160.0
                        action {
                            TilesEditorView(model.occupiedTiles).openWindow(resizable = false)
                        }
                    }
                }
                vbox {
                    val modelViewer = ModelViewer(240, 240, model.meshData, model.texture, model.occupiedTiles) {
                        fill = Styles.textFieldColor
                        rotateY(-45)
                    }
                    add(modelViewer)
                    hbox {
                        alignment = Pos.CENTER
                        padding = insets(10)
                        button("<<") {
                            spacing = 10.0
                            action {
                                modelViewer.rotateY(-90)
                            }
                        }
                        button(">>") {
                            spacing = 10.0
                            action {
                                modelViewer.rotateY(90)
                            }
                        }
                    }
                }
            }
            hbox {
                vbox {
                    hgrow = Priority.ALWAYS
                    button("Load texture") {
                        prefWidth = 160.0
                        action {
                            controller.chooseTextureDialog(model.item.getTypeName(), false)?.let {
                                model.texture.value = it
                            }
                        }
                    }
                }
                vbox {
                    imageDataView(model.texture, 128.0, 128.0) {
                        model.addValidator(this, model.texture) {
                            if (model.texture.value != null) success() else error()
                        }
                    }
                }
            }
        },
        "Settings" to VBox().fieldset {
            field("Placement type") {
                combobox(model.placementType, PlacementType.values().toList())
            }
            checkbox("Pickupable", model.pickupable)
            checkbox("Sellable", model.sellable)
        }
    )
}

fun EventTarget.statNumberField(property: Property<Number>, name: String = property.name) = field(name.capitalize()) {
    padding = insets(0)
    textfield(property, NumberStringConverter()) {
        padding = insets(2, 0)
        filterInput {
            it.controlNewText.length <= 2 && it.controlNewText.isInt() && it.controlNewText.toInt() in 0..10
        }
        validator {
            if (it.isNullOrBlank()) error("The $name field is required") else null
        }
    }
}

fun EventTarget.statCheckbox(property: Property<Boolean>, name: String = property.name) = checkbox(name.capitalize(), property) {
    padding = insets(5, 2)
}