package org.theponies.ponecrafter.model

import javafx.beans.property.Property
import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*
import javax.json.JsonObject

class Skills : JsonModel {
    val cookingProperty: Property<Boolean> = SimpleBooleanProperty(this, "cooking", false)
    var cooking: Boolean by cookingProperty
    val mechanicalProperty = SimpleBooleanProperty(this, "mechanical", false)
    var mechanical: Boolean by mechanicalProperty
    val charismaProperty = SimpleBooleanProperty(this, "charisma", false)
    var charisma: Boolean by charismaProperty
    val bodyProperty = SimpleBooleanProperty(this, "body", false)
    var body: Boolean by bodyProperty
    val logicProperty = SimpleBooleanProperty(this, "logic", false)
    var logic: Boolean by logicProperty
    val creativityProperty = SimpleBooleanProperty(this, "creativity", false)
    var creativity: Boolean by creativityProperty

    override fun updateModel(json: JsonObject) {
        with(json) {
            cooking = int("cooking") ?: 0 > 0
            mechanical = int("mechanical") ?: 0 > 0
            charisma = int("charisma") ?: 0 > 0
            body = int("body") ?: 0 > 0
            logic = int("logic") ?: 0 > 0
            creativity = int("creativity") ?: 0 > 0
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("cooking", if (cooking) 1 else 0)
            add("mechanical", if (mechanical) 1 else 0)
            add("charisma", if (charisma) 1 else 0)
            add("body", if (body) 1 else 0)
            add("logic", if (logic) 1 else 0)
            add("creativity", if (creativity) 1 else 0)
        }
    }
}