package org.theponies.ponecrafter.model

import javafx.beans.property.SimpleIntegerProperty
import tornadofx.*
import javax.json.JsonObject

class Skills : JsonModel {
    val cookingProperty = SimpleIntegerProperty(this, "cooking", 0)
    var cooking: Int by cookingProperty
    val mechanicalProperty = SimpleIntegerProperty(this, "mechanical", 0)
    var mechanical: Int by mechanicalProperty
    val charismaProperty = SimpleIntegerProperty(this, "charisma", 0)
    var charisma: Int by charismaProperty
    val bodyProperty = SimpleIntegerProperty(this, "body", 0)
    var body: Int by bodyProperty
    val logicProperty = SimpleIntegerProperty(this, "logic", 0)
    var logic: Int by logicProperty
    val creativityProperty = SimpleIntegerProperty(this, "creativity", 0)
    var creativity: Int by creativityProperty

    override fun updateModel(json: JsonObject) {
        with(json) {
            cooking = int("cooking") ?: 0
            mechanical = int("mechanical") ?: 0
            charisma = int("charisma") ?: 0
            body = int("body") ?: 0
            logic = int("logic") ?: 0
            creativity = int("creativity") ?: 0
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("cooking", cooking)
            add("mechanical", mechanical)
            add("charisma", charisma)
            add("body", body)
            add("logic", logic)
            add("creativity", creativity)
        }
    }
}