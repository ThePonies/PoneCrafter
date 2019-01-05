package org.theponies.ponecrafter.model

import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.Image
import org.theponies.ponecrafter.exceptions.SavePackageException

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.HashMap
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class SavePackage {
    private val files: MutableMap<String, ByteArray>

    init {
        files = HashMap()
    }

    fun getFiles(): Map<String, ByteArray> {
        return files
    }

    @Throws(SavePackageException::class)
    fun addImage(name: String, image: Image) {
        try {
            files[name] = getImageBytes(image)
        } catch (e: IOException) {
            throw SavePackageException("Failed to add image to save package.", e)
        }

    }

    fun addFile(name: String, data: ByteArray) {
        files[name] = data
    }

    @Throws(IOException::class)
    fun saveToZip(path: String) {
        val file = File(path)
        val out = ZipOutputStream(FileOutputStream(file))
        for (filename in files.keys) {
            out.putNextEntry(ZipEntry(filename))
            out.write(files[filename])
            out.closeEntry()
        }
        out.close()
    }

    @Throws(IOException::class)
    private fun getImageBytes(image: Image): ByteArray {
        val bufferedImage = SwingFXUtils.fromFXImage(image, null)
        ByteArrayOutputStream().use { output ->
            ImageIO.write(bufferedImage, "png", output)
            return output.toByteArray()
        }
    }
}
