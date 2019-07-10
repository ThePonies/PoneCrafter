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
            model.meshData?.let {
                zip.putNextEntry(ZipEntry("model.obj"))
                zip.write(it.data)
            }
            model.texture?.let {
                zip.putNextEntry(ZipEntry("texture.png"))
                zip.write(it.data)
            }
            zip.closeEntry()
        }
    }

    override fun saveRaw(model: Furniture, path: Path) {
        Files.write(path.resolve("properties.json"), model.toJSON().toPrettyString().toByteArray())
        model.meshData?.let { Files.write(path.resolve("model.obj"), it.data) }
        model.texture?.let { Files.write(path.resolve("texture.png"), it.data) }
    }
}