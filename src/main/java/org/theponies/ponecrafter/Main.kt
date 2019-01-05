package org.theponies.ponecrafter

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import org.theponies.ponecrafter.controller.BaseController

import java.net.URL

class Main : Application() {

    override fun start(primaryStage: Stage) {
        BaseController.switchScene(primaryStage, SceneName.MENU)
        primaryStage.show()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }

}