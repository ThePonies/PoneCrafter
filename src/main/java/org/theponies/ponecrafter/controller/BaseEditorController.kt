package org.theponies.ponecrafter.controller

import javafx.embed.swing.SwingFXUtils
import javafx.scene.control.Alert
import javafx.scene.image.Image
import javafx.stage.FileChooser
import org.theponies.ponecrafter.model.BaseModel
import tornadofx.Controller
import tornadofx.FileChooserMode
import tornadofx.alert
import tornadofx.chooseFile
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipOutputStream
import javax.imageio.ImageIO

abstract class BaseEditorController<T : BaseModel> : Controller() {

    abstract fun save(model: T, file: File)

    fun chooseTextureDialog(typeName: String): Image? {
        val imageFile: File? = chooseFile(
            "Select $typeName texture...",
            arrayOf(FileChooser.ExtensionFilter("Image (png, jpeg, gif)", "*.png", "*.jpg", "*.jpeg", "*.gif"))
        ).firstOrNull()
        if (imageFile != null) {
            val image = Image(imageFile.toURI().toString(), 128.0, 128.0, false, true)
            if (image.isError) {
                alert(
                    Alert.AlertType.ERROR,
                    "Can't load image",
                    "The image could not be loaded."
                )
            } else {
                return image
            }
        }
        return null
    }

    fun saveDialog(model: T) {
        val file = chooseFile(
            "Save ${model.getTypeName()}...",
            arrayOf(FileChooser.ExtensionFilter("PoneCrafter Content", "*.pcc")),
            FileChooserMode.Save
        ) {
            val regex = Regex("[^A-Za-z0-9 ]")
            val name = regex.replace(model.getModelName(), "").replace(" ", "-")
            initialFileName = "$name.pcc"
        }.firstOrNull()
        if (file != null) {
            save(model, file)
            alert(
                Alert.AlertType.INFORMATION,
                "Success",
                "${model.getTypeName().capitalize()} saved to ${file.name}"
            )
        }
    }

    protected fun writeToZip(file: File, output: (zip: ZipOutputStream) -> Unit) {
        val fos = FileOutputStream(file)
        val bos = BufferedOutputStream(fos)
        val zos = ZipOutputStream(bos)
        zos.use { zip -> output(zip) }
    }

    protected fun getImageBytes(image: Image): ByteArray {
        val bufferedImage = SwingFXUtils.fromFXImage(image, null)
        ByteArrayOutputStream().use { output ->
            ImageIO.write(bufferedImage, "png", output)
            return output.toByteArray()
        }
    }
}