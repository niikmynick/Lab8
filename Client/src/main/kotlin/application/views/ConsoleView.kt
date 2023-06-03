package application.views

import application.*
import exceptions.InvalidInputException
import exceptions.NotAuthorized
import javafx.scene.input.KeyCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tornadofx.*
import java.io.ByteArrayInputStream
import java.util.*


class ConsoleView() : View() {
    val coroutineScope = CoroutineScope(Dispatchers.Default)

    override val root = pane {
        clear()

//        layoutX = 72.0
//        layoutY = 84.0
        style = "-fx-background-color: #ffffff; -fx-position: absolute;"

        add(HeadBar(true, this@ConsoleView).root)

        add(LeftMenu(this@ConsoleView).root)

        GUI.console.initialize()
        GUI.console.registerBasicCommands()

        text {
            style = "-fx-font-size: 32px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
            x = 100.0
            y = 124.0
        }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("consoleView.title"))

        val outputArea = textarea {
            isEditable = false
            isWrapText = true
            layoutX = 92.0
            layoutY = 186.0
            style =
                "-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 20px; -fx-position: absolute;"
            setPrefSize(1256.0, 590.0)
        }

        val inputField = textfield {
            layoutX = 92.0
            layoutY = 146.0
            style =
                "-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 20px; -fx-position: absolute;"
            setPrefSize(1256.0, 42.0)
        }


        inputField.setOnKeyPressed { event ->
            if (event.code === KeyCode.ENTER) {
                val byteArrayInputStream = ByteArrayInputStream(inputField.text.toByteArray())
                GUI.console.inputManager.addScanner(Scanner(byteArrayInputStream))
                inputField.clear()
                coroutineScope.launch {
                    do {
                        try {
                            val query = GUI.console.inputManager.read().trim().split(" ")

                            if (query[0].lowercase() == "clear") {
                                outputArea.text = ""
                            } else if (query[0] != "") {
                                GUI.console.outputManager.println("$ ${query.toString()}")

                                GUI.console.checkConnection()
                                GUI.console.executeCommand(query)
                            }


                        } catch (e: InvalidInputException) {
                            GUI.console.outputManager.println(e.message)
                        } catch (e: NotAuthorized) {
                            runLater {
                                replaceWith(AuthView(AuthMode.LOGIN), ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                            }
                        } catch (e: Exception) {
                            GUI.console.outputManager.println(e.message.toString())
                        }
                    } while (GUI.console.executeFlag != false)

                }
            }

        }

        GUI.console.outputManager.outputStream = TextAreaOutputStream(outputArea)

    }
}