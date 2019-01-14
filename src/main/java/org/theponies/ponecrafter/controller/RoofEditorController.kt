package org.theponies.ponecrafter.controller

import org.theponies.ponecrafter.model.Roof
import java.io.File
import java.util.zip.ZipEntry

class RoofEditorController : BaseEditorController<Roof>() {

    override fun save(model: Roof, file: File) {
        writeToZip(file) {
            it.putNextEntry(ZipEntry("properties.json"))
            it.write(model.toJSON().toString().toByteArray())
            it.putNextEntry(ZipEntry("texture.png"))
            it.write(getImageBytes(model.image))
            it.closeEntry()
        }
    }
}