package org.theponies.ponecrafter.view.editor

import javafx.geometry.Pos
import javafx.scene.layout.Priority
import org.theponies.ponecrafter.Icons
import org.theponies.ponecrafter.controller.RoofEditorController
import org.theponies.ponecrafter.model.RoofModel
import org.theponies.ponecrafter.util.UuidUtil
import org.theponies.ponecrafter.view.MenuView
import tornadofx.*

class RoofEditorView : BaseEditorView("Create a Roof") {
    private val controller: RoofEditorController by inject()
    private val model: RoofModel = RoofModel()

    override val root = form {
        padding = insets(20)
        fieldset {
            createHeader(this, name, Icons.roof)
            field("Name") {
                textfield(model.name).validator {
                    if (it.isNullOrBlank()) error("The name field is required") else null
                }
                labelContainer.alignment = Pos.TOP_LEFT
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
                alignment = Pos.BOTTOM_RIGHT
                padding = insets(0, 0, 90, 0)
                hgrow = Priority.ALWAYS
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
                        controller.chooseTextureDialog(model.item.getTypeName()).let { model.image.value = it }
                    }
                }
            }
            vbox {
                padding = insets(0, 30)
                alignment = Pos.BOTTOM_RIGHT
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