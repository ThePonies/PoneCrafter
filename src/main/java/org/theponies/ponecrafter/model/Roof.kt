package org.theponies.ponecrafter.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.theponies.ponecrafter.util.UuidUtil
import tornadofx.*
import java.io.InputStream
import java.util.*
import javax.json.JsonObject

class Roof(name: String = "", description: String = "") : BaseModel() {

    val nameProperty = SimpleStringProperty(this, "name", name)
    var name: String by nameProperty

    val descriptionProperty = SimpleStringProperty(this, "description", description)
    var description: String by descriptionProperty

    val imageProperty = SimpleObjectProperty<ImageData>(this, "image", null)
    var image: ImageData? by imageProperty

    val uuidProperty = SimpleObjectProperty<UUID>(this, "uuid", UuidUtil.generateContentUuid())
    var uuid: UUID by uuidProperty

    override fun getTypeName() = "roof"

    override fun getModelName() = name

    override fun updateModel(json: JsonObject) {
        with(json) {
            uuid = uuid("uuid") ?: UUID(0, 0)
            name = string("name") ?: ""
            description = string("description") ?: ""
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("uuid", uuid)
            add("type", getTypeName())
            add("name", name)
            add("description", description)
        }
    }

    fun loadModel(json: JsonObject, imageInput: InputStream?): Roof {
        updateModel(json)
        image = imageInput?.use { ImageData(it.readBytes()) }
        return this
    }
}