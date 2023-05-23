package application

import clientUtils.Console
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.stage.StageStyle
import tornadofx.*


class GUI : App(MainView::class) {

    override fun start(stage: Stage) {
        stage.title = "Space Marine Client"

        val image = Image("file:Client/src/main/resources/app_logo.jpg")
        stage.icons.add(image)

        val primaryScene = Scene(MainView().root, 1440.0, 900.0)
        stage.scene = primaryScene
        stage.isResizable = false
        stage.scene.fill = Color.TRANSPARENT;
        stage.initStyle(StageStyle.TRANSPARENT)

        stage.show()
    }
}
