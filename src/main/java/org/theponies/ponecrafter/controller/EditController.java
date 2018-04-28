package org.theponies.ponecrafter.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.theponies.ponecrafter.SceneName;
import org.theponies.ponecrafter.model.JsonModel;
import org.theponies.ponecrafter.util.Alerts;
import org.theponies.ponecrafter.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        //TODO: Replace with ZIP file logic (probably in implementation class)
        if (!validateInput()) {
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save " + modelName);
        File file = fileChooser.showOpenDialog(getStage());
        if (file != null) {
            byte[] bytes = createModelFromInput().toJson().getBytes(StandardCharsets.UTF_8);
            try {
                if (!Files.exists(file.toPath()) || Alerts.showConfirmation("Overwrite file?", "Are you sure you want to overwrite " + file.getName() + "?")) {
                    Files.write(file.toPath(), bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                    Alerts.showInfo(StringUtils.capitalizeFirst(modelName + " saved"),
                            StringUtils.capitalizeFirst(modelName + " " + file.getName() + " was saved successfully."));
                }
            } catch (IOException e) {
                logger.log(Level.WARNING, "Unable to save file.", e);
                Alerts.showError("Can't save file", "Unable to save " + modelName + ": " + e.getMessage());
            }
        }
    }

    public void saveToZip (String path, Map<String, byte[]> fileMap) throws IOException {
        File file = new File(path);
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
        for (String filename : fileMap.keySet()) {
            out.putNextEntry(new ZipEntry(filename));
            out.write(fileMap.get(filename));
            out.closeEntry();
        }
        out.close();
    }

    protected File loadImageDialog () {
        return loadFileDialog("Image", new String[]{"png", "jpg", "gif"});
    }

    protected File loadFileDialog (String typeName, String[] extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load " + typeName);
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter(typeName, extensions));
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
