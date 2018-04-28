package org.theponies.ponecrafter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.theponies.ponecrafter.model.Floor;
import org.theponies.ponecrafter.model.JsonModel;
import org.theponies.ponecrafter.util.Alerts;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class FloorEditController extends EditController {
    @FXML private TextField nameInput;
    @FXML private TextField priceInput;
    @FXML private TextArea descriptionInput;
    @FXML private ImageView floorImageView;

    public FloorEditController () {
        super("floor");
    }

    @Override
    protected boolean validateInput () {
        if (nameInput.getText().isEmpty()) {
            validationError("Please enter a name.");
        } else if (descriptionInput.getText().isEmpty()) {
            validationError("Please enter a description.");
        } else if (floorImageView.getImage() == null) {
            validationError("Please select an image.");
        } else {
            try {
                int price = Integer.parseInt(priceInput.getText());
                if (price >= 0) {
                    return true;
                } else {
                    validationError("Price must not be negative.");
                }
            } catch (NumberFormatException e) {
                validationError("Please enter a valid price (whole number).");
            }
        }
        return false;
    }

    @Override
    public JsonModel createModelFromInput () {
        return new Floor(
                getGuid(),
                nameInput.getText(),
                descriptionInput.getText(),
                Integer.parseInt(priceInput.getText())
        );
    }

    public void onLoadImageButton (ActionEvent actionEvent) {
        File file = loadImageDialog();
        if (file != null) {
            Image image = new Image(file.toURI().toString(), 128, 128, false, true);
            floorImageView.setImage(image);
        }
    }
}
