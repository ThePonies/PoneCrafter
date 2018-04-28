package org.theponies.ponecrafter;

public enum SceneName {
    MENU("menu.fxml"),
    EDIT_FLOOR("floor-edit.fxml");

    private final String fileName;

    SceneName (String fileName) {
        this.fileName = fileName;
    }

    public String getFileName () {
        return "/" + fileName;
    }
}
