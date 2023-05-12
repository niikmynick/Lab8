package application

import clientUtils.Console
import tornadofx.*

class MainView : View() {
    override val root = vbox {
        val console = Console("localhost", 8061)
        button("Connect") {
            action {
                console.connect()
            }
        }
        button("Authorize") {
            action {
                console.authorize()
            }
        }
        button("Start Interactive Mode") {
            action {
                console.startInteractiveMode()
            }
        }
    }
}
