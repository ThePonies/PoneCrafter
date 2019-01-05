package org.theponies.ponecrafter.util

import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType

import java.util.Optional

object Alerts {

    fun showInfo(title: String, message: String) {
        showAlert(Alert.AlertType.INFORMATION, title, message)
    }

    fun showWarning(title: String, message: String) {
        showAlert(Alert.AlertType.WARNING, title, message)
    }

    fun showError(title: String, message: String) {
        showAlert(Alert.AlertType.ERROR, title, message)
    }

    private fun showAlert(alertType: Alert.AlertType, title: String, message: String): Optional<ButtonType> {
        val alert = Alert(alertType)
        alert.title = title
        alert.contentText = message
        return alert.showAndWait()
    }

    fun showConfirmation(title: String, message: String): Boolean {
        val optional = showAlert(Alert.AlertType.CONFIRMATION, title, message)
        return optional.isPresent && !optional.get().buttonData.isCancelButton
    }
}//Private constructor
