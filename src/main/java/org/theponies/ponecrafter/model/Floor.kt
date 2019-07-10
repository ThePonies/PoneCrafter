package org.theponies.ponecrafter.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.theponies.ponecrafter.util.UuidUtil
import tornadofx.*
import java.io.InputStream
import java.util.*
import javax.json.JsonObject

class Floor(name: String = "", description: String = "", price: Int = 0) : BaseModel() {

    val nameProperty = SimpleStringProperty(this, "name", name)
    var name: String by nameProperty

    val descriptionProperty = SimpleStringProperty(this, "description", description)
    var description: String by descriptionProperty

    val priceProperty = SimpleIntegerProperty(this, "price", price)
    var price: Int by priceProperty

    val imageProperty = SimpleObjectProperty<ImageData>(this, "image", null)
    var image: ImageData? by imageProperty

    val uuidProperty = SimpleObjectProperty<UUID>(this, "uuid", UuidUtil.generateContentUuid())
    var uuid: UUID by uuidProperty

    override fun getTypeName() = "floor"

    override fun getModelName() = name

    override fun updateModel(json: JsonObject) {
        with(json) {
            uuid = uuid("uuid") ?: UUID(0, 0)
            name = string("name") ?: ""
            description = string("description") ?: ""
            price = int("price") ?: 0
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("uuid", uuid)
            add("type", getTypeName())
            add("name", name)
            add("description", description)
            add("price", price)
        }
    }

    fun loadModel(json: JsonObject, imageInput: InputStream?): Floor {
        updateModel(json)
        image = imageInput?.use { ImageData(it.readBytes()) }
        return this
    }
}