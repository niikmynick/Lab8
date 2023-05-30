package application

import clientUtils.Console
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.stage.StageStyle
import tornadofx.*


class GUI : App() {

    companion object {
        val console = Console("localhost", 8061)
    }

    override fun start(stage: Stage) {

        stage.title = "Space Marine Client"
        stage.isResizable = false
        stage.initStyle(StageStyle.TRANSPARENT)

        val image = Image("file:Client/src/main/resources/app_logo.jpg")
        stage.icons.add(image)

        val primaryScene = Scene(WelcomeView().root, 1440.0, 900.0)
        stage.scene = primaryScene
        stage.scene.fill = Color.TRANSPARENT;

        stage.show()
    }
}
