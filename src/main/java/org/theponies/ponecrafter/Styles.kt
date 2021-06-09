package org.theponies.ponecrafter

import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val subtleText by cssclass()
        val mainMenuButton by cssclass()
        val svgPath by csselement("SVGPath")
        val windowTitle by cssclass()
        val editorFieldsSection by cssclass()

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
        val roundCorners = mixin {
            borderRadius += box(20.px)
            backgroundRadius += box(20.px)
        }
        root {
            +standardText
            backgroundColor += background
        }
        button {
            +standardText
            +roundCorners
            backgroundColor += buttonColor
            padding = box(10.px, 20.px)
        }
        label {
            +standardText
        }
        textField {
            +standardText
            +roundCorners
            backgroundColor += textFieldColor
            borderColor += box(textFieldBorder)
        }
        textArea {
            +standardText
            +roundCorners
            backgroundColor += textFieldColor
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
        checkBox {
            +standardText
            box {
                backgroundColor += textFieldColor
            }
            and(selected) {
                mark {
                    backgroundColor += textColor
                }
            }
        }
        comboBox {
            +roundCorners
            backgroundColor += textFieldColor
            listCell {
                +roundCorners
                text {
                    fill = textColor
                }
                and(hover) {
                    text {
                        fill = clickablePressedColor
                    }
                }
                backgroundColor += textFieldColor
            }
            arrow {
                backgroundColor += textColor
            }
            comboBoxPopup {
                listView {
                    backgroundColor += textFieldColor
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
        editorFieldsSection {
            +roundCorners
            backgroundColor += textFieldColor
            padding = box(10.px)
            label {
                fontSize = 14.px
            }
            textField {
                backgroundColor += c("#193EA7")
                fontSize = 14.px
            }
            checkBox {
                fontSize = 14.px
                box {
                    backgroundColor += c("#193EA7")
                }
                and(selected) {
                    mark {
                        backgroundColor += textColor
                    }
                }
            }
        }
    }
}