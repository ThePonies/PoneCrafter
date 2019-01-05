package org.theponies.ponecrafter.controller

import javafx.event.ActionEvent
import org.theponies.ponecrafter.SceneName

class MenuController : BaseController() {

    override fun setup() {
        init("Menu")
    }

    fun onStartButton(actionEvent: ActionEvent) {
        switchScene(SceneName.EDIT_FLOOR)
    }

    fun onExitButton(actionEvent: ActionEvent) {
        stage!!.close()
    }


}
