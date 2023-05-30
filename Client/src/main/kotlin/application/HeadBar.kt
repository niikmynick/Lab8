package application

import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.scene.shape.SVGPath
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
                layoutX = 1330.0
                layoutY = 27.0
                fitHeight = 20.0
                fitWidth = 20.0

                setOnMouseClicked {
                    replaceWith(SettingsView::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                }
            }
        }

        /*
        Exit button
         */
        region {
            shape = svgpath {
                content = "M18.8,16l5.5-5.5c0.8-0.8,0.8-2,0-2.8l0,0C24,7.3,23.5,7,23,7c-0.5,0-1,0.2-1.4,0.6L16,13.2l-5.5-5.5  c-0.8-0.8-2.1-0.8-2.8,0C7.3,8,7,8.5,7,9.1s0.2,1,0.6,1.4l5.5,5.5l-5.5,5.5C7.3,21.9,7,22.4,7,23c0,0.5,0.2,1,0.6,1.4  C8,24.8,8.5,25,9,25c0.5,0,1-0.2,1.4-0.6l5.5-5.5l5.5,5.5c0.8,0.8,2.1,0.8,2.8,0c0.8-0.8,0.8-2.1,0-2.8L18.8,16z"
                layoutX = 1380.0
                layoutY = 20.0
                fill = Color.WHITE

                setOnMouseClicked {
                    close()
                }
            }
            setMaxSize(20.0,20.0)
        }


    }

}