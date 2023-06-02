package application

import basicClasses.SpaceMarine
import clientUtils.Console
import exceptions.NotAuthorized
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.json.Json
import tornadofx.*

class CollectionView : View() {
    val console = GUI.console

    override val root = pane {
        clear()

        style = "-fx-background-color: #ffffff;"

        add(HeadBar(true, this@CollectionView).root)

        add(LeftMenu(this@CollectionView).root)

        val controller = find(SpaceMarineController::class)

        text {
            style = "-fx-font-size: 32px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
            x = 100.0
            y = 124.0
        }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.title"))

        tableview(controller.collection) {
            updateCollection(console, controller)
            column("collectionView.table.id", SpaceMarine::getId).textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.id"))
            column("collectionView.table.name", SpaceMarine::getName).textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.name"))
            column("collectionView.table.coordinates", SpaceMarine::getCoordinates).textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.coordinates"))
            column("collectionView.table.creationDate", SpaceMarine::getCreationDate).textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.creationDate"))
            column("collectionView.table.health", SpaceMarine::getHealth).textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.health"))
            column("collectionView.table.loyalty", SpaceMarine::getLoyalty).textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.loyalty"))
            column("collectionView.table.category", SpaceMarine::getCategory).textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.category"))
            column("collectionView.table.meleeWeapon", SpaceMarine::getWeapon).textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.meleeWeapon"))
            column("collectionView.table.chapter", SpaceMarine::getChapter).textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.chapter"))

            enableCellEditing()
            bindSelected(controller.model)
            smartResize()

            style = "-fx-background-color: #ffffff; -fx-font-family: 'IBM Plex Sans'; -fx-font-size: 16px; -fx-fill: #000000;"
            layoutX = 92.0
            layoutY = 146.0
            columnResizePolicy = SmartResize.POLICY
            setPrefSize(1100.0, 550.0)
        }

        button {
            style = "-fx-background-color: #ffffff; -fx-font-family: 'IBM Plex Sans'; -fx-font-size: 16px; -fx-fill: #000000; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 20px;"
            layoutX = 92.0
            layoutY = 756.0

            setOnMouseClicked {
                updateCollection(console, controller)
            }
        }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.button.update"))

    }

    private fun updateCollection(server: Console, controller: SpaceMarineController) {
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        try {
            coroutineScope.launch {
                withTimeout(5000) {
                    var map = mapOf<String, String>()
                    try {
                        map = server.loadCollection()
                    } catch (e: NotAuthorized) {
                        runLater {
                            replaceWith(AuthView(AuthMode.LOGIN), ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                        }
                    }
                    val input = map.keys.asSequence()
                    val collection = input.map {
                        Json.decodeFromString(SpaceMarine.serializer(), it)
                    }
                    controller.collection.setAll(collection.toList().toObservable())
                }
            }

        } catch (e: NotAuthorized) {
            replaceWith(AuthView(AuthMode.LOGIN), ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
        }
    }

    class SpaceMarineModel() : ItemViewModel<SpaceMarine>() {
        val id = bind {item?.getId()?.toProperty()}
        val name = bind {item?.getName()?.toProperty()}
        val coordinates = bind {item?.getCoordinates()?.toProperty()}
        val creationDate = bind {item?.getCreationDate()?.toProperty()}
        val health = bind {item?.getHealth()?.toProperty()}
        val loyal = bind {item?.getLoyalty()?.toProperty()}
        val category = bind {item?.getCategory()?.toProperty()}
        val meleeWeapon = bind {item?.getWeapon()?.toProperty()}
        val chapter = bind {item?.getChapter()?.toProperty()}
    }

    class SpaceMarineController() : Controller() {
        var collection = listOf<SpaceMarine>().toObservable()
        val model = SpaceMarineModel()
    }

}