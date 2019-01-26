package org.theponies.ponecrafter.controller

import org.theponies.ponecrafter.model.Furniture
import java.io.File
import java.util.zip.ZipEntry

class FurnitureEditorController : BaseEditorController<Furniture>() {

    override fun save(model: Furniture, file: File) {
        writeToZip(file) {
            it.putNextEntry(ZipEntry("properties.json"))
            it.write(model.toJSON().toString().toByteArray())
            it.closeEntry()
        }
    }
}