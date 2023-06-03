package application.views

import application.AuthMode
import application.GUI
import application.HeadBar
import application.LeftMenu
import javafx.scene.image.Image
import javafx.scene.layout.*
import javafx.scene.paint.Color
import tornadofx.*

class MapView() : View() {

    val controller =  find(SpaceMarineController::class)
    val spaceMarineImage = Image("file:Client/src/main/resources/miniSpaceMarine.png", 24.0, 30.0, true, true)

    override val root = pane {
        clear()

        style = "-fx-background-color: #ffffff;"

        add(HeadBar(true, this@MapView).root)

        add(LeftMenu(this@MapView).root)

        text {
            style = "-fx-font-size: 32px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
            x = 100.0
            y = 124.0
        }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("mapView.title"))

        pane {
            layoutX = 92.0
            layoutY = 146.0
            prefWidth = 1280.0
            prefHeight = 712.0
            usePrefSize = true
            //fill = ImagePattern(Image("file:Client/src/main/resources/World_map_blank_without_borders.png", this@rectangle.width, this@rectangle.height, true, true))
            //style = "-fx-stroke: black; -fx-stroke-width: 1px;"
            border = Border(BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii(20.0), BorderWidths(1.0)))
            if (GUI.console.username.isNotEmpty()) {
                try {
                    controller.updateCollection(GUI.console)
                } catch (e: Exception) {
                    replaceWith(
                        AuthView(AuthMode.LOGIN),
                        ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT)
                    )
                }
            }

            fun setMap() {
                val world = imageview(Image("file:Client/src/main/resources/World_map_blank_without_borders.png", this.width, this.height, true, true))

                for (sm in controller.observableCollection) {
                    val imageview = imageview(spaceMarineImage)
                    imageview.setOnMouseClicked {
                        openInternalWindow(ChangingFormView(controller, sm.getSpaceMarine()))
                    }

                    imageview.xProperty().bind(sm.getCoordinates().getX().toProperty())
                    imageview.yProperty().bind(sm.getCoordinates().getY().toProperty())
                    imageview.tooltip {
                        text = "${sm.getId()}\n${sm.getName()}\n${sm.getCoordinates()}\n${sm.getAuthor()}"
                    }
                    this.add(imageview)
                }

                setMap()

                button {
                    style = "-fx-background-color: #ffffff; -fx-font-family: 'IBM Plex Sans'; -fx-font-size: 16px; -fx-fill: #000000; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 20px;"
                    layoutX = 1130.0
                    layoutY = 0.0

                    setOnMouseClicked {
                        try {
                            controller.updateCollection(GUI.console)
                            clear()
                            setMap()
                        } catch (e:Exception) {
                            replaceWith(AuthView(AuthMode.LOGIN), ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                        }
                    }
                }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.button.update"))
            }

//            for (sm in controller.observableCollection) {
////                val imageview = imageview(spaceMarineImage)
////                this.add(imageview)
////                imageview.xProperty().bind(sm.getCoordinates().getX().toProperty())
////                imageview.yProperty().bind(sm.getCoordinates().getY().toProperty())
//                val circle = Circle(20.0, Color.RED)
//                this.add(circle)
//                println("added SpaceMarine")
//            }
        }
    }
}