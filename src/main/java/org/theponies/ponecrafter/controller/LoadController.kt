package org.theponies.ponecrafter.controller

import javafx.scene.control.Alert
import javafx.stage.FileChooser
import org.theponies.ponecrafter.model.BaseModel
import org.theponies.ponecrafter.model.Floor
import org.theponies.ponecrafter.model.Furniture
import org.theponies.ponecrafter.model.Roof
import tornadofx.alert
import tornadofx.chooseFile
import tornadofx.string
import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipError
import java.util.zip.ZipException
import java.util.zip.ZipFile
import javax.json.Json
import javax.json.JsonException
import javax.json.JsonObject

class LoadController {
    fun openLoadDialog(): BaseModel? {
        val file = chooseFile(
            "Load PCC file...",
            arrayOf(FileChooser.ExtensionFilter("PoneCrafter Content", "*.pcc"),
                FileChooser.ExtensionFilter("Raw content", "*"))
        ).firstOrNull() ?: return null

        val zipFile: ZipFile
        val zipEntry: ZipEntry
        try {
            zipFile = ZipFile(file)
            zipEntry = zipFile.getEntry("properties.json")
            if (zipEntry == null) {
                loadError("The properties.json file is missing.")
                return null
            }
        } catch (e: ZipException) {
            loadError("The selected file is not a PCC package.")
            return null
        }

        val propertiesInput = zipFile.getInputStream(zipEntry)
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
            "floor" -> Floor().loadModel(json, getZipEntryStream(zipFile, "texture.png"))
            "roof" -> Roof().loadModel(json, getZipEntryStream(zipFile, "texture.png"))
            "furniture" -> Furniture().loadModel(json, getZipEntryStream(zipFile, "texture.png"), getZipEntryStream(zipFile, "model.obj"))
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