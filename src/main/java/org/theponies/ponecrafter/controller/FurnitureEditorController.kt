package org.theponies.ponecrafter.controller

import org.theponies.ponecrafter.model.Furniture
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipEntry

class FurnitureEditorController : BaseEditorController<Furniture>() {

    override fun save(model: Furniture, file: File) {
        writeToZip(file) {
            it.putNextEntry(ZipEntry("properties.json"))
            it.write(model.toJSON().toString().toByteArray())
            it.closeEntry()
        }
    }

    override fun saveRaw(model: Furniture, path: Path) {
        Files.write(path.resolve("properties.json"), model.toJSON().toString().toByteArray())
    }
}