package org.theponies.ponecrafter

enum class SceneName constructor(private val fileName: String) {
    MENU("menu.fxml"),
    EDIT_FLOOR("floor-edit.fxml");

    fun getFileName(): String {
        return "/$fileName"
    }
}
