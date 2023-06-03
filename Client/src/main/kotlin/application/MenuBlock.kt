package application

import tornadofx.*

class MenuBlock(root: View, title: String, description: String, buttonText: String, x: Double, y: Double, view: View) : View() {

    override val root = pane {
        setPrefSize(436.0, 211.0)
        style =
            "-fx-background-color: #ffffff; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px;"

        layoutX = x
        layoutY = y

        text {
            style =
                "-fx-text-alignment: left; -fx-font-size: 32px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000;"
            layoutX = 28.0
            layoutY = 52.0
        }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding(title))

        text {
            style =
                "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000;"
            layoutX = 28.0
            layoutY = 96.0
        }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding(description))

        button {
            style =
                "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000;"
            layoutX = 28.0
            layoutY = 140.0
            setPrefSize(160.0, 42.0)

            setOnMouseClicked {
                root.replaceWith(view, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
            }
        }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding(buttonText))
    }

}