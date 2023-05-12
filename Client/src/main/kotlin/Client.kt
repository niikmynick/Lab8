import application.GUI
import clientUtils.Console
import tornadofx.*

fun client(actions: Console.() -> Unit) {
    val console = Console("localhost", 8061)
    console.actions()
}

/**
 * Main
 */

fun main() {

    launch<GUI>()

//
//    client {
//
//        connect()
//
//        authorize()
//        if (authorized) {
//            startInteractiveMode()
//        }
//
//    }

}
