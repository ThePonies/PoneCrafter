package org.theponies.ponecrafter

import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val subtleText by cssclass()

        val background = c("#01478D")
        val textFieldColor = c("#092E87")
        val textFieldBorder: Color = Color.TRANSPARENT
        val buttonColor = c("#07638C")
        val textColor = c("#FFFFFF")
        val defaultFont = loadFont("/fonts/LexieReadable-Regular.ttf", 12)!!
    }

    init {
        val standardText = mixin {
            textFill = textColor
            font = defaultFont
        }
        root {
            +standardText
            backgroundColor += background
        }
        button {
            +standardText
            backgroundColor += buttonColor
        }
        label {
            +standardText
        }
        textField {
            +standardText
            backgroundColor += textFieldColor
            borderColor += cssBox(textFieldBorder)
        }
        textArea {
            +standardText
            backgroundColor += textFieldColor
            focusColor = Color.TRANSPARENT
            textBoxBorder = textFieldBorder
            content {
                backgroundColor += textFieldColor
            }
        }
        subtleText {
            font = defaultFont
            textFill = c("#7CA0C4")
        }
    }
}

private fun <T>cssBox(all: T) = CssBox(all, all, all, all)
