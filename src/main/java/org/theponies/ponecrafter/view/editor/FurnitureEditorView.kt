package org.theponies.ponecrafter.view.editor

import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import org.theponies.ponecrafter.Icons
import org.theponies.ponecrafter.controller.FurnitureEditorController
import org.theponies.ponecrafter.model.FurnitureModel
import org.theponies.ponecrafter.util.IntStringConverter
import org.theponies.ponecrafter.view.MenuView
import tornadofx.*

class FurnitureEditorView : TabEditorView("Create an Object", Icons.objects) {
    private val controller: FurnitureEditorController by inject()
    private val model: FurnitureModel = FurnitureModel()

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
            field("Name") {
                textfield(model.name).validator {
                    if (it.isNullOrBlank()) error("The name field is required") else null
                }
                labelContainer.alignment = Pos.TOP_LEFT
            }
            field("Price") {
                textfield(model.price, IntStringConverter()) {
                    filterInput { it.controlNewText.isInt() }
                    validator {
                        if (it.isNullOrBlank()) error("The price field is required") else null
                    }
                }
            }
            field("Description") {
                textarea(model.description) {
                    prefHeight = 200.0
                }
            }
        },
        "Model" to VBox().fieldset {
            label("This will be the model tab")
        },
        "Settings" to VBox().fieldset {
            label("This will be the settings tab")
        }
    )
}