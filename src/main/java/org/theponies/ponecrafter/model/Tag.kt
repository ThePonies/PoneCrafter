package org.theponies.ponecrafter.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import javax.json.JsonObject

class Tag : JsonModel {
    val nameProperty = SimpleStringProperty(this, "name", "name")
    var name: String by nameProperty
    val valueProperty = SimpleStringProperty(this, "value", "value")
    var value: String by valueProperty

    override fun updateModel(json: JsonObject) {
        with(json) {
            name = string("name") ?: ""
            value = string("value") ?: ""
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("name", name)
            add("value", value)
        }
    }
}