package application

import tornadofx.*

class WelcomeView() : View() {

    val console = GUI.console

    override val root = anchorpane {

        add(HeadBar(false).root)

        pane {
            setPrefSize(1440.0, 836.0)
            style = "-fx-background-color: #ffffff; "
            layoutX = 0.0
            layoutY = 64.0

            text {
                text = "Hello,\nBefore we start you have to log in to your account"
                style =
                    "-fx-text-alignment: left; -fx-font-size: 24px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
                wrappingWidth = 600.0
                x = 420.0
                y = 388.0
            }

            button {
                text = "Authorize"
                style =
                    "-fx-alignment: center; -fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
                layoutX = 420.0
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
            }
        }
    }

}