package org.theponies.ponecrafter.view.editor

import javafx.beans.property.Property
import javafx.event.EventTarget
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.SelectionMode
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import org.theponies.ponecrafter.Icons
import org.theponies.ponecrafter.Styles
import org.theponies.ponecrafter.controller.FurnitureEditorController
import org.theponies.ponecrafter.model.*
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
                hbox(5) {
                    button("Save") {
                        enableWhen(model.valid)
                        action {
                            if (model.commit()) {
                                controller.saveDialog(model.item, false)
                            }
                        }
                    }
                    button("Save raw") {
                        enableWhen(model.valid)
                        action {
                            if (model.commit()) {
                                controller.saveDialog(model.item, true)
                            }
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
                    button("Add model files") {
                        prefWidth = 160.0
                        action {
                            val meshFiles = chooseFile(
                                "Add model file(s)...",
                                arrayOf(
                                    FileChooser.ExtensionFilter("GLTF and textures", "*.gltf", "*.glb", "*.bin",
                                        "*.png", "*.jpg", "*.jpeg", "*.png"),
                                    FileChooser.ExtensionFilter("All files", "*")
                                ),
                                FileChooserMode.Multi
                            )
                            model.meshFiles.addAll(meshFiles.map { MeshFile(it) })
                        }
                    }
                    model.addValidator(this, model.meshFiles) {
                        if (model.meshFiles.isEmpty())
                            error("Add at least one model file.")
                        else if (model.meshFiles.distinctBy { it.fileName }.count() < model.meshFiles.count())
                            error("Model files list should not contain duplicate filenames.")
                        else if (model.meshFiles.count {
                                it.fileName.endsWith(".gltf", true) || it.fileName.endsWith(".glb", true)
                            } != 1)
                            error("Furniture items should contain exactly one GLTF file.")
                        else
                            success()
                    }
                    button("Edit tiles") {
                        prefWidth = 160.0
                        action {
                            TilesEditorView(model.occupiedTiles).openWindow(resizable = false)
                        }
                    }
                }
                vbox {
                    val fileList = listview(model.meshFiles) {
                        selectionModel.selectionMode = SelectionMode.MULTIPLE
                    }
                    button("Remove") {
                        enableWhen(fileList.selectionModel.selectedItemProperty().isNotNull)
                        action {
                            model.meshFiles.removeAll(fileList.selectionModel.selectedItems)
                        }
                    }
                }
            }
        },
        "Settings" to VBox().fieldset {
            hbox {
                vbox {
                    hgrow = Priority.ALWAYS
                    field("Placement type") {
                        combobox(model.placementType, PlacementType.values().toList())
                    }
                    checkbox("Pickupable", model.pickupable)
                    checkbox("Sellable", model.sellable)
                }
                vbox(10) {
                    vgrow = Priority.ALWAYS
                    label("Tags")
                    val tagsView = tableview(model.tags) {
                        column("Name", Tag::name).makeEditable()
                        column("Value", Tag::value).makeEditable()
                    }
                    // TODO: Add validation on tag editing.
                    /*model.addValidator(this, model.tags) {
                        if (model.tags.distinctBy { it.name }.count() < model.tags.count())
                            error("Tag names should be unique.")
                        else
                            success()
                    }*/
                    hbox {
                        vbox {
                            hgrow = Priority.ALWAYS
                            button("Add") {
                                action {
                                    model.tags.add(Tag())
                                }
                            }
                        }
                        vbox(0, Pos.BOTTOM_RIGHT) {
                            button("Remove") {
                                enableWhen(tagsView.selectionModel.selectedItemProperty().isNotNull)
                                action {
                                    model.tags.removeAll(tagsView.selectionModel.selectedItems)
                                }
                            }
                        }
                    }
                }
            }
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