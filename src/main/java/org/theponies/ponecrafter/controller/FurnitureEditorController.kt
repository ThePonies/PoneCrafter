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
                val fileName = if (it.fileName.endsWith(".gltf")) "model.gltf" else it.fileName
                zip.putNextEntry(ZipEntry(fileName))
                zip.write(it.data)
            }
            zip.closeEntry()
        }
    }

    override fun saveRaw(model: Furniture, path: Path) {
        Files.write(path.resolve("properties.json"), model.toJSON().toPrettyString().toByteArray())
        model.meshFiles.forEach {
            val fileName = if (it.fileName.endsWith(".gltf")) "model.gltf" else it.fileName
            Files.write(path.resolve(fileName), it.data)
        }
    }
}