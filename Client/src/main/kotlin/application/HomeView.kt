package application

import tornadofx.*

class HomeView : View() {

    val console = GUI.console

    override val root = anchorpane {
        clear()

        add(HeadBar(true).root)

        add(LeftMenu(this@HomeView).root)

        add(MenuBlock(this@HomeView,"Console", "Manage your collection", "Open editor", 72.0, 84.0, ConsoleView(), console))

        add(MenuBlock(this@HomeView,"Settings", "Set up your account and application preferences", "Open settings", 528.0, 84.0, SettingsView(), console))

        add(MenuBlock(this@HomeView,"Collection", "Check out all existing Space Marines", "Go to the collection", 984.0, 84.0, CollectionView(), console))

        add(MenuBlock(this@HomeView,"Fun stuff", "Racoon is here", "Open the page", 72.0, 315.0, RacoonView(), console))
    }

}