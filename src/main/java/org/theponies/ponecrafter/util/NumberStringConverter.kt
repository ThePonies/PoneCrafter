package org.theponies.ponecrafter.util

import javafx.util.StringConverter
import tornadofx.isInt

class NumberStringConverter : StringConverter<Number>() {
    override fun toString(number: Number?): String {
        return number?.toString() ?: ""
    }

    override fun fromString(string: String?): Number {
        return if (string?.isInt() == true) string.toInt() else 0
    }
}