package org.theponies.ponecrafter.cli

import org.theponies.ponecrafter.controller.FloorEditorController
import org.theponies.ponecrafter.model.Floor
import org.theponies.ponecrafter.model.ImageData
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class CreateCommand(paramDescription: String, description: String) : Command(paramDescription, description) {

    override fun execute(params: List<String>) {
        if (params.size != 2) {
            println("Usage: create <type> <output folder>")
        }
        when (params[0]) {
            "floor" -> createFloor(params[1])
            else -> throw CliException("Invalid content type: " + params[0])
        }
    }

    private fun createFloor(outputFolder: String) {
        try {
            val path = Paths.get(outputFolder)
            Files.createDirectories(path)
            val directoryStream = Files.newDirectoryStream(path)
            if (directoryStream.iterator().hasNext()) {
                throw CliException("Output directory is not empty.")
            }
            val controller = FloorEditorController()
            val floor = Floor()
            val resourcePath = javaClass.getResourceAsStream("/images/placeholders/floor.png")
            floor.image = ImageData(resourcePath.readBytes())
            controller.saveRaw(floor, path)
        } catch (e: IOException) {
            throw CliException("Unhandled IOException: " + e.message, e)
        }
    }
}
