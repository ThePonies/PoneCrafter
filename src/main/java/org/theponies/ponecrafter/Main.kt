package org.theponies.ponecrafter

import javafx.scene.Scene
import javafx.stage.Stage
import org.theponies.ponecrafter.cli.Cli
import org.theponies.ponecrafter.view.MenuView
import tornadofx.*

fun main(args: Array<String>) {
    if (args.isNotEmpty() && args[0] == "cli") {
        Cli().execute(args)
    } else {
        launch<PoneCrafterApp>(args)
    }
}

class PoneCrafterApp : App(MenuView::class, Styles::class) {
    override fun start(stage: Stage) {
        stage.isResizable = false
        super.start(stage)
    }

    override fun createPrimaryScene(view: UIComponent) = Scene(view.root, 800.0, 600.0)
}
