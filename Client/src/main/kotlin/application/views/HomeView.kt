package application.views

import application.GUI
import application.HeadBar
import application.LeftMenu
import application.MenuBlock
import tornadofx.*

class HomeView() : View() {

    val console = GUI.console

    override val root = anchorpane {
        clear()
        style = "-fx-background-color: #ffffff; "
        add(HeadBar(true, this@HomeView).root)

        add(LeftMenu(this@HomeView).root)

        add(MenuBlock(this@HomeView,"homeView.title.console", "homeView.description.console", "homeView.button.console", 72.0, 84.0, ConsoleView()))

        add(MenuBlock(this@HomeView,"homeView.title.settings", "homeView.description.settings", "homeView.button.settings", 528.0, 84.0, SettingsView()))

        add(MenuBlock(this@HomeView,"homeView.title.collection", "homeView.description.collection", "homeView.button.collection", 984.0, 84.0, CollectionView()))

        add(MenuBlock(this@HomeView,"homeView.title.funStuff", "homeView.description.funStuff", "homeView.button.funStuff", 72.0, 315.0, RacoonView()))

        add(MenuBlock(this@HomeView,"homeView.title.map", "homeView.description.map", "homeView.button.map", 528.0, 315.0, MapView()))
    }

}