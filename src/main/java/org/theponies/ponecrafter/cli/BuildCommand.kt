package org.theponies.ponecrafter.cli

import org.theponies.ponecrafter.controller.*
import org.theponies.ponecrafter.model.*
import tornadofx.loadJsonObject
import java.nio.file.Files
import java.nio.file.InvalidPathException
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.NoSuchFileException
import javax.json.JsonString

class BuildCommand(paramDescription: String, description: String) : Command(paramDescription, description) {
    private val typeMap = mapOf(
        "floor" to ::buildFloor,
        "wallcover" to ::buildWallCover,
        "roof" to ::buildRoof,
        "furniture" to ::buildFurniture
    )

    override fun execute(params: List<String>) {
        if (params.size != 1 && params.size != 2) {
            println("Usage: build <path> (<output file>)")
            return
        }
        try {
            val inputPath = Paths.get(params[0])
            val outputFile = Paths.get(if (params.size > 1) params[1] else "$inputPath.pcc")
            val type = getContentType(inputPath)
            typeMap.getValue(type).invoke(inputPath, outputFile)
            println("Successfully built $type to file: ${outputFile.toAbsolutePath()}")
        } catch (e: InvalidPathException) {
            throw CliException("Invalid path: ${e.message}")
        }
    }

    private fun getContentType(inputPath: Path): String {
        val propertiesPath = inputPath.resolve("properties.json")
        try {
            val json = loadJsonObject(propertiesPath)
            if (!json.containsKey("type")) {
                throw CliException("Properties file does not contain a type field.")
            }

            val type = json["type"] as? JsonString ?: throw CliException("Content type must be a string value.")

            if (!typeMap.containsKey(type.string)) {
                throw CliException("Invalid content type: $type")
            }

            return type.string
        } catch (e: NoSuchFileException) {
            throw CliException("Unable to find properties file: ${propertiesPath.toAbsolutePath()}")
        }
    }

    private fun buildFloor(inputPath: Path, outputFile: Path) {
        val controller = FloorEditorController()
        val floor = Floor()
        try {
            val properties = loadJsonObject(inputPath.resolve("properties.json"))
            val imageData = ImageData(Files.readAllBytes(inputPath.resolve("texture.png")))
            floor.updateModel(properties)
            floor.image = imageData
            controller.save(floor, outputFile.toFile())
        } catch (e: NoSuchFileException) {
            throw CliException("Missing file: ${e.message}")
        }
    }

    private fun buildWallCover(inputPath: Path, outputFile: Path) {
        val controller = WallCoverEditorController()
        val wallCover = WallCover()
        try {
            val properties = loadJsonObject(inputPath.resolve("properties.json"))
            val imageData = ImageData(Files.readAllBytes(inputPath.resolve("texture.png")))
            wallCover.updateModel(properties)
            wallCover.image = imageData
            controller.save(wallCover, outputFile.toFile())
        } catch (e: NoSuchFileException) {
            throw CliException("Missing file: ${e.message}")
        }
    }

    private fun buildRoof(inputPath: Path, outputFile: Path) {
        val controller = RoofEditorController()
        val roof = Roof()
        try {
            val properties = loadJsonObject(inputPath.resolve("properties.json"))
            val imageData = ImageData(Files.readAllBytes(inputPath.resolve("texture.png")))
            roof.updateModel(properties)
            roof.image = imageData
            controller.save(roof, outputFile.toFile())
        } catch (e: NoSuchFileException) {
            throw CliException("Missing file: ${e.message}")
        }
    }

    private fun buildFurniture(inputPath: Path, outputFile: Path) {
        try {
            val furniture = ModelLoader().loadFromFolder(inputPath.toFile()) as Furniture
            FurnitureEditorController().save(furniture, outputFile.toFile())
        } catch (e: NoSuchFileException) {
            throw CliException("Missing file: ${e.message}")
        } catch (e: ModelLoader.ModelLoadException) {
            throw CliException("Failed to load: ${e.message}")
        }
    }

}
