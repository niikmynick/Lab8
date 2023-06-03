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
        fieldset("Changing Space Marine") {
            field("Name") {
                val name = textfield(spaceMarine.getName())
                textfields.add(name)
            }
            field("Coordinates") {
                field("X") {
                    val x = textfield(spaceMarine.getCoordinatesX().toString())
                    textfields.add(x)
                }
                field("Y") {
                    val y = textfield(spaceMarine.getCoordinatesY().toString())
                    textfields.add(y)
                }
            }
            field("Health") {
                val health = textfield(spaceMarine.getHealth().toString())
                textfields.add(health)
            }
            field("Loyal") {
                val loyal = checkbox(spaceMarine.getLoyalty().toString())
                checkboxs.add(loyal)
            }
            field("Category") {
                val category = combobox(spaceMarine.getCategory().toProperty(), AstartesCategory.values().toList())
                comboBoxes.add(category as javafx.scene.control.ComboBox<Any>)
            }
            field("Melee Weapon") {
                val weapon = combobox(spaceMarine.getWeapon().toProperty(), MeleeWeapon.values().toList())
                comboBoxes.add(weapon as javafx.scene.control.ComboBox<Any>)
            }
            field("Chapter") {
                field("Name") {
                    val name = textfield(spaceMarine.getChapterName())
                    textfields.add(name)
                }
                field("Marines Count") {
                    val count = textfield(spaceMarine.getChapterCount().toString())
                    textfields.add(count)
                }
            }

            button("Save changes") {
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
            }
        }
    }

}