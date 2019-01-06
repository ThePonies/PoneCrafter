package org.theponies.ponecrafter.model

import javafx.beans.property.SimpleIntegerProperty
import tornadofx.ItemViewModel

class FloorModel(floor: Floor = Floor()) : ItemViewModel<Floor>(floor) {
    val name = bind(Floor::nameProperty)
    val description = bind(Floor::descriptionProperty)
    val price: SimpleIntegerProperty = bind(Floor::priceProperty)
    val image = bind(Floor::imageProperty)
}