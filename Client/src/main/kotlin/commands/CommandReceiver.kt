package commands

import basicClasses.*
import commands.consoleCommands.Command
import clientUtils.*
import clientUtils.readers.*
import exceptions.InvalidInputException
import exceptions.NotAuthorized
import utils.*

class CommandReceiver(private val commandInvoker: CommandInvoker,
                      private val outputManager: OutputManager,
                      private val inputManager: InputManager,
                      private val connectionManager: ConnectionManager
) {

    private val creator = Creator(outputManager, inputManager)
    private val enumReader = EnumReader(outputManager, inputManager)
    private val jsonCreator = JsonCreator()

    /**
     * Gets a command map from [commandInvoker], and prints each command's info or info of provided command in arg
     */
    fun help() {
        val commands = commandInvoker.getCommandMap()

        outputManager.println("Help is available for the following commands:")
        for (key in commands.keys) {
            outputManager.println("- ${key.uppercase()}")
        }

        outputManager.println("For information on a command, type HELP {command name}")
        outputManager.println("To get information about each available command, type HELP ALL")
    }

    /**
     * Prints information about the provided command.
     */
    fun help(arg:String) {
        val commands = commandInvoker.getCommandMap()

        if (arg.lowercase() == "all") {
            commandInvoker.getCommandMap().forEach { (name: String?, command: Command) -> outputManager.println(name.uppercase() + " - " + command.getInfo()) }

        } else {
            outputManager.println(commands[arg.lowercase()]?.getInfo().toString())
        }
    }

    fun executeScript(filepath: String) {
        inputManager.startScriptReader(filepath)
    }

    fun unknownCommand(commandName:String, commandArgs: List<String> ,args: Map<String, String>, token: String){
        val sending = mutableMapOf<String, String>()

        if (("id" in args.keys) and (commandArgs.isEmpty())) {
            throw InvalidInputException()
        } else

        for (arg in args.keys) {
            sending[arg] = when (args[arg]) {
                "AstartesCategory" -> jsonCreator.objectToString(enumReader.read<AstartesCategory>("Enter Astartes category from the list: ", false)!!)
                "MeleeWeapon" -> jsonCreator.objectToString(enumReader.read<MeleeWeapon>("Enter Weapon category from the list: ", true))
                "Chapter" -> jsonCreator.objectToString(creator.createChapter())
                "Coordinates" -> jsonCreator.objectToString(creator.createCoordinates())
                "SpaceMarine" -> jsonCreator.objectToString(creator.createSpaceMarine())
                "Long" -> commandArgs[0]
                else -> ""
            }
        }

        val query = Query(QueryType.COMMAND_EXEC, commandName, sending, token)
        val answer = connectionManager.checkedSendReceive(query)
        if (answer.message == "Unknown token. Authorize again.") {
            throw NotAuthorized("Not authorized")
        }
        outputManager.println(answer.message)
    }
}