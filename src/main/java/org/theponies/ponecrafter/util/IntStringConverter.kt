package org.theponies.ponecrafter.util

import javafx.util.StringConverter
import tornadofx.isInt

class IntStringConverter : StringConverter<Int>() {
    override fun toString(number: Int?): String {
        return number?.toString() ?: ""
    }

    override fun fromString(string: String?): Int {
        return if (string?.isInt() == true) string.toInt() else 0
    }
}