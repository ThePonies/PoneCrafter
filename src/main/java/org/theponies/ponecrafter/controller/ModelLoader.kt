package org.theponies.ponecrafter.controller

import org.theponies.ponecrafter.model.*
import tornadofx.string
import java.io.File
import java.io.InputStream
import java.nio.file.Paths
import javax.json.Json
import javax.json.JsonException
import javax.json.JsonObject

class ModelLoader {
    class ModelLoadException(message: String) : Exception(message)

    fun loadFromFolder(folder: File): BaseModel {
        val fileList = folder.listFiles()?.map { it.name } ?: throw ModelLoadException("No files.")
        return ModelLoader().createModel(fileList) {
            val file = Paths.get(folder.absolutePath, it).toFile()
            if (file.exists()) file.inputStream() else null
        }
    }

    fun createModel(fileList: List<String>, readFile: (name: String) -> InputStream?): BaseModel {
        val propertiesInput = readFile("properties.json") ?: throw ModelLoadException("The properties.json file is missing.")

        val type: String?
        val json: JsonObject
        try {
            json = propertiesInput.use { Json.createReader(it).readObject() }
            type = json.string("type")
            if (type == null) {
                throw ModelLoadException("Item type was not specified.")
            }
        } catch (e: JsonException) {
            throw ModelLoadException("Properties file contains invalid JSON.")
        }

        return when (type) {
            "floor" -> Floor().loadModel(json, readFile("texture.png"))
            "wallcover" -> WallCover().loadModel(json, readFile("texture.png"))
            "roof" -> Roof().loadModel(json, readFile("texture.png"))
            "furniture" -> Furniture().loadModel(json, fileList.minus("properties.json"), readFile)
            else -> throw ModelLoadException("Unknown item type: $type")
        }
    }
}