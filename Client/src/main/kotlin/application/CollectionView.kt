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
            text = GUI.rb.getString("collectionView.title")
            style = "-fx-font-size: 32px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
            x = 100.0
            y = 124.0
        }

        tableview(controller.collection) {
            updateCollection(console, controller)
            column(GUI.rb.getString("collectionView.table.id"), SpaceMarine::getId)
            column(GUI.rb.getString("collectionView.table.name"), SpaceMarine::getName)
            column(GUI.rb.getString("collectionView.table.coordinates"), SpaceMarine::getCoordinates)
            column(GUI.rb.getString("collectionView.table.creationDate"), SpaceMarine::getCreationDate)
            column(GUI.rb.getString("collectionView.table.health"), SpaceMarine::getHealth)
            column(GUI.rb.getString("collectionView.table.loyalty"), SpaceMarine::getLoyalty)
            column(GUI.rb.getString("collectionView.table.category"), SpaceMarine::getCategory)
            column(GUI.rb.getString("collectionView.table.meleeWeapon"), SpaceMarine::getWeapon)
            column(GUI.rb.getString("collectionView.table.chapter"), SpaceMarine::getChapter)

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
            text = GUI.rb.getString("collectionView.button.update")
            style = "-fx-background-color: #ffffff; -fx-font-family: 'IBM Plex Sans'; -fx-font-size: 16px; -fx-fill: #000000; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 20px;"
            layoutX = 92.0
            layoutY = 756.0

            setOnMouseClicked {
                updateCollection(console, controller)
            }
        }

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
                            replaceWith(AuthView(AuthMode.LOGIN))
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
            replaceWith(AuthView(AuthMode.LOGIN))
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