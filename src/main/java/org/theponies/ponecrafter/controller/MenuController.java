package org.theponies.ponecrafter.controller;

import javafx.event.ActionEvent;
import org.theponies.ponecrafter.SceneName;

public class MenuController extends BaseController {

    @Override
    public void setup () {
        init("Menu");
    }

    public void onStartButton (ActionEvent actionEvent) {
        switchScene(SceneName.EDIT_FLOOR);
    }

    public void onExitButton (ActionEvent actionEvent) {
        getStage().close();
    }


}
