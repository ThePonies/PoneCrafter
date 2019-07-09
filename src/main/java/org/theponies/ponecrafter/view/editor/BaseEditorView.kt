package org.theponies.ponecrafter.view.editor

import javafx.beans.property.Property
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Priority
import org.theponies.ponecrafter.Styles
import org.theponies.ponecrafter.model.ImageData
import org.theponies.ponecrafter.util.NumberStringConverter
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
    textfield(property, NumberStringConverter()) {
        filterInput {
            it.controlNewText.isInt()
        }
        validator {
            if (it.isNullOrBlank()) error("The $name field is required") else null
        }
    }
}

// Stolen from TornadoFX's Controls.kt, because if was internal.
private inline fun <T : Node> T.attachTo(
    parent: EventTarget,
    after: T.() -> Unit,
    before: (T) -> Unit
) = this.also(before).attachTo(parent, after)

fun EventTarget.imageDataView(observableData: ObservableValue<ImageData>, width: Double, height: Double, op: ImageView.() -> Unit = {}) =
    ImageView().attachTo(this, op) { imageView ->
        imageView.imageProperty().bind(objectBinding(observableData) { value?.let { Image(it.data.inputStream(), width, height, false, false) } })
    }