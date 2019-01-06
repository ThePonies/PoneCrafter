package org.theponies.ponecrafter.view

import javafx.geometry.Pos
import javafx.scene.layout.Priority
import tornadofx.*

class MenuView : View("PoneCrafter Menu") {
    override val root = vbox(10, Pos.CENTER) {
        prefWidth = 160.0
        prefHeight = 100.0
        button("Start") {
            prefWidth = 120.0
            hgrow = Priority.ALWAYS
            action {
                replaceWith<FloorEditorView>(sizeToScene = true)
            }
        }
        button("Exit") {
            prefWidth = 120.0
            action {
                close()
            }
        }
    }
}