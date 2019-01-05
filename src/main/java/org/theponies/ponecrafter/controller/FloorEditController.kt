package org.theponies.ponecrafter.controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.theponies.ponecrafter.AppConstants
import org.theponies.ponecrafter.exceptions.SavePackageException
import org.theponies.ponecrafter.model.Floor
import org.theponies.ponecrafter.model.JsonModel
import org.theponies.ponecrafter.model.SavePackage

import java.io.*
import java.util.logging.Logger


class FloorEditController : EditController("floor") {
    @FXML
    private lateinit var nameInput: TextField
    @FXML
    private lateinit var priceInput: TextField
    @FXML
    private lateinit var descriptionInput: TextArea
    @FXML
    private lateinit var floorImageView: ImageView

    override val initialSaveName: String
        get() = nameInput.text

    override fun validateInput(): Boolean {
        if (nameInput.text.isEmpty()) {
            validationError("Please enter a name.")
        } else if (descriptionInput.text.isEmpty()) {
            validationError("Please enter a description.")
        } else if (floorImageView.image == null) {
            validationError("Please select an image.")
        } else {
            try {
                val price = Integer.parseInt(priceInput.text)
                if (price >= 0) {
                    return true
                } else {
                    validationError("Price must not be negative.")
                }
            } catch (e: NumberFormatException) {
                validationError("Please enter a valid price (whole number).")
            }

        }
        return false
    }

    public override fun createModelFromInput(): JsonModel {
        return Floor(
            guid,
            nameInput.text,
            descriptionInput.text,
            Integer.parseInt(priceInput.text)
        )
    }

    fun onLoadImageButton(actionEvent: ActionEvent) {
        val file = loadImageDialog()
        val image = Image(file.toURI().toString(), 128.0, 128.0, false, true)
        floorImageView.image = image
    }

    @Throws(SavePackageException::class)
    override fun createSavePackage(): SavePackage {
        val savePackage = SavePackage()
        savePackage.addFile("properties.json", createModelFromInput().toJson().toByteArray(AppConstants.DEFAULT_CHARSET))
        savePackage.addImage("texture.png", floorImageView.image)
        return savePackage
    }


}
