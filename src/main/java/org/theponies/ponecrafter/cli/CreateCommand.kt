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
        floor.name = "New floor"
        floor.description = "This is a new floor."
        floor.image = ImageData(readResource("/images/placeholders/floor.png"))
        controller.saveRaw(floor, outputPath)
    }

    private fun createRoof(outputPath: Path) {
        val controller = RoofEditorController()
        val roof = Roof()
        roof.name = "New roof"
        roof.description = "This is a new roof."
        roof.image = ImageData(readResource("/images/placeholders/roof.png"))
        controller.saveRaw(roof, outputPath)
    }

    private fun createFurniture(outputPath: Path) {
        val controller = FurnitureEditorController()
        val furniture = Furniture()
        furniture.name = "New object"
        furniture.description = "This is a new object."
        controller.saveRaw(furniture, outputPath)
    }

    private fun readResource(location: String) = javaClass.getResourceAsStream(location).use {
        it.readBytes()
    }
}
