package org.theponies.ponecrafter.view.editor

import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Parent
import javafx.scene.layout.VBox
import tornadofx.*

abstract class TabEditorView(name: String, private val headerIcon: String) : BaseEditorView(name) {
    private val contentRoot = VBox()
    private val currentTabProperty = SimpleObjectProperty<Parent>(VBox())
    private var currentTab: Parent by currentTabProperty

    fun createRoot() = form {
        padding = insets(20)
        val tabs = createTabs()
        currentTab = tabs.values.first()
        addHeader(this, tabs)
        add(contentRoot)
        addFooter(this)
        contentRoot.add(currentTab)
    }

    private fun addHeader(parent: Parent, tabs: Map<String, Parent>) = createHeaderWithTabButtons(parent, name, headerIcon, tabs)

    abstract fun addFooter(parent: Parent): Parent

    abstract fun createTabs(): Map<String, Parent>

    private fun createHeaderWithTabButtons(parent: Parent, name: String, icon: String, tabs: Map<String, Parent>) = createHeader(parent, name, icon) {
        it.hbox(5) {
            tabs.forEach { (name, tab) ->
                button(name) {
                    action {
                        currentTab.removeFromParent()
                        currentTab = tab
                        contentRoot.add(tab)
                    }
                    disableWhen(currentTabProperty.isEqualTo(tab))
                }
            }
        }
    }
}