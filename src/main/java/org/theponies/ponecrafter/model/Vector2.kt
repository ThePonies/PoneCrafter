package org.theponies.ponecrafter.model

import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*
import javax.json.JsonObject

class Vector2(initialX: Int = 0, initialY: Int = 0) : JsonModel {
    val xProperty = SimpleIntegerProperty(this, "x", initialX)
    var x: Int by xProperty
    val yProperty = SimpleIntegerProperty(this, "y", initialY)
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vector2

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }


}