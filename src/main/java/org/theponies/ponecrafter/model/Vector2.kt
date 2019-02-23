package org.theponies.ponecrafter.model

import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*
import javax.json.JsonObject

class Vector2 : JsonModel {
    val xProperty = SimpleIntegerProperty(this, "x", 0)
    var x: Int by xProperty
    val yProperty = SimpleIntegerProperty(this, "y", 0)
    var y: Int by yProperty

    override fun updateModel(json: JsonObject) {
        with(json) {
            x = int("x") ?: 0
            y = int("y") ?: 0
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("x", x)
            add("y", y)
        }
    }
}