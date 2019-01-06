package org.theponies.ponecrafter.controller

import javafx.scene.control.Alert
import javafx.stage.FileChooser
import org.theponies.ponecrafter.model.Floor
import tornadofx.Controller
import tornadofx.alert
import tornadofx.chooseFile
import java.io.File

class FloorEditorController : Controller() {

    fun saveDialog(model: Floor) {
        val file = chooseFile(
            "Save floor...",
            arrayOf(FileChooser.ExtensionFilter("PoneCrafter Content", "*.pcc"))
        ) {
            initialFileName = "${model.name}.pcc"
        }.firstOrNull()
        if (file != null) {
            save(model, file)
            alert(
                Alert.AlertType.INFORMATION,
                "Success",
                "Floor saved to ${file.name}"
            )
        }
    }

    private fun save(model: Floor, file: File) {

    }
}