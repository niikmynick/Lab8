package application

import tornadofx.*

class HomeView : View() {

    val console = GUI.console

    override val root = anchorpane {

        add(HeadBar(true).root)

        add(LeftMenu().root)

        add(MenuBlock("Console", "Manage your collection", "Open editor", 72.0, 84.0, ButtonOption.CONSOLE, console))

        add(MenuBlock("Settings", "Set up your account and application preferences", "Open settings", 528.0, 84.0, ButtonOption.SETTINGS, console))

        add(MenuBlock("Collection", "Check out all existing Space Marines", "Go to the collection", 984.0, 84.0, ButtonOption.COLLECTION, console))

        add(MenuBlock("Fun stuff", "Racoon is here", "Open the page", 72.0, 315.0, ButtonOption.RACOON, console))
    }

}