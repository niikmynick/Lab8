package application

import javafx.scene.image.Image
import tornadofx.*
class HeadBar(needAccountIcon: Boolean) : View() {

    override val root = pane {
        setPrefSize(1440.0, 64.0)
        style = "-fx-background-color: #000000; -fx-border-radius: 20px;"

        text {
            text = "Space Marine Collection"
            style =
                "-fx-text-alignment: left; -fx-vertical-alignment: top; -fx-font-size: 24px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #ffffff;"
            x = 20.0
            y = 40.0
        }

        if (needAccountIcon) {
            imageview {
                image = Image("file:Client/src/main/resources/account_icon.png")
                style = "-fx-text-alignment: left; -fx-vertical-alignment: top;"
                layoutX = 1380.0
                layoutY = 20.0
                fitHeight = 20.0
                fitWidth = 20.0

                setOnMouseClicked {
                    replaceWith(SettingsView::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                }
            }
        }
    }

}