package org.theponies.ponecrafter

import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val subtleText by cssclass()
        val mainMenuButton by cssclass()
        val svgPath by csselement("SVGPath")
        val windowTitle by cssclass()

        val background = c("#01478D")
        val textFieldColor = c("#092E87")
        val textFieldBorder: Color = Color.TRANSPARENT
        val buttonColor = c("#07638C")
        val textColor = c("#FFFFFF")
        val clickableHoverColor = c("#FFF9E5")
        val clickablePressedColor = c("#60D2FF")
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
            borderRadius += box(20.px)
            backgroundRadius += box(20.px)
        }
        label {
            +standardText
        }
        textField {
            +standardText
            backgroundColor += textFieldColor
            borderColor += box(textFieldBorder)
            borderRadius += box(20.px)
            backgroundRadius += box(20.px)
        }
        textArea {
            +standardText
            backgroundColor += textFieldColor
            borderRadius += box(20.px)
            backgroundRadius += box(20.px)
            scrollPane {
                backgroundColor += Color.TRANSPARENT
                viewport {
                    backgroundColor += Color.TRANSPARENT
                }
                content {
                    backgroundColor += Color.TRANSPARENT
                }
            }
        }
        subtleText {
            font = defaultFont
            textFill = c("#7CA0C4")
        }
        mainMenuButton {
            svgPath {
                fill = textColor
                borderWidth += box(0.px)
            }
            and(hover) {
                label {
                    textFill = clickableHoverColor
                }
                svgPath {
                    fill = clickableHoverColor
                }
            }
            and(pressed) {
                label {
                    textFill = clickablePressedColor
                }
                svgPath {
                    fill = clickablePressedColor
                }
            }
        }
        windowTitle {
            fontSize = 22.px
        }
    }
}