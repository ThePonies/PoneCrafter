package org.theponies.ponecrafter.view.editor

import javafx.beans.property.Property
import javafx.event.EventTarget
import javafx.scene.Parent
import javafx.scene.layout.Priority
import org.theponies.ponecrafter.Styles
import org.theponies.ponecrafter.util.IntStringConverter
import tornadofx.*

abstract class BaseEditorView(val name: String) : View(name) {

    fun createHeader(parent: Parent, name: String, icon: String, buttonBar: (parent: Parent) -> Unit = {}) = with(parent) {
        hbox {
            hgrow = Priority.ALWAYS
            svgpath(icon) {
                fill = Styles.textColor
            }
            label(name) {
                addClass(Styles.windowTitle)
                padding = insets(15, 0)
            }
            prefHeight = 80.0
            buttonBar(this)
        }
    }
}

fun EventTarget.numberField(property: Property<Number>, name: String = property.name) = field(name.capitalize()) {
    textfield(property, IntStringConverter()) {
        filterInput {
            it.controlNewText.isInt()
        }
        validator {
            if (it.isNullOrBlank()) error("The $name field is required") else null
        }
    }
}