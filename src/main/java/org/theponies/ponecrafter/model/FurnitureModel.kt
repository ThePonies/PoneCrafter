package org.theponies.ponecrafter.model

import tornadofx.ItemViewModel

class FurnitureModel(furniture: Furniture = Furniture()) : ItemViewModel<Furniture>(furniture) {
    val name = bind(Furniture::nameProperty)
    val description = bind(Furniture::descriptionProperty)
    val price = bind(Furniture::priceProperty)
    val uuid = bind(Furniture::uuidProperty)
}