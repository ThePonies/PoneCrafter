package org.theponies.ponecrafter.model

enum class Age(val id: Int) {
    ANY(0),
    ADULT(1),
    FOAL(2);

    companion object {
        fun getById(id: Int) = values().firstOrNull { it.id == id } ?: ANY
    }
}