package org.theponies.ponecrafter.controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.stage.FileChooser
import org.theponies.ponecrafter.SceneName
import org.theponies.ponecrafter.exceptions.SavePackageException
import org.theponies.ponecrafter.model.JsonModel
import org.theponies.ponecrafter.model.SavePackage
import org.theponies.ponecrafter.util.Alerts
import org.theponies.ponecrafter.util.StringUtils
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import java.util.logging.Level
import java.util.logging.Logger

abstract class EditController(private val modelName: String) : BaseController() {
    @FXML
    private lateinit var guidLabel: Label
    var guid: UUID = UUID(0, 0)
        private set

    abstract val initialSaveName: String

    override fun setup() {
        init("Edit $modelName")
        regenerateGuid()
    }

    fun onSaveButton(actionEvent: ActionEvent) {
        if (!validateInput()) {
            return
        }
        var saveName: String? = initialSaveName
        if (saveName == null || saveName.isEmpty()) {
            saveName = "untitled"
        }
        val file = saveFileDialog(saveName)
        if (file != null) {
            if (file.exists() && !Alerts.showConfirmation("File already exists.",
                    "Are you sure you want to overwrite " + file.name + "?")) {
                return
            }
            try {
                val savePackage = createSavePackage()
                savePackage.saveToZip(file.path)
                Alerts.showInfo("Success!", StringUtils.capitalizeFirst(modelName) + " " + file.name + " saved successfully.")
            } catch (e: SavePackageException) {
                logger.log(Level.WARNING, "Failed to create save package.", e)
                Alerts.showError("Couldn't create save package.", e.message.toString())
            } catch (e: IOException) {
                logger.log(Level.WARNING, "Failed to save package.", e)
                Alerts.showError("Couldn't save package.", e.message.toString())
            }

        }
    }

    @Throws(SavePackageException::class)
    abstract fun createSavePackage(): SavePackage

    protected fun loadImageDialog(): File {
        return loadFileDialog("Image", arrayOf("png", "jpg", "gif"))
    }

    protected fun loadFileDialog(typeName: String, extensions: Array<String>): File {
        val fileChooser = FileChooser()
        fileChooser.title = "Load $typeName"
        fileChooser.selectedExtensionFilter = FileChooser.ExtensionFilter(typeName, *extensions)
        return fileChooser.showOpenDialog(stage)
    }

    protected fun saveFileDialog(name: String): File? {
        val fileChooser = FileChooser()
        fileChooser.title = "Save $modelName"
        fileChooser.initialFileName = "$name.pcc"
        return fileChooser.showOpenDialog(stage)
    }

    protected abstract fun validateInput(): Boolean

    protected fun validationError(message: String) {
        Alerts.showWarning("Invalid input", message)
    }

    protected abstract fun createModelFromInput(): JsonModel

    fun onBackButton(actionEvent: ActionEvent) {
        switchScene(SceneName.MENU)
    }

    fun onGenerateGuidButton(actionEvent: ActionEvent) {
        regenerateGuid()
    }

    fun regenerateGuid() {
        this.guid = generateGuid()
        guidLabel.text = "GUID: $guid"
    }

    private fun generateGuid(): UUID {
        //Starting at 00000100, which is 16^10.
        val min = 1099511627776L
        //Up until 00010000, which is 16^12.
        val max = 281474976710656L
        val mostSignificant = ThreadLocalRandom.current().nextLong(min, max)
        val leastSignificant = ThreadLocalRandom.current().nextLong(0, java.lang.Long.MAX_VALUE)
        return UUID(mostSignificant, leastSignificant)
    }

    companion object {
        private val logger = Logger.getLogger(EditController::class.java.name)
    }
}
