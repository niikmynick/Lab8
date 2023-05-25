package application

import tornadofx.*

class SettingsView : View() {
    override val root = vbox {

        add(HeadBar(true).root)

        add(LeftMenu().root)

        text {
            text = "Settings"
            style = "-fx-font-size: 24px; -fx-font_family: 'IBM Plex Sans';"
        }

        text {
            text = "Change username"
            style = "-fx-font-size: 18px; -fx-font_family: 'IBM Plex Sans';"
        }

        text {
            text = "Change password"
            style = "-fx-font-size: 18px; -fx-font_family: 'IBM Plex Sans';"
        }
    }
}