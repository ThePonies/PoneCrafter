package org.theponies.ponecrafter.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.theponies.ponecrafter.AppConstants;
import org.theponies.ponecrafter.SceneName;
import org.theponies.ponecrafter.util.Alerts;

import java.io.IOException;

public abstract class BaseController {
    private Stage stage;
    @FXML
    private GridPane root;

    public abstract void setup ();

    protected void init (String title) {
        stage.setTitle(AppConstants.APP_NAME + " - " + title);
        stage.setScene(new Scene(root));
    }

    public static void switchScene (Stage stage, SceneName sceneName) throws IOException {
        FXMLLoader loader = new FXMLLoader(BaseController.class.getResource(sceneName.getFileName()));
        loader.load();
        BaseController controller = loader.getController();
        controller.stage = stage;
        controller.setup();
    }

    protected boolean switchScene (SceneName sceneName) {
        try {
            switchScene(stage, sceneName);
            return true;
        } catch (IOException e) {
            Alerts.showError("Cannot switch scene", "Unable to change scene: " + e.getMessage());
            return false;
        }
    }

    protected Stage getStage () {
        return stage;
    }

    protected Pane getRoot () {
        return root;
    }
}
