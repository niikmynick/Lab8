package application

import exceptions.InvalidInputException
import exceptions.NotAuthorized
import kotlinx.coroutines.runBlocking
import tornadofx.*

class ConsoleView() : View() {
    val console = GUI.console

    override val root = pane {
        clear()

//        layoutX = 72.0
//        layoutY = 84.0
        style = "-fx-background-color: #ffffff; -fx-position: absolute;"

        add(HeadBar(true, this@ConsoleView).root)

        add(LeftMenu(this@ConsoleView).root)

        console.initialize()
        console.registerBasicCommands()

        text {
            style = "-fx-font-size: 24px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
            x = 72.0
            y = 84.0
        }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("consoleView.title"))

        val outputArea = textarea {
            isEditable = false
            isWrapText = true
            layoutX = 72.0
            layoutY = 84.0
            style =
                "-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 20px; -fx-position: absolute;"
            setPrefSize(1256.0, 590.0)
        }

        val inputField = textfield {
            layoutX = 72.0
            layoutY = 84.0
            style =
                "-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 20px; -fx-position: absolute;"
            setPrefSize(1256.0, 42.0)
        }

        inputField.setOnAction {
            runBlocking {
                do {
                    try {
                        outputArea.appendText("$ ")

                        val query = inputField.text.trim().split(" ")
                        inputField.clear()

                        outputArea.appendText("$query\n")

                        if (query[0] != "") {
                            console.checkConnection()
                            console.executeCommand(query)
                        }

                    } catch (e: InvalidInputException) {
                        outputArea.appendText(e.message)
//                    logger.warn(e.message)
                    } catch (e: NotAuthorized) {
                        runBlocking {
                            replaceWith(AuthView(AuthMode.LOGIN), ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                        }
                    } catch (e: Exception) {
                        outputArea.appendText(e.message.toString())
//                    logger.warn(e.message)
                    }

                } while (console.executeFlag != false)

                // send the command to the server and get the response
            }
        }
    }
}