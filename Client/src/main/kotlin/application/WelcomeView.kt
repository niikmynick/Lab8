package application

import javafx.geometry.Pos
import tornadofx.*

class WelcomeView() : View() {

    val console = GUI.console

    override val root = vbox {
        add(HeadBar(false, this@WelcomeView).root)

        pane {
            alignment = Pos.CENTER
            setPrefSize(1440.0, 836.0)
            style = "-fx-background-color: #ffffff; "
            layoutX = 0.0
            layoutY = 64.0

            text {
                style =
                    "-fx-text-alignment: center; -fx-font-size: 24px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
                wrappingWidth = 600.0
                x = 420.0
                y = 388.0
            }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("welcomeView.welcomeMessage"))

            button {
                style =
                    "-fx-alignment: center; -fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
                layoutX = 640.0
                layoutY = 470.0
                setPrefSize(160.0, 42.0)

                setOnMouseEntered {
                    style =
                        "-fx-text-fill: #555; -fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #777; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
                }

                setOnMouseExited {
                    style =
                        "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
                }

                setOnMouseClicked {
                    replaceWith(AuthView(AuthMode.LOGIN))
                }
            }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("welcomeView.authorize"))
        }
    }

}