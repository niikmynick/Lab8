package application.views

import application.GUI
import basicClasses.*
import javafx.scene.control.TextField
import tornadofx.*

class ChangingFormView(controller: SpaceMarineController, private val spaceMarine: SpaceMarine = SpaceMarine()) : View() {

    override val root = form {
        style = "-fx-background-color: #ffffff;"
        val textfields = mutableListOf<TextField>()
        val checkboxs = mutableListOf<javafx.scene.control.CheckBox>()
        val comboBoxes = mutableListOf<javafx.scene.control.ComboBox<Any>>()
        fieldset {
            field {
                val name = textfield(spaceMarine.getName())
                textfields.add(name)
            }.textProperty.bind(GUI.RESOURCE_FACTORY.getStringBinding("changingForm.name"))

            field {
                field {
                    val x = textfield(spaceMarine.getCoordinatesX().toString())
                    textfields.add(x)
                }.textProperty.bind(GUI.RESOURCE_FACTORY.getStringBinding("changingForm.X"))

                field {
                    val y = textfield(spaceMarine.getCoordinatesY().toString())
                    textfields.add(y)
                }.textProperty.bind(GUI.RESOURCE_FACTORY.getStringBinding("changingForm.Y"))

            }.textProperty.bind(GUI.RESOURCE_FACTORY.getStringBinding("changingForm.coordinates"))

            field {
                val health = textfield(spaceMarine.getHealth().toString())
                textfields.add(health)
            }.textProperty.bind(GUI.RESOURCE_FACTORY.getStringBinding("changingForm.health"))

            field {
                val loyal = checkbox(spaceMarine.getLoyalty().toString())
                checkboxs.add(loyal)
            }.textProperty.bind(GUI.RESOURCE_FACTORY.getStringBinding("changingForm.loyalty"))

            field {
                val category = combobox(spaceMarine.getCategory().toProperty(), AstartesCategory.values().toList())
                comboBoxes.add(category as javafx.scene.control.ComboBox<Any>)
            }.textProperty.bind(GUI.RESOURCE_FACTORY.getStringBinding("changingForm.category"))

            field {
                val weapon = combobox(spaceMarine.getWeapon().toProperty(), MeleeWeapon.values().toList())
                comboBoxes.add(weapon as javafx.scene.control.ComboBox<Any>)
            }.textProperty.bind(GUI.RESOURCE_FACTORY.getStringBinding("changingForm.meleeWeapon"))

            field {
                field("Name") {
                    val name = textfield(spaceMarine.getChapterName())
                    textfields.add(name)
                }.textProperty.bind(GUI.RESOURCE_FACTORY.getStringBinding("changingForm.chapterName"))

                field("Marines Count") {
                    val count = textfield(spaceMarine.getChapterCount().toString())
                    textfields.add(count)
                }.textProperty.bind(GUI.RESOURCE_FACTORY.getStringBinding("changingForm.countOfMarines"))

            }.textProperty.bind(GUI.RESOURCE_FACTORY.getStringBinding("changingForm.chapter"))

            button {
                action {
                    spaceMarine.setName(textfields[0].text)
                    spaceMarine.setCoordinates(Coordinates(textfields[1].text.toDouble(), textfields[2].text.toInt()))
                    spaceMarine.setHealth(textfields[3].text.toFloat())
                    spaceMarine.setLoyalty(checkboxs[0].isSelected)
                    spaceMarine.setCategory(comboBoxes[0].value as AstartesCategory)
                    spaceMarine.setMeleeWeapon(comboBoxes[1].value as MeleeWeapon)
                    spaceMarine.setChapter(Chapter(textfields[4].text, textfields[5].text.toLong()))
                    controller.spaceMarineEdit(GUI.console, spaceMarine, "add")
                    close()
                }
            }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("changingForm.save"))

            button {
                action {
                    close()
                }
            }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("changingForm.cancel"))

            button {
                action {
                    controller.spaceMarineEdit(GUI.console, spaceMarine, "remove")
                    close()
                }
            }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("changingForm.delete"))

        }.textProperty.bind(GUI.RESOURCE_FACTORY.getStringBinding("changingForm.title"))
    }

}