package org.theponies.ponecrafter.view.editor

import javafx.scene.Parent
import javafx.scene.layout.Priority
import org.theponies.ponecrafter.Styles
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