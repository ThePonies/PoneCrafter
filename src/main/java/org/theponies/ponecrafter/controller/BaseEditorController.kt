package org.theponies.ponecrafter.controller

import javafx.embed.swing.SwingFXUtils
import javafx.scene.control.Alert.AlertType
import javafx.scene.image.Image
import javafx.stage.FileChooser
import org.theponies.ponecrafter.model.BaseModel
import org.theponies.ponecrafter.model.ImageData
import tornadofx.Controller
import tornadofx.FileChooserMode
import tornadofx.alert
import tornadofx.chooseFile
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipOutputStream
import javax.imageio.ImageIO

abstract class BaseEditorController<T : BaseModel> : Controller() {

    abstract fun save(model: T, file: File)

    abstract fun saveRaw(model: T, path: Path)

    fun chooseTextureDialog(typeName: String, reformat: Boolean, reformatWidth: Number = 128,
                            reformatHeight: Number = 128): ImageData? {
        val imageFile: File? = chooseFile(
            "Select $typeName texture...",
            arrayOf(FileChooser.ExtensionFilter("Image (png, jpeg, gif)", "*.png", "*.jpg", "*.jpeg", "*.gif"))
        ).firstOrNull()
        if (imageFile != null) {
            val image = if (reformat) {
                Image(imageFile.toURI().toString(), reformatWidth.toDouble(), reformatHeight.toDouble(), false, true)
            } else {
                Image(imageFile.toURI().toString())
            }
            if (image.isError) {
                alert(
                    AlertType.ERROR,
                    "Can't load image",
                    "The image could not be loaded."
                )
            } else {
                return ImageData(getImageBytes(image))
            }
        }
        return null
    }

    fun saveDialog(model: T) {
        val file = chooseFile(
            "Save ${model.getTypeName()}...",
            arrayOf(FileChooser.ExtensionFilter("PoneCrafter Content", "*.pcc"), FileChooser.ExtensionFilter("Raw content", "*")),
            FileChooserMode.Save
        ) {
            val regex = Regex("[^A-Za-z0-9 ]")
            val name = regex.replace(model.getModelName(), "").replace(" ", "-")
            initialFileName = "$name.pcc"
        }.firstOrNull()
        if (file != null) {
            val message = if (file.extension.isNotEmpty()) {
                save(model, file)
                "${model.getTypeName().capitalize()} saved to ${file.name}"
            } else {
                handleRawSave(file, model)
                "Raw ${model.getTypeName()} saved to folder ${file.name}"
            }
            alert(AlertType.INFORMATION, "Success", message)
        }
    }

    private fun handleRawSave(file: File, model: T) {
        val outputPath = file.toPath()
        Files.createDirectories(outputPath)
        val directoryStream = Files.newDirectoryStream(outputPath)
        if (directoryStream.iterator().hasNext()) {
            alert(AlertType.ERROR, "The selected folder must be empty to save in raw format.")
            return
        }
        saveRaw(model, file.toPath())
    }

    protected fun writeToZip(file: File, output: (zip: ZipOutputStream) -> Unit) {
        val fos = FileOutputStream(file)
        val bos = BufferedOutputStream(fos)
        val zos = ZipOutputStream(bos)
        zos.use { zip -> output(zip) }
    }

    private fun getImageBytes(image: Image): ByteArray {
        val bufferedImage = SwingFXUtils.fromFXImage(image, null)
        ByteArrayOutputStream().use { output ->
            ImageIO.write(bufferedImage, "png", output)
            return output.toByteArray()
        }
    }
}