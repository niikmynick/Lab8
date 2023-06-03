package application.views

import application.*
import basicClasses.SpaceMarine
import exceptions.NotAuthorized
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
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

        tableview(controller.observableCollection) {
            try {
                controller.updateCollection(console)
            } catch (e:Exception) {
                replaceWith(AuthView(AuthMode.LOGIN), ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
            }

            val idColumn = column("collectionView.table.id", SpaceMarineController.SpaceMarineWithAuthor::getId)
            idColumn.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.id"))

            val nameColumn = column("collectionView.table.name", SpaceMarineController.SpaceMarineWithAuthor::getName)
            nameColumn.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.name"))
            nameColumn.makeEditable()

            val coordinatesColumn = column("collectionView.table.coordinates", SpaceMarineController.SpaceMarineWithAuthor::getCoordinates)
            coordinatesColumn.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.coordinates"))
            //coordinatesColumn.makeEditable()

            val creationDateColumn = column("collectionView.table.creationDate", SpaceMarineController.SpaceMarineWithAuthor::getCreationDate)
            creationDateColumn.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.creationDate"))

            val healthColumn = column("collectionView.table.health", SpaceMarineController.SpaceMarineWithAuthor::getHealth)
            healthColumn.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.health"))
            //healthColumn.converter(DecimaleConverter())

            val loyaltyColumn = column("collectionView.table.loyalty", SpaceMarineController.SpaceMarineWithAuthor::getLoyalty)
            loyaltyColumn.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.loyalty"))
            loyaltyColumn.makeEditable()

            val categoryColumn = column("collectionView.table.category", SpaceMarineController.SpaceMarineWithAuthor::getCategory)
            categoryColumn.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.category"))
            //categoryColumn.makeEditable()

            val meleeWeaponColumn = column("collectionView.table.meleeWeapon", SpaceMarineController.SpaceMarineWithAuthor::getWeapon)
            meleeWeaponColumn.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.meleeWeapon"))
            //meleeWeaponColumn.makeEditable()

            val chapterColumn = column("collectionView.table.chapter", SpaceMarineController.SpaceMarineWithAuthor::getChapter)
            chapterColumn.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.chapter"))
            //chapterColumn.makeEditable()

            val authorColumn = column("collectionView.table.author", SpaceMarineController.SpaceMarineWithAuthor::getAuthor)
            authorColumn.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.table.author"))

            enableCellEditing()
            bindSelected(controller.model)
            smartResize()
            regainFocusAfterEdit()

            onEditCommit {
                if (console.username == it.getAuthor()) {
                    try {
                        controller.spaceMarineEdit(console, it.getSpaceMarine(), "update")
                    } catch (e:Exception) {
                        replaceWith(AuthView(AuthMode.LOGIN), ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                    }
                } else {
                    val alert = alert(Alert.AlertType.ERROR,
                        GUI.RESOURCE_FACTORY.getResources()["collectionView.onEditError.header"],
                        GUI.RESOURCE_FACTORY.getResources()["collectionView.onEditError.content"],
                    ) {
                        //TODO: Style
                    }

                }

            }

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
                try {
                    controller.updateCollection(console)
                } catch (e:Exception) {
                    replaceWith(AuthView(AuthMode.LOGIN), ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                }
            }
        }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.button.update"))

        button {
            style = "-fx-background-color: #ffffff; -fx-font-family: 'IBM Plex Sans'; -fx-font-size: 16px; -fx-fill: #000000; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 20px;"
            layoutX = 292.0
            layoutY = 756.0
            enableWhen { controller.model.empty.not() and (controller.model.author.value == console.username)} // TODO: CHECK FOR AUTHOR

            setOnMouseClicked {
                val alert = alert(Alert.AlertType.CONFIRMATION,
                    GUI.RESOURCE_FACTORY.getResources()["collectionView.button.delete.alert.header"],
                    GUI.RESOURCE_FACTORY.getResources()["collectionView.button.delete.alert.content"],
                ) {
                    //TODO: Style
                    if (it == ButtonType.OK) {
                        try {
                            controller.spaceMarineEdit(console, controller.model.item.getSpaceMarine(), "remove")
                            controller.observableCollection.remove(controller.model.item)
                        } catch (e:Exception) {
                            replaceWith(AuthView(AuthMode.LOGIN), ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                        }

                    }
                }
            }
        }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("collectionView.button.delete"))

    }

}