package org.theponies.ponecrafter

import javafx.stage.Stage
import org.theponies.ponecrafter.view.MenuView
import tornadofx.*

class PoneCrafterApp : App(MenuView::class, Styles::class) {
    override fun start(stage: Stage) {
        stage.isResizable = false
        super.start(stage)
    }
}
