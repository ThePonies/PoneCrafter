package org.theponies.ponecrafter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.theponies.ponecrafter.SceneName;
import org.theponies.ponecrafter.exceptions.SavePackageException;
import org.theponies.ponecrafter.model.JsonModel;
import org.theponies.ponecrafter.model.SavePackage;
import org.theponies.ponecrafter.util.Alerts;
import org.theponies.ponecrafter.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class EditController extends BaseController {
    private static final Logger logger = Logger.getLogger(EditController.class.getName());
    private final String modelName;
    @FXML private Label guidLabel;
    private UUID guid;

    public EditController (String modelName) {
        this.modelName = modelName;
    }

    public void setup () {
        init("Edit " + modelName);
        regenerateGuid();
    }

    public void onSaveButton (ActionEvent actionEvent) {
        if (!validateInput()) {
            return;
        }
        String saveName = getInitialSaveName();
        if (saveName == null || saveName.isEmpty()) {
            saveName = "untitled";
        }
        File file = saveFileDialog(saveName);
        if (file != null) {
            if (file.exists() && !Alerts.showConfirmation("File already exists.",
                    "Are you sure you want to overwrite " + file.getName() + "?")) {
                return;
            }
            try {
                SavePackage savePackage = createSavePackage();
                savePackage.saveToZip(file.getPath());
                Alerts.showInfo("Success!", StringUtils.capitalizeFirst(modelName) + " " + file.getName() + " saved successfully.");
            } catch (SavePackageException e) {
                logger.log(Level.WARNING, "Failed to create save package.", e);
                Alerts.showError("Couldn't create save package.", e.getMessage());
            } catch (IOException e) {
                logger.log(Level.WARNING, "Failed to save package.", e);
                Alerts.showError("Couldn't save package.", e.getMessage());
            }
        }
    }

    public abstract SavePackage createSavePackage () throws SavePackageException;

    public abstract String getInitialSaveName ();

    protected File loadImageDialog () {
        return loadFileDialog("Image", new String[]{"png", "jpg", "gif"});
    }

    protected File loadFileDialog (String typeName, String[] extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load " + typeName);
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(typeName, extensions));
        return fileChooser.showOpenDialog(getStage());
    }

    protected File saveFileDialog (String name) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save " + modelName);
        fileChooser.setInitialFileName(name + ".pcc");
        return fileChooser.showOpenDialog(getStage());
    }

    protected abstract boolean validateInput ();

    protected void validationError (String message) {
        Alerts.showWarning("Invalid input", message);
    }

    protected abstract JsonModel createModelFromInput ();

    public UUID getGuid () {
        return guid;
    }

    public void onBackButton (ActionEvent actionEvent) {
        switchScene(SceneName.MENU);
    }

    public void onGenerateGuidButton (ActionEvent actionEvent) {
        regenerateGuid();
    }

    public void regenerateGuid () {
        this.guid = generateGuid();
        guidLabel.setText("GUID: " + guid);
    }

    private UUID generateGuid () {
        //Starting at 00000100, which is 16^10.
        final long min = 1099511627776L;
        //Up until 00010000, which is 16^12.
        final long max = 281474976710656L;
        long mostSignificant = ThreadLocalRandom.current().nextLong(min, max);
        long leastSignificant = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
        return new UUID(mostSignificant, leastSignificant);
    }
}
