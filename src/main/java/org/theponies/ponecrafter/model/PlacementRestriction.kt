package org.theponies.ponecrafter.model

enum class PlacementRestriction(val id: Int) {
    TERRAIN(0),
    FLOOR(1),
    SURFACE(2),
    COUNTER(3),
    WALL(4),
    CEILING(5);

    companion object {
        fun getById(id: Int) = values().firstOrNull { it.id == id } ?: TERRAIN
    }
}