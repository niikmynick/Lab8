package application

import tornadofx.*

class HomeView : View() {

    val console = GUI.console

    override val root = anchorpane {
        clear()
        style = "-fx-background-color: #ffffff; "
        add(HeadBar(true, this@HomeView).root)

        add(LeftMenu(this@HomeView).root)

        add(MenuBlock(this@HomeView, GUI.rb.getString("homeView.title.console"), GUI.rb.getString("homeView.description.console"), GUI.rb.getString("homeView.button.console"), 72.0, 84.0, ConsoleView()))

        add(MenuBlock(this@HomeView,GUI.rb.getString("homeView.title.settings"), GUI.rb.getString("homeView.description.settings"), GUI.rb.getString("homeView.button.settings"), 528.0, 84.0, SettingsView()))

        add(MenuBlock(this@HomeView,GUI.rb.getString("homeView.title.collection"), GUI.rb.getString("homeView.description.collection"), GUI.rb.getString("homeView.button.collection"), 984.0, 84.0, CollectionView()))

        add(MenuBlock(this@HomeView,GUI.rb.getString("homeView.title.funStuff"), GUI.rb.getString("homeView.description.funStuff"), GUI.rb.getString("homeView.button.funStuff"), 72.0, 315.0, RacoonView()))
    }

}