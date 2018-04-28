package org.theponies.ponecrafter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.theponies.ponecrafter.controller.BaseController;

import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BaseController.switchScene(primaryStage, SceneName.MENU);
        primaryStage.show();
    }

}
