package org.theponies.ponecrafter.util

object StringUtils {

    fun capitalizeFirst(string: String): String {
        return string.substring(0, 1).toUpperCase() + string.substring(1)
    }
}//Private constructor
