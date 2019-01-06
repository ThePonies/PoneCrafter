package org.theponies.ponecrafter.controller

import javafx.embed.swing.SwingFXUtils
import javafx.scene.control.Alert
import javafx.scene.image.Image
import javafx.stage.FileChooser
import org.theponies.ponecrafter.model.Floor
import tornadofx.Controller
import tornadofx.FileChooserMode
import tornadofx.alert
import tornadofx.chooseFile
import java.io.*
import java.util.zip.ZipOutputStream
import java.util.zip.ZipEntry
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import javax.imageio.ImageIO

class FloorEditorController : Controller() {

    fun saveDialog(model: Floor) {
        val file = chooseFile(
            "Save floor...",
            arrayOf(FileChooser.ExtensionFilter("PoneCrafter Content", "*.pcc")),
            FileChooserMode.Save
        ) {
            val regex = Regex("[^A-Za-z0-9 ]")
            val name = regex.replace(model.name, "").replace(" ", "-")
            initialFileName = "${name}.pcc"
        }.firstOrNull()
        if (file != null) {
            save(model, file)
            alert(
                Alert.AlertType.INFORMATION,
                "Success",
                "Floor saved to ${file.name}"
            )
        }
    }

    private fun save(model: Floor, file: File) {
        val fos = FileOutputStream(file)
        val bos = BufferedOutputStream(fos)
        val zos = ZipOutputStream(bos)
        zos.use { zip ->
            zip.putNextEntry(ZipEntry("properties.json"))
            zip.write(model.toJSON().toString().toByteArray())
            zip.putNextEntry(ZipEntry("texture.png"))
            zip.write(getImageBytes(model.image))
            zip.closeEntry()
        }
    }

    private fun getImageBytes(image: Image): ByteArray {
        val bufferedImage = SwingFXUtils.fromFXImage(image, null)
        ByteArrayOutputStream().use { output ->
            ImageIO.write(bufferedImage, "png", output)
            return output.toByteArray()
        }
    }
}