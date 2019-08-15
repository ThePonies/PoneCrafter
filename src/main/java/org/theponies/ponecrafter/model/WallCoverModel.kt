package org.theponies.ponecrafter.model

import tornadofx.ItemViewModel

class WallCoverModel(wallCover: WallCover = WallCover()) : ItemViewModel<WallCover>(wallCover) {
    val name = bind(WallCover::nameProperty)
    val description = bind(WallCover::descriptionProperty)
    val price = bind(WallCover::priceProperty)
    val image = bind(WallCover::imageProperty)
    val uuid = bind(WallCover::uuidProperty)
}