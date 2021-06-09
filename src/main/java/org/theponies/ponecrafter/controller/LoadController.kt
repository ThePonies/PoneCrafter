package org.theponies.ponecrafter.controller

import javafx.scene.control.Alert
import javafx.stage.FileChooser
import org.theponies.ponecrafter.model.BaseModel
import tornadofx.alert
import tornadofx.chooseDirectory
import tornadofx.chooseFile
import java.io.InputStream
import java.util.zip.ZipException
import java.util.zip.ZipFile

class LoadController {
    fun loadFileDialog(): BaseModel? {
        val file = chooseFile(
            "Load PCC file...",
            arrayOf(FileChooser.ExtensionFilter("PoneCrafter Content", "*.pcc"))
        ).firstOrNull() ?: return null

        val zipFile: ZipFile
        try {
            zipFile = ZipFile(file)
        } catch (e: ZipException) {
            loadErrorAlert("The selected file is not a PCC package.")
            return null
        }

        val fileList = zipFile.entries().toList().map { it.name }
        return try {
            ModelLoader().createModel(fileList) { getZipEntryStream(zipFile, it) }
        } catch (e: ModelLoader.ModelLoadException) {
            loadErrorAlert(e.message.toString())
            null
        }
    }

    fun loadDirectoryDialog(): BaseModel? {
        val folder = chooseDirectory("Choose raw content folder...") ?: return null
        return try {
            ModelLoader().loadFromFolder(folder)
        } catch (e: ModelLoader.ModelLoadException) {
            loadErrorAlert(e.message.toString())
            null
        }
    }

    private fun getZipEntryStream(zipFile: ZipFile, name: String): InputStream? {
        val entry = zipFile.getEntry(name) ?: return null
        return zipFile.getInputStream(entry)
    }

    private fun loadErrorAlert(message: String) {
        alert(
            Alert.AlertType.ERROR,
            "Failed to load item",
            message
        )
    }
}