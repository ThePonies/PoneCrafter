package org.theponies.ponecrafter.view

import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.image.Image
import org.theponies.ponecrafter.Icons
import org.theponies.ponecrafter.Styles
import org.theponies.ponecrafter.controller.LoadController
import org.theponies.ponecrafter.model.Floor
import org.theponies.ponecrafter.model.Furniture
import org.theponies.ponecrafter.model.Roof
import org.theponies.ponecrafter.view.editor.FloorEditorView
import org.theponies.ponecrafter.view.editor.FurnitureEditorView
import org.theponies.ponecrafter.view.editor.RoofEditorView
import tornadofx.*

class MenuView : View("PoneCrafter Menu") {
    override val root = vbox {
        hbox {
            padding = insets(80, 100, 0, 100)
            imageview(Image("images/PoneCrafter.png"))
        }
        hbox(0, Pos.CENTER) {
            padding = insets(20)
            addClass(Styles.windowTitle)
            label("Custom Content Tool for The Ponies")
        }
        hbox {
            padding = insets(40, 20)
            label("Create a...") {
                addClass(Styles.windowTitle)
            }
        }
        hbox(30) {
            padding = insets(40, 10)
            mainMenuButtonBox(this, "Wall Texture", Icons.wallpaper)
            mainMenuButtonBox(this, "Floor Texture", Icons.floor) {
                replaceWith(FloorEditorView())
            }
            mainMenuButtonBox(this, "Roof Texture", Icons.roof) {
                replaceWith(RoofEditorView())
            }
            mainMenuButtonBox(this, "Terrain Texture", Icons.terrain)
            mainMenuButtonBox(this, "Object", Icons.objects) {
                replaceWith(FurnitureEditorView())
            }
        }
        hbox(0, Pos.CENTER) {
            padding = insets(60, 60, 10, 60)
            label("More text may or may not be added here") {
                addClass(Styles.subtleText)
            }
        }
        hbox {
            padding = insets(10)
            button("Load...") {
                action {
                    when (val model = LoadController().openLoadDialog()) {
                        is Floor -> replaceWith(FloorEditorView(model))
                        is Roof -> replaceWith(RoofEditorView(model))
                        is Furniture -> replaceWith(FurnitureEditorView(model))
                    }
                }
            }
        }
    }

    private fun mainMenuButtonBox(parent: Parent, name: String, icon: String, action: () -> Unit = {}) = with(parent) {
        vbox(10) {
            addClass(Styles.mainMenuButton)
            prefWidth = 120.0
            alignment = Pos.CENTER
            hbox(0, Pos.CENTER) {
                svgpath(icon)
                prefHeight = 80.0
            }
            label(name)
            onMouseClicked = EventHandler {
                action()
            }
        }
    }
}