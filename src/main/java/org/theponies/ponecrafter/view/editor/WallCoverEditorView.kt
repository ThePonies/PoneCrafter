package org.theponies.ponecrafter.view.editor

import javafx.geometry.Pos
import javafx.scene.layout.Priority
import org.theponies.ponecrafter.Icons
import org.theponies.ponecrafter.controller.WallCoverEditorController
import org.theponies.ponecrafter.model.WallCover
import org.theponies.ponecrafter.model.WallCoverModel
import org.theponies.ponecrafter.util.NumberStringConverter
import org.theponies.ponecrafter.util.UuidUtil
import org.theponies.ponecrafter.view.MenuView
import tornadofx.*

class WallCoverEditorView(wallCover: WallCover = WallCover()) : BaseEditorView("Create a Wall Cover") {
    private val controller: WallCoverEditorController by inject()
    private val model = WallCoverModel(wallCover)

    override val root = form {
        padding = insets(20)
        fieldset {
            createHeader(this, name, Icons.wallpaper)
            field("Name") {
                textfield(model.name).validator {
                    if (it.isNullOrBlank()) error("The name field is required") else null
                }
                labelContainer.alignment = Pos.TOP_LEFT
            }
            field("Price") {
                textfield(model.price, NumberStringConverter()) {
                    filterInput { it.controlNewText.isInt() }
                    validator {
                        if (it.isNullOrBlank()) error("The price field is required") else null
                    }
                }
            }
            field("Description") {
                textarea(model.description) {
                    prefHeight = 112.0
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
            hbox {
                padding = insets(150, 0, 0, 0)
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
                        controller.chooseTextureDialog(model.item.getTypeName(), true, 128, 256).let {
                            model.image.value = it
                        }
                    }
                }
            }
            vbox {
                imageDataView(model.image, 128.0, 256.0) {
                    model.addValidator(this, model.image) {
                        if (model.image.value != null) success() else error()
                    }
                }
            }
        }
    }
}