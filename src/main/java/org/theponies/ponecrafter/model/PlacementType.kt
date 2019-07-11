package org.theponies.ponecrafter.model

enum class PlacementType(val id: Int) {
    GROUND(0),
    TERRAIN(1),
    FLOOR(2),
    SURFACE(3),
    GROUND_OR_SURFACE(4),
    COUNTER(5),
    WALL(6),
    THROUGH_WALL(7),
    CEILING(8);

    companion object {
        fun getById(id: Int) = values().firstOrNull { it.id == id } ?: TERRAIN
    }

    override fun toString(): String {
        return name.toLowerCase().capitalize().replace('_', ' ')
    }
}