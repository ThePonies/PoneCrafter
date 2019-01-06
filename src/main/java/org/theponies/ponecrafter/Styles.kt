package org.theponies.ponecrafter

import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val background = c("#01478D")
        val textFieldColor = c("#092E87")
        val textFieldBorder: Color = Color.TRANSPARENT
        val buttonColor = c("#07638C")
        val textColor = c("#FFFFFF")
    }

    init {
        val standardText = mixin {
            textFill = textColor
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
    }
}

private fun <T>cssBox(all: T) = CssBox(all, all, all, all)
