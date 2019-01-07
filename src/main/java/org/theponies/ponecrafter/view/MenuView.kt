package org.theponies.ponecrafter.view

import javafx.geometry.Pos
import javafx.scene.image.Image
import org.theponies.ponecrafter.Styles
import tornadofx.*

class MenuView : View("PoneCrafter Menu") {
    override val root = vbox {
        prefWidth = 800.0
        prefHeight = 600.0
        hbox {
            padding = insets(80, 100, 0, 100)
            imageview(Image("images/PoneCrafter.png"))
        }
        hbox(0, Pos.CENTER) {
            padding = insets(20)
            style {
                fontSize = 18.pt
            }
            label("Custom Content Tool for The Ponies")
        }
        hbox {
            padding = insets(40, 20)
            label("Create a...") {
                style {
                    fontSize = 18.pt
                }
            }
        }
        hbox(30) {
            padding = insets(40, 10)
            button("Wall Texture") {
                prefWidth = 120.0
            }
            button("Floor Texture") {
                prefWidth = 120.0
                action { replaceWith<FloorEditorView>(sizeToScene = true) }
            }
            button("Roof Texture") {
                prefWidth = 120.0
            }
            button("Terrain Texture") {
                prefWidth = 120.0
            }
            button("Object") {
                prefWidth = 120.0
            }
        }
        hbox(0, Pos.CENTER) {
            padding = insets(80)
            label("More text may or may not be added here") {
                addClass(Styles.subtleText)
            }
        }
    }
}