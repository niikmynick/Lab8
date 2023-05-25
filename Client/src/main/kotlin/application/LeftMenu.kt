package application


import javafx.geometry.Insets
import tornadofx.*

class LeftMenu : View() {
    val console = GUI.console

    override val root = vbox(20.0) {
        style = "-fx-background-color: #fff;"
        setPrefSize(72.0, 836.0)
        layoutX = 0.0
        layoutY = 64.0
        padding = Insets(20.0, 0.0, 0.0, 12.0)

        button {
            imageview("file:Client/src/main/resources/home_icon.png") {
                fitHeight = 25.0
                fitWidth = 30.0
            }
            style = "-fx-background-color: transparent;"
            setOnMouseClicked {
                replaceWith(HomeView::class)
            }
        }

        button {
            imageview("file:Client/src/main/resources/console_icon.png") {
                fitHeight = 30.0
                fitWidth = 30.0
            }
            style = "-fx-background-color: transparent;"
            setOnMouseClicked {
                replaceWith(ConsoleView::class)
            }
        }

        button {
            imageview("file:Client/src/main/resources/collection_icon.png") {
                fitHeight = 30.0
                fitWidth = 30.0
            }
            style = "-fx-background-color: transparent;"
            setOnMouseClicked {
                replaceWith(CollectionView::class)
            }
        }

    }

}