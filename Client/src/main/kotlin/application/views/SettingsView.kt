package application.views

import application.GUI
import application.HeadBar
import application.LeftMenu
import tornadofx.*

class SettingsView : View() {
    override val root = anchorpane {

        style = "-fx-background-color: #ffffff;"
        add(HeadBar(true, this@SettingsView).root)

        add(LeftMenu(this@SettingsView).root)

        text {
            style = "-fx-font-size: 32px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
            x = 100.0
            y = 124.0
        }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("settingsView.title"))

        pane {
            layoutX = 92.0
            layoutY = 146.0
            setPrefSize(500.0, 250.0)
            style =
                "-fx-background-color: #ffffff; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px;"

            text {
                style =
                    "-fx-text-alignment: left; -fx-font-size: 24px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000;"
                layoutX = 28.0
                layoutY = 52.0

            }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("settingsView.account.title"))

            tilepane {
                layoutX = 28.0
                layoutY = 82.0
                hgap = 30.0
                vgap = 30.0

                button {
                    style =
                        "-fx-alignment: center; -fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"

                    setOnMouseClicked {
                        //TODO
                    }
                }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("settingsView.account.changeUsername"))

                button {
                    style =
                        "-fx-alignment: center; -fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"

                    setOnMouseClicked {
                        //TODO
                    }
                }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("settingsView.account.changePassword"))
            }

        }

        //TODO: Second pane for language settings
        //TODO: Third pane for saving username and password locally?


    }
}