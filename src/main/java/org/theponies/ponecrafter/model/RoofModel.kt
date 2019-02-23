package org.theponies.ponecrafter.model

import tornadofx.ItemViewModel

class RoofModel(roof: Roof = Roof()) : ItemViewModel<Roof>(roof) {
    val name = bind(Roof::nameProperty)
    val description = bind(Roof::descriptionProperty)
    val image = bind(Roof::imageProperty)
    val uuid = bind(Roof::uuidProperty)
}