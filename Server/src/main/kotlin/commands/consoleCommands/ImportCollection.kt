package commands.consoleCommands

import basicClasses.SpaceMarine
import serverUtils.DBManager
import utils.Answer
import utils.AnswerType
import utils.JsonCreator

class ImportCollection() : Command() {

    private lateinit var dbManager: DBManager
    private lateinit var jsonCreator: JsonCreator
    constructor(dbManager: DBManager, jsonCreator: JsonCreator) : this() {
        this.dbManager = dbManager
        this.jsonCreator = jsonCreator
    }

    private val info = "Retrieves the collection from database"
    private val argsTypes = mapOf<String, String>()

    override fun getInfo(): String {
        return info
    }

    override fun getArgsTypes(): Map<String, String> {
        return argsTypes
    }

    /**
     * Calls [DBManager.loadCollection]
     */
    override fun execute(args: Map<String, String>, username: String): Answer {
        val collection = dbManager.loadCollection()
        println(collection)
        val map = mutableMapOf<String, String>()
        for (element in collection) {
            val spaceMarine = jsonCreator.stringToObject<SpaceMarine>(element.key)
            val author = element.value
            map[jsonCreator.objectToString(spaceMarine)] = author
        }
        return Answer(AnswerType.OK, jsonCreator.objectToString(map), receiver = args["sender"]!!)
    }

}