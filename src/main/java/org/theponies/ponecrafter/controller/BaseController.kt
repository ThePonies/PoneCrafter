package org.theponies.ponecrafter.controller

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.stage.Stage
import org.theponies.ponecrafter.AppConstants
import org.theponies.ponecrafter.SceneName
import org.theponies.ponecrafter.util.Alerts

import java.io.IOException

abstract class BaseController {
    protected var stage: Stage? = null
        private set
    @FXML
    private lateinit var root: GridPane

    abstract fun setup()

    protected fun init(title: String) {
        stage!!.title = AppConstants.APP_NAME + " - " + title
        stage!!.scene = Scene(root)
    }

    protected fun switchScene(sceneName: SceneName): Boolean {
        try {
            switchScene(stage, sceneName)
            return true
        } catch (e: IOException) {
            Alerts.showError("Cannot switch scene", "Unable to change scene: " + e.message)
            return false
        }

    }

    protected fun getRoot(): Pane? {
        return root
    }

    companion object {

        @Throws(IOException::class)
        fun switchScene(stage: Stage?, sceneName: SceneName) {
            val loader = FXMLLoader(BaseController::class.java.getResource(sceneName.getFileName()))
            loader.load<Any>()
            val controller = loader.getController<BaseController>()
            controller.stage = stage
            controller.setup()
        }
    }
}
