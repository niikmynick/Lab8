package application

import application.views.CollectionView
import application.views.ConsoleView
import application.views.HomeView
import javafx.geometry.Insets
import tornadofx.*

class LeftMenu(root: View) : View() {

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
                if (root::class != HomeView::class) {
                    root.replaceWith(GUI.viewsObjectPool.homeView, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                }
            }
        }

        button {
            imageview("file:Client/src/main/resources/console_icon.png") {
                fitHeight = 30.0
                fitWidth = 30.0
            }
            style = "-fx-background-color: transparent;"
            setOnMouseClicked {
                if (root::class != ConsoleView::class) {
                root.replaceWith(GUI.viewsObjectPool.consoleView, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                }
            }
        }

        button {
            imageview("file:Client/src/main/resources/collection_icon.png") {
                fitHeight = 30.0
                fitWidth = 30.0
            }
            style = "-fx-background-color: transparent;"
            setOnMouseClicked {
                if (root::class != CollectionView::class) {
                root.replaceWith(GUI.viewsObjectPool.collectionView, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                }
            }
        }

    }

}