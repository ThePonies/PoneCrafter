package org.theponies.ponecrafter.controller

import org.theponies.ponecrafter.model.Floor
import java.io.File
import java.util.zip.ZipEntry

class FloorEditorController : BaseEditorController<Floor>() {

    override fun save(model: Floor, file: File) {
        writeToZip(file) {
            it.putNextEntry(ZipEntry("properties.json"))
            it.write(model.toJSON().toString().toByteArray())
            it.putNextEntry(ZipEntry("texture.png"))
            it.write(getImageBytes(model.image))
            it.closeEntry()
        }
    }
}