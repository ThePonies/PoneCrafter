package org.theponies.ponecrafter.controller

import org.theponies.ponecrafter.model.Roof
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipEntry

class RoofEditorController : BaseEditorController<Roof>() {

    override fun save(model: Roof, file: File) {
        writeToZip(file) {
            it.putNextEntry(ZipEntry("properties.json"))
            it.write(model.toJSON().toString().toByteArray())
            it.putNextEntry(ZipEntry("texture.png"))
            it.write(model.image.data)
            it.closeEntry()
        }
    }

    override fun saveRaw(model: Roof, path: Path) {
        Files.write(path.resolve("properties.json"), model.toJSON().toString().toByteArray())
        Files.write(path.resolve("texture.png"), model.image.data)
    }
}