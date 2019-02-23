package org.theponies.ponecrafter.model

import javafx.beans.property.*
import javafx.collections.ObservableList
import org.theponies.ponecrafter.util.UuidUtil
import tornadofx.*
import java.util.*
import javax.json.JsonNumber
import javax.json.JsonObject

class Furniture(name: String = "", description: String = "", price: Int = 0) : BaseModel() {

    val nameProperty = SimpleStringProperty(this, "name", name)
    var name: String by nameProperty

    val descriptionProperty = SimpleStringProperty(this, "description", description)
    var description: String by descriptionProperty

    val priceProperty = SimpleIntegerProperty(this, "price", price)
    var price: Int by priceProperty

    val uuidProperty = SimpleObjectProperty<UUID>(this, "uuid", UuidUtil.generateContentUuid())
    var uuid: UUID by uuidProperty

    val categoryProperty = SimpleObjectProperty<CatalogCategory>(this, "category", CatalogCategory.SEATING)
    var category: CatalogCategory by categoryProperty

    val pickupableProperty = SimpleBooleanProperty(this, "pickupable", true)
    var pickupable: Boolean by pickupableProperty

    val sellableProperty = SimpleBooleanProperty(this, "sellable", true)
    var sellable: Boolean by sellableProperty

    val occupiedTilesProperty: SimpleListProperty<Vector2> = SimpleListProperty(this, "occupiedTiles", observableList(Vector2()))
    val occupiedTiles: ObservableList<Vector2> by occupiedTilesProperty

    val placementRestrictionsProperty: SimpleListProperty<PlacementRestriction> =
        SimpleListProperty(this, "placementRestrictions", observableList(PlacementRestriction.TERRAIN, PlacementRestriction.FLOOR))
    val placementRestrictions: ObservableList<PlacementRestriction> by placementRestrictionsProperty

    val needStats = Needs()

    val skillStats = Skills()

    val requiredAgeProperty = SimpleObjectProperty<Age>(this, "requiredAge", Age.ANY)
    var requiredAge: Age by requiredAgeProperty

    override fun getTypeName() = "furniture"

    override fun getModelName() = name

    override fun updateModel(json: JsonObject) {
        with(json) {
            uuid = uuid("uuid") ?: UUID(0, 0)
            name = string("name") ?: ""
            description = string("description") ?: ""
            price = int("price") ?: 0
            category = CatalogCategory.getById(int("category") ?: 0)
            pickupable = bool("pickupable") ?: true
            sellable = bool("sellable") ?: true
            jsonArray("occupiedTiles")?.let { occupiedTiles.setAll(it.toModel()) }
            jsonArray("placementRestrictions")?.let {
                placementRestrictions.setAll(it.map { value ->
                    PlacementRestriction.getById((value as? JsonNumber)?.intValue() ?: 0)
                })
            }
            jsonObject("needStats")?.let { needStats.updateModel(it) }
            jsonObject("skillStats")?.let { skillStats.updateModel(it) }
            requiredAge = Age.getById(int("requiredAge") ?: 0)
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("uuid", uuid)
            add("type", getTypeName())
            add("name", name)
            add("description", description)
            add("price", price)
            add("category", category.id)
            add("pickupable", pickupable)
            add("sellable", sellable)
            add("occupiedTiles", occupiedTiles)
            add("placementRestrictions", placementRestrictions.map { it.id })
            add("needStats", needStats)
            add("skillStats", skillStats)
            add("requiredAge", requiredAge.id)
        }
    }
}