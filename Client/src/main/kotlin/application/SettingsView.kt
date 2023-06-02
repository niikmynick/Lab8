package application

import tornadofx.*

class SettingsView : View() {
    override val root = anchorpane {

        style = "-fx-background-color: #ffffff;"
        add(HeadBar(true, this@SettingsView).root)

        add(LeftMenu(this@SettingsView).root)

        text {
            text = GUI.rb.getString("settingsView.title")
            style = "-fx-font-size: 32px; -fx-font_family: 'IBM Plex Sans';"
            x = 72.0
            y = 108.0
        }

        pane {
            layoutX = 72.0
            layoutY = 124.0
            setPrefSize(500.0, 250.0)
            style =
                "-fx-background-color: #ffffff; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px;"

            text {
                text = GUI.rb.getString("settingsView.account.title")
                style =
                    "-fx-text-alignment: left; -fx-font-size: 24px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000;"
                layoutX = 28.0
                layoutY = 52.0

            }

            tilepane {
                layoutX = 28.0
                layoutY = 82.0
                hgap = 30.0
                vgap = 30.0

                button {
                    text = GUI.rb.getString("settingsView.account.changeUsername")
                    style =
                        "-fx-alignment: center; -fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"

                    setOnMouseClicked {

                    }
                }

                button {
                    text = GUI.rb.getString("settingsView.account.changePassword")
                    style =
                        "-fx-alignment: center; -fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"

                    setOnMouseClicked {

                    }
                }
            }

        }



    }
}