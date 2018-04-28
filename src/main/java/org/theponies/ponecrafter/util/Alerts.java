package org.theponies.ponecrafter.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts {

    private Alerts () {
        //Private constructor
    }

    public static void showInfo (String title, String message) {
        showAlert(Alert.AlertType.INFORMATION, title, message);
    }

    public static void showWarning (String title, String message) {
        showAlert(Alert.AlertType.WARNING, title, message);
    }

    public static void showError (String title, String message) {
        showAlert(Alert.AlertType.ERROR, title, message);
    }

    private static Optional<ButtonType> showAlert (Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    public static boolean showConfirmation (String title, String message) {
        Optional<ButtonType> optional = showAlert(Alert.AlertType.CONFIRMATION, title, message);
        return optional.isPresent() && !optional.get().getButtonData().isCancelButton();
    }
}
