package org.theponies.ponecrafter.controller

import org.theponies.ponecrafter.model.Furniture
import tornadofx.toPrettyString
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipEntry

class FurnitureEditorController : BaseEditorController<Furniture>() {

    override fun save(model: Furniture, file: File) {
        writeToZip(file) { zip ->
            zip.putNextEntry(ZipEntry("properties.json"))
            zip.write(model.toJSON().toString().toByteArray())
            model.meshFiles.forEach {
                zip.putNextEntry(ZipEntry(convertFileName(it.fileName)))
                zip.write(it.data)
            }
            zip.closeEntry()
        }
    }

    override fun saveRaw(model: Furniture, path: Path) {
        Files.write(path.resolve("properties.json"), model.toJSON().toPrettyString().toByteArray())
        model.meshFiles.forEach {
            Files.write(path.resolve(convertFileName(it.fileName)), it.data)
        }
    }

    private fun convertFileName(fileName: String) = when {
        fileName.endsWith(".gltf", true) -> "model.gltf"
        fileName.endsWith(".glb", true) -> "model.glb"
        else -> fileName
    }
}