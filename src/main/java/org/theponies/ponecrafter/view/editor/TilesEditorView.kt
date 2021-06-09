package org.theponies.ponecrafter.view.editor

import javafx.beans.InvalidationListener
import javafx.collections.ObservableList
import javafx.scene.Parent
import javafx.scene.image.Image
import org.theponies.ponecrafter.model.Vector2
import org.theponies.ponecrafter.util.IntStringConverter
import tornadofx.*

class TilesEditorView(private val tilesList: ObservableList<Vector2>) : View("Tile Footprint Editor") {
    private val tileEnabledImage = Image(javaClass.getResourceAsStream("/images/controls/tileEnabled.png"))
    private val tileDisabledImage = Image(javaClass.getResourceAsStream("/images/controls/tileDisabled.png"))

    override val root = vbox {
        val tilesWidth = (tilesList.maxBy { it.x }?.x ?: 0) + 1
        val tilesHeight = (tilesList.maxBy { it.y }?.y ?: 0) + 1
        val tilesWideProperty = property(tilesWidth).fxProperty
        val tilesHighProperty = property(tilesHeight).fxProperty

        prefWidth = 220.0
        prefHeight = 280.0
        padding = insets(10)
        val tilesParent = vbox()

        tilesWideProperty.addListener { _, _, _ ->
            tilesParent.clear()
            createTilesPane(tilesParent, tilesWideProperty.value, tilesHighProperty.value)
        }
        tilesHighProperty.addListener { _, _, _ ->
            tilesParent.clear()
            createTilesPane(tilesParent, tilesWideProperty.value, tilesHighProperty.value)
        }
        tilesList.addListener(InvalidationListener {
            tilesParent.clear()
            createTilesPane(tilesParent, tilesWideProperty.value, tilesHighProperty.value)
        })

        createTilesPane(tilesParent, tilesWideProperty.value, tilesHighProperty.value)

        hbox {
            textfield(tilesWideProperty, IntStringConverter())
            textfield(tilesHighProperty, IntStringConverter())
        }
    }

    private fun createTilesPane(parent: Parent, width: Int, height: Int) = with(parent) {
        gridpane {
            prefWidth = 200.0
            prefHeight = 200.0
            for (y in height - 1 downTo 0) {
                row {
                    repeat(width) { x ->
                        pane {
                            val vector = Vector2(x, y)
                            val image = if (tilesList.contains(vector)) tileEnabledImage else tileDisabledImage
                            imageview(image) {
                                fitWidth = 200.0 / width
                                fitHeight = 200.0 / height
                            }
                            setOnMouseClicked {
                                if (tilesList.contains(vector)) {
                                    tilesList.removeAll(vector)
                                } else {
                                    tilesList.add(vector)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}