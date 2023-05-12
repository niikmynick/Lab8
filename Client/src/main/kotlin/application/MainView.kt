package application

import clientUtils.Console
import tornadofx.*

import tornadofx.*
import kotlin.system.exitProcess

class MainView : View() {
    private val client = Console("localhost", 8061)
    private var connected = false
    private var authorized = false
    private var interactiveModeStarted = false

    override val root = pane {

        button("Connect") {
            action {
                client.connect()
                connected = true
                updateUI()
            }
        }
    }

    private fun updateUI() {
        if (connected && !authorized) {
            val authorizeButton = button("Authorize") {
                action {
                    client.authorize()
                    authorized = true
                    updateUI()
                }
            }
            root.clear()
            root.add(authorizeButton)
        }

        if (authorized && !interactiveModeStarted) {
            val startButton = button("Start Interactive Mode") {
                action {
                    client.startInteractiveMode()
                    interactiveModeStarted = true
                    updateUI()
                }
            }
            root.clear()
            root.add(startButton)
        }

        if (interactiveModeStarted) {
            val exitButton = button ("Exit") {
                action {
                    exitProcess(1)
                }
            }
            root.clear()
            root.add(exitButton)
        }
    }
}
