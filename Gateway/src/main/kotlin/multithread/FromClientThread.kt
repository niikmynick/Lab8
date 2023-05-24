package multithread

import gatewayUtils.ConnectionManager
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import utils.Answer
import utils.AnswerType
import utils.Query
import java.net.InetSocketAddress
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit

class FromClientThread(private val connectionManager: ConnectionManager, private val queryQueue: LinkedBlockingQueue<Query>) : Runnable {

    private val logger: Logger = LogManager.getLogger(FromClientThread::class.java)
    override fun run() {
        val received = queryQueue.take() as Query
        try {
            do {
                val address: InetSocketAddress = connectionManager.availableServers.poll(5000, TimeUnit.MILLISECONDS)
                    ?: throw Exception("No server was found")
                val isConnected = connectionManager.connected(address)
                if (isConnected) {
                    connectionManager.availableServers.add(address)
                    connectionManager.sendToServer(received, address, "channel")
                }
            } while (!isConnected)
        } catch (e:Exception) {
            logger.warn(e.message)
            val answer = Answer(AnswerType.ERROR, ("GATEWAY ERROR: " + e.message), received.token, received.args["sender"]!!)
            val receiver = answer.receiver.split(':')
            val address = InetSocketAddress(receiver[0].replace("/",""), receiver[1].toInt())
            if ((address != connectionManager.addressForPinging) and (address != connectionManager.addressForServer)) {
                connectionManager.sendToClient(answer, address)
            }
        }
    }
}