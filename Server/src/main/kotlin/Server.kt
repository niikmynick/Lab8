import serverUtils.Console
import kotlin.concurrent.thread

fun server(actions: Console.() -> Unit) {
    val console = Console()
    console.actions()
}

/**
 * Main
 */
fun main() {

    server {
        val port = 8070
        val host = "localhost"

        // address of the gateway
        val gatewayHost = "localhost"
        val gatewayPort = 8071

        initialize()

        thread {
            while (true) {
                when (readlnOrNull()) {
                    "exit" -> {
                        save()
                        connectionManager.registrationRequest(gatewayHost, gatewayPort, "Closing Server")
                        stop()
                        break
                    }
                    "save" -> {
                        save()
                    }
                }
            }
        }

        start {

            startServer(host, port)
            registrationRequest(gatewayHost, gatewayPort, "Registration request")

        }

        startInteractiveMode()

    }

}
