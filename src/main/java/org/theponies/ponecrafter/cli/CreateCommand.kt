package org.theponies.ponecrafter.cli

import org.theponies.ponecrafter.controller.FloorEditorController
import org.theponies.ponecrafter.controller.FurnitureEditorController
import org.theponies.ponecrafter.controller.RoofEditorController
import org.theponies.ponecrafter.model.Floor
import org.theponies.ponecrafter.model.Furniture
import org.theponies.ponecrafter.model.ImageData
import org.theponies.ponecrafter.model.Roof
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class CreateCommand(paramDescription: String, description: String) : Command(paramDescription, description) {
    private val typeMap = mapOf(
        "floor" to ::createFloor,
        "roof" to ::createRoof,
        "furniture" to ::createFurniture
    )

    override fun execute(params: List<String>) {
        if (params.size != 2) {
            println("Usage: create <type> <output folder>")
        }
        val typeKey = params[0]
        val output = params[1]
        if (!typeMap.containsKey(typeKey)) {
            throw CliException("Invalid content type: $typeKey")
        }
        try {
            val outputPath = Paths.get(output)
            Files.createDirectories(outputPath)
            val directoryStream = Files.newDirectoryStream(outputPath)
            if (directoryStream.iterator().hasNext()) {
                throw CliException("Output directory is not empty.")
            }
            typeMap.getValue(typeKey).invoke(outputPath)
            println("Created $typeKey ${outputPath.fileName} at ${outputPath.toAbsolutePath()}")
        } catch (e: IOException) {
            throw CliException("Unhandled IOException: " + e.message, e)
        }
    }

    private fun createFloor(outputPath: Path) {
        val controller = FloorEditorController()
        val floor = Floor()
        val resourcePath = javaClass.getResourceAsStream("/images/placeholders/floor.png")
        floor.image = ImageData(resourcePath.readBytes())
        controller.saveRaw(floor, outputPath)
    }

    private fun createRoof(outputPath: Path) {
        val controller = RoofEditorController()
        val roof = Roof()
        val resourcePath = javaClass.getResourceAsStream("/images/placeholders/roof.png")
        roof.image = ImageData(resourcePath.readBytes())
        controller.saveRaw(roof, outputPath)
    }

    private fun createFurniture(outputPath: Path) {
        val controller = FurnitureEditorController()
        val furniture = Furniture()
        controller.saveRaw(furniture, outputPath)
    }
}
