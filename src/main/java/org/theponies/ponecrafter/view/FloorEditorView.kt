package org.theponies.ponecrafter.view

import javafx.geometry.Pos
import javafx.scene.layout.Priority
import org.theponies.ponecrafter.Icons
import org.theponies.ponecrafter.Styles
import org.theponies.ponecrafter.controller.FloorEditorController
import org.theponies.ponecrafter.model.FloorModel
import org.theponies.ponecrafter.util.IntStringConverter
import org.theponies.ponecrafter.util.UuidUtil
import tornadofx.*

class FloorEditorView : View("Create a Floor") {
    private val controller: FloorEditorController by inject()
    private val model: FloorModel = FloorModel()

    override val root = form {
        padding = insets(20)
        fieldset {
            hbox {
                svgpath(Icons.floor) {
                    fill = Styles.textColor
                }
                label("Create a Floor") {
                    addClass(Styles.windowTitle)
                    padding = insets(15, 0)
                }
                prefHeight = 80.0
            }
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
                    prefHeight = 240.0
                }
            }
        }
        hbox {
            vgrow = Priority.ALWAYS
            alignment = Pos.CENTER
            vbox(10) {
                padding = insets(20, 30)
                alignment = Pos.BOTTOM_LEFT
                button("Back") {
                    action {
                        replaceWith<MenuView>()
                    }
                }
                button("Save") {
                    enableWhen(model.valid)
                    action {
                        if (model.commit()) {
                            controller.saveDialog(model.item)
                        }
                    }
                }
            }
            hbox {
                padding = insets(0, 42)
                hgrow = Priority.ALWAYS
                alignment = Pos.TOP_RIGHT
                label(model.uuid) {
                    style {
                        fontSize = 12.px
                    }
                }
            }
            vbox(10) {
                padding = insets(20, 30)
                alignment = Pos.BOTTOM_RIGHT
                button("Generate UUID") {
                    prefWidth = 160.0
                    action {
                        model.uuid.value = UuidUtil.generateContentUuid()
                    }
                }
                button("Load Image") {
                    prefWidth = 160.0
                    action {
                        controller.chooseTextureDialog().let { model.image.value = it }
                    }
                }
            }
            vbox {
                imageview(model.image) {
                    prefWidth = 128.0
                    prefHeight = 128.0
                    model.addValidator(this, model.image) {
                        if (model.image.value != null) success() else error()
                    }
                }
            }
        }
    }
}