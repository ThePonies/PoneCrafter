package org.theponies.ponecrafter.model

enum class CatalogCategory(val id: Int) {
    NONE(0),
    SEATING(1),
    SURFACES(2),
    DECORATION(3),
    ELECTRONICS(4),
    APPLIANCES(5),
    PLUMBING(6),
    LIGHTING(7),
    MISC(8),
    DOORS(9),
    WINDOWS(10),
    GARDENING(11),
    STAIRS(12),
    FIREPLACES(13);

    companion object {
        fun getById(id: Int) = values().firstOrNull { it.id == id } ?: NONE
    }

    override fun toString(): String {
        return name.toLowerCase().capitalize()
    }
}