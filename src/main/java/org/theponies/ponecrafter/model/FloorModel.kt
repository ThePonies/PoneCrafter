package org.theponies.ponecrafter.model

import tornadofx.ItemViewModel

class FloorModel(floor: Floor = Floor()) : ItemViewModel<Floor>(floor) {
    val name = bind(Floor::nameProperty)
    val description = bind(Floor::descriptionProperty)
    val price = bind(Floor::priceProperty)
    val image = bind(Floor::imageProperty)
    val uuid = bind(Floor::uuidProperty)
}