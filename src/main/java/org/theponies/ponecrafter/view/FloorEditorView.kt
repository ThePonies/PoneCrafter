package org.theponies.ponecrafter.view

import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.image.Image
import javafx.stage.FileChooser
import org.theponies.ponecrafter.Icons
import org.theponies.ponecrafter.Styles
import org.theponies.ponecrafter.controller.FloorEditorController
import org.theponies.ponecrafter.model.FloorModel
import org.theponies.ponecrafter.util.IntStringConverter
import org.theponies.ponecrafter.util.UuidUtil
import tornadofx.*
import java.io.File

class FloorEditorView : View("Floor Editor") {
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
        imageview(model.image) {
            model.addValidator(this, model.image) {
                if (model.image.value != null) success() else error()
            }
        }
        button("Generate UUID") {
            action {
                model.uuid.value = UuidUtil.generateContentUuid()
            }
        }
        label(model.uuid)
        button("Load Image") {
            action {
                val imageFile: File? = chooseFile(
                    "Select floor texture...",
                    arrayOf(FileChooser.ExtensionFilter("Image (png, jpeg, gif)", "*.png", "*.jpg", "*.jpeg", "*.gif"))
                ).firstOrNull()
                if (imageFile != null) {
                    val image = Image(imageFile.toURI().toString(), 128.0, 128.0, false, true)
                    if (image.isError) {
                        alert(
                            Alert.AlertType.ERROR,
                            "Can't load image",
                            "The image could not be loaded."
                        )
                    } else {
                        model.image.value = image
                    }
                }
            }
        }
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
}