package org.theponies.ponecrafter.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.image.Image
import tornadofx.*

class Floor(name: String = "", description: String = "", price: Int = 0) {
    val nameProperty = SimpleStringProperty(this, "name", name)
    var name by nameProperty

    val descriptionProperty = SimpleStringProperty(this, "description", description)
    var description by descriptionProperty

    val priceProperty = SimpleIntegerProperty(this, "price", price)
    var price by priceProperty

    val imageProperty = SimpleObjectProperty<Image>(this, "image", null)
    var image by imageProperty
}