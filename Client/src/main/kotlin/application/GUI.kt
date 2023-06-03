package application

import application.views.ViewsObjectPool
import clientUtils.Console
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.stage.StageStyle
import tornadofx.*
import utils.JsonCreator
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


class GUI : App() {

    init {
        RESOURCE_FACTORY.setResources(ResourceBundle.getBundle(RESOURCE_NAME, Locale(settings.language)))
    }

    companion object {
        val RESOURCE_NAME = "messages"
        val RESOURCE_FACTORY = ObservableResourceFactory()
        var settings = try {
            JsonCreator().stringToObject(Files.readAllLines(Paths.get("Client/src/main/resources/settings.json"))[0])
        } catch (e: Exception) {
            Settings("en_US", listOf("en_US","es_HN","it_IT", "ro_RO","ru_RU"), "localhost", 8061)
        }
        var console = Console(settings.host, settings.port)
        val viewsObjectPool = ViewsObjectPool()
    }

    override fun start(stage: Stage) {

        stage.title = "Space Marine Client"
        stage.isResizable = false
        stage.initStyle(StageStyle.TRANSPARENT)

        val image = Image("file:Client/src/main/resources/app_logo.jpg")
        stage.icons.add(image)

        val primaryScene = Scene(viewsObjectPool.welcomeView.root, 1440.0, 900.0)
        stage.scene = primaryScene
        stage.scene.fill = Color.TRANSPARENT

        stage.show()
    }
}
