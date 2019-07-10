package org.theponies.ponecrafter.controller

import javafx.scene.control.Alert
import javafx.stage.FileChooser
import org.theponies.ponecrafter.model.BaseModel
import org.theponies.ponecrafter.model.Floor
import org.theponies.ponecrafter.model.Furniture
import org.theponies.ponecrafter.model.Roof
import tornadofx.*
import java.io.InputStream
import java.nio.file.Paths
import java.util.zip.ZipException
import java.util.zip.ZipFile
import javax.json.Json
import javax.json.JsonException
import javax.json.JsonObject

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
            loadError("The selected file is not a PCC package.")
            return null
        }

        return createModel { getZipEntryStream(zipFile, it) }
    }

    fun loadDirectoryDialog(): BaseModel? {
        val folder = chooseDirectory("Choose raw content folder...") ?: return null
        return createModel {
            val file = Paths.get(folder.absolutePath, it).toFile()
            if (file.exists()) file.inputStream() else null
        }
    }

    private fun createModel(readFile: (name: String) -> InputStream?): BaseModel? {
        val propertiesInput = readFile("properties.json")
        if (propertiesInput == null) {
            loadError("The properties.json file is missing.")
            return null
        }

        val type: String?
        val json: JsonObject
        try {
            json = propertiesInput.use { Json.createReader(it).readObject() }
            type = json.string("type")
            if (type == null) {
                loadError("Item type was not specified.")
                return null
            }

        } catch (e: JsonException) {
            loadError("Properties file contains invalid JSON.")
            return null
        }

        return when (type) {
            "floor" -> Floor().loadModel(json, readFile("texture.png"))
            "roof" -> Roof().loadModel(json, readFile("texture.png"))
            "furniture" -> Furniture().loadModel(json, readFile("texture.png"), readFile("model.obj"))
            else -> {
                loadError("Unknown item type: $type")
                return null
            }
        }
    }

    private fun getZipEntryStream(zipFile: ZipFile, name: String): InputStream? {
        val entry = zipFile.getEntry(name) ?: return null
        return zipFile.getInputStream(entry)
    }

    private fun loadError(message: String) {
        alert(
            Alert.AlertType.ERROR,
            "Failed to load item",
            message
        )
    }
}