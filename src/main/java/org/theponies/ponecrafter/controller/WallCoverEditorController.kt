package org.theponies.ponecrafter.controller

import org.theponies.ponecrafter.model.WallCover
import tornadofx.toPrettyString
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipEntry

class WallCoverEditorController : BaseEditorController<WallCover>() {

    override fun save(model: WallCover, file: File) {
        writeToZip(file) { zip ->
            zip.putNextEntry(ZipEntry("properties.json"))
            zip.write(model.toJSON().toString().toByteArray())
            model.image?.let {
                zip.putNextEntry(ZipEntry("texture.png"))
                zip.write(it.data)
            }
            zip.closeEntry()
        }
    }

    override fun saveRaw(model: WallCover, path: Path) {
        Files.write(path.resolve("properties.json"), model.toJSON().toPrettyString().toByteArray())
        model.image?.let { Files.write(path.resolve("texture.png"), it.data) }
    }
}