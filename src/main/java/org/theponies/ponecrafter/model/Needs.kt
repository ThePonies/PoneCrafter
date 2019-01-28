package org.theponies.ponecrafter.model

import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*
import javax.json.JsonObject

class Needs : JsonModel {
    val hungerProperty = SimpleIntegerProperty(this, "hunger", 0)
    var hunger: Int by hungerProperty
    val energyProperty = SimpleIntegerProperty(this, "energy", 0)
    var energy: Int by energyProperty
    val comfortProperty = SimpleIntegerProperty(this, "comfort", 0)
    var comfort: Int by comfortProperty
    val entertainmentProperty = SimpleIntegerProperty(this, "fun", 0)
    var entertainment: Int by entertainmentProperty
    val hygieneProperty = SimpleIntegerProperty(this, "hygiene", 0)
    var hygiene: Int by hygieneProperty
    val socialProperty = SimpleIntegerProperty(this, "social", 0)
    var social: Int by socialProperty
    val bladderProperty = SimpleIntegerProperty(this, "bladder", 0)
    var bladder: Int by bladderProperty
    val roomProperty = SimpleIntegerProperty(this, "room", 0)
    var room: Int by roomProperty

    override fun updateModel(json: JsonObject) {
        with(json) {
            hunger = int("hunger") ?: 0
            energy = int("energy") ?: 0
            comfort = int("comfort") ?: 0
            entertainment = int("fun") ?: 0
            hygiene = int("hygiene") ?: 0
            social = int("social") ?: 0
            bladder = int("bladder") ?: 0
            room = int("room") ?: 0
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("hunger", hunger)
            add("energy", energy)
            add("comfort", comfort)
            add("fun", entertainment)
            add("hygiene", hygiene)
            add("social", social)
            add("bladder", bladder)
            add("room", room)
        }
    }
}