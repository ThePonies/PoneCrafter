package org.theponies.ponecrafter.controller

import org.theponies.ponecrafter.model.Furniture
import tornadofx.toPrettyString
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipEntry

class FurnitureEditorController : BaseEditorController<Furniture>() {

    override fun save(model: Furniture, file: File) {
        writeToZip(file) {
            it.putNextEntry(ZipEntry("properties.json"))
            it.write(model.toJSON().toString().toByteArray())
            it.putNextEntry(ZipEntry("model.obj"))
            it.write(model.meshData.data)
            it.putNextEntry(ZipEntry("texture.png"))
            it.write(model.texture.data)
            it.closeEntry()
        }
    }

    override fun saveRaw(model: Furniture, path: Path) {
        Files.write(path.resolve("properties.json"), model.toJSON().toPrettyString().toByteArray())
        Files.write(path.resolve("model.obj"), model.meshData.data)
        Files.write(path.resolve("texture.png"), model.texture.data)
    }
}