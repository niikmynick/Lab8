package commands

import basicClasses.*
import collection.CollectionManager
import exceptions.InvalidArgumentException
import serverUtils.*
import utils.*

/**
 * Class that receives and processes user commands
 * @param collectionManager A [CollectionManager] that manages the collection of objects
 * @param connectionManager A [ConnectionManager] object to handle communication with the client
 */
class CommandReceiver(private val collectionManager: CollectionManager,
                      private val connectionManager: ConnectionManager
) {

    private val jsonCreator = JsonCreator()

    /**
     * Sends the collection's info to the client
     */
    fun info(args: Map<String, String>) : Answer{
        return try {
            Answer(AnswerType.OK, collectionManager.getInfo(), receiver = args["sender"]!!)
        } catch (e: Exception) {
            Answer(AnswerType.ERROR, e.message.toString(), receiver = args["sender"]!!)
        }
    }

    /**
     * Sends each element in the collection to the client
     */
    fun show(args: Map<String, String>) : Answer{
        return try {
            Answer(AnswerType.OK, collectionManager.show().joinToString("\n"), receiver = args["sender"]!!)
        } catch (e: Exception) {
            Answer(AnswerType.ERROR, e.message.toString(), receiver = args["sender"]!!)
        }
    }

    /**
     * Creates new Space Marine and adds it into collection
     */
    fun add(args: Map<String, String>, username: String) : Answer{
        return try {
            val spaceMarine = jsonCreator.stringToObject<SpaceMarine>(args["spaceMarine"]!!)
            collectionManager.add(spaceMarine, username)
            Answer(AnswerType.OK, "Space Marine ${spaceMarine.getName()} has been created and added to the collection", receiver = args["sender"]!!)
        } catch (e: Exception) {
            Answer(AnswerType.ERROR, e.message.toString(), receiver = args["sender"]!!)
        }
    }

    /**
     * Searches for a Space Marine with provided id and updates its values
     */
    fun updateByID(args: Map<String, String>, username: String) : Answer{
        val id = args["id"]!!

        return try {
            val oldSpaceMarine = collectionManager.getByID(id.toLong())
                ?: throw InvalidArgumentException("No Space Marine with id: $id was found")
            val newSpaceMarine = jsonCreator.stringToObject<SpaceMarine>(args["spaceMarine"]!!)
            collectionManager.update(newSpaceMarine, oldSpaceMarine, username)
            Answer(AnswerType.OK, "Space Marine ${oldSpaceMarine.getName()} has been updated", receiver = args["sender"]!!)

        } catch (e: Exception) {
            Answer(AnswerType.ERROR, e.message.toString(), receiver = args["sender"]!!)
        }
    }

    /**
     * Searches for a Space Marine with the provided id and removes it from the collection
     */
    fun removeByID(args: Map<String, String>, username: String) : Answer{
        val id = args["id"]!!

        return try {
            val spaceMarine = collectionManager.getByID(id.toLong())
                ?: throw InvalidArgumentException("No Space Marine with id: $id was found")

            collectionManager.remove(spaceMarine, username)
            Answer(AnswerType.OK, "Space Marine ${spaceMarine.getName()} has been deleted", receiver = args["sender"]!!)

        } catch (e: Exception) {
            Answer(AnswerType.ERROR, e.message.toString(), receiver = args["sender"]!!)
        }
    }

    /**
     * Clears the collection
     */
    fun clear(args: Map<String, String>, username: String) : Answer{
        return if (collectionManager.getCollection().size > 0) {
            try {
                collectionManager.clear(username)
                Answer(AnswerType.OK, "Your collection has been cleared", receiver = args["sender"]!!)
            } catch (e: Exception) {
                Answer(AnswerType.ERROR, e.message.toString(), receiver = args["sender"]!!)
            }
        } else {
            Answer(AnswerType.ERROR, "The collection is already empty", receiver = args["sender"]!!)
        }
    }

    fun addMin(args: Map<String, String>, username: String) : Answer{
        try {
            val spaceMarine = jsonCreator.stringToObject<SpaceMarine>(args["spaceMarine"]!!)
            return if (collectionManager.getCollection().isNotEmpty()) {
                if (spaceMarine < collectionManager.getCollection().first()) {
                    collectionManager.add(spaceMarine, username)
                    Answer(AnswerType.OK, "Space Marine ${spaceMarine.getName()} has been created and added to the collection", receiver = args["sender"]!!)
                } else {
                    Answer(AnswerType.ERROR, "Space Marine ${spaceMarine.getName()} has not been added to the collection, because it is not the smallest", receiver = args["sender"]!!)
                }
            } else {
                collectionManager.add(spaceMarine, username)
                Answer(AnswerType.OK, "Space Marine ${spaceMarine.getName()} has been created and added to the collection", receiver = args["sender"]!!)
            }

        } catch (e: Exception) {
            return Answer(AnswerType.ERROR, e.message.toString(), receiver = args["sender"]!!)
        }
    }

    /**
     * Removes all elements greater than provided
     */
    fun removeGreater(args: Map<String, String>, username: String) : Answer{
        val id = args["id"]
        try {
            val collection = collectionManager.getCollection()
            val spaceMarine = collectionManager.getByID(id!!.toLong())
                ?: throw InvalidArgumentException("No Space Marine with id: $id was found")
            var count = 0

            if (collection.isNotEmpty()) {
                while (collection.last() > spaceMarine) {
                    collectionManager.remove(collection.last(), username)
                    count++
                }
            }
            return Answer(AnswerType.OK, when (count) {
                0 -> { "No Space Marines were deleted" }
                1 -> { "Only 1 Space Marine was deleted" }
                else -> { "$count Space Marines have been deleted" }
            }, receiver = args["sender"]!!)

        } catch (e: Exception) {
            return Answer(AnswerType.ERROR, e.message.toString(), receiver = args["sender"]!!)
        }
    }

    /**
     * Removes all elements lower than provided
     */
    fun removeLower(args: Map<String, String>, username: String) : Answer{
        val id = args["id"]
        try {
            val collection = collectionManager.getCollection()
            val spaceMarine = collectionManager.getByID(id!!.toLong())
                ?: throw InvalidArgumentException("No Space Marine with id: $id was found")
            var count = 0

            if (collection.isNotEmpty()) {
                while (collection.first() < spaceMarine) {
                    collectionManager.remove(collection.last(), username)
                    count++
                }
            }

            return Answer(AnswerType.OK, when (count) {
                0 -> { "No Space Marines were deleted" }
                1 -> { "Only 1 Space Marine was deleted" }
                else -> { "$count Space Marines have been deleted" }
            }, receiver = args["sender"]!!)

        } catch (e: Exception) {
            return Answer(AnswerType.ERROR, e.message.toString(), receiver = args["sender"]!!)
        }
    }

    /**
     * Removes first found element with [Chapter] equal to provided
     */
    fun removeByChapter(args: Map<String, String>, username: String) : Answer{
        try {
            val chapter = jsonCreator.stringToObject<Chapter>(args["chapter"]!!)

            val collection = collectionManager.getCollection()
            var count = 0

            for (spaceMarine in collection) {
                if (spaceMarine.getChapter() == chapter) {
                    collectionManager.remove(spaceMarine, username)
                    count++
                }
            }

            return Answer(AnswerType.OK, when (count) {
                0 -> { "No Space Marines with $chapter was found" }
                1 -> { "Only 1 Space Marine with $chapter was found and deleted" }
                else -> { "$count Space Marines with $chapter were found and deleted" }
            }, receiver = args["sender"]!!)


        } catch (e: Exception) {
            return Answer(AnswerType.ERROR, e.message.toString(), receiver = args["sender"]!!)
        }

    }

    fun countByWeapon(args: Map<String, String>) : Answer{
        try {
            val weapon = jsonCreator.stringToObject<MeleeWeapon>(args["weapon"]!!)
            val collection = collectionManager.getCollection()
            var count = 0

            if (collection.isNotEmpty()) {
                for (spaceMarine in collection) {
                    if (spaceMarine.getWeapon() == weapon) {
                        count++
                    }
                }
                return Answer(AnswerType.OK, when (count) {
                    0 -> { "No Space Marines with $weapon were found" }
                    1 -> { "Only 1 Space Marine with $weapon was found" }
                    else -> { "$count Space Marines with $weapon were found" }
                }, receiver = args["sender"]!!)

            } else {
                throw Exception("The collection is empty")
            }
        } catch (e: Exception) {
            return Answer(AnswerType.ERROR, e.message.toString(), receiver = args["sender"]!!)
        }
    }

    fun filterByChapter(args: Map<String, String>) : Answer{
        try {
            val chapter = jsonCreator.stringToObject<Chapter>(args["chapter"]!!)
            val filteredList = mutableListOf<SpaceMarine>()

            for (i in collectionManager.filter { e -> e.getChapter()!! == chapter }) {
                filteredList.add(i)
            }

            if (filteredList.isEmpty()) {
                throw Exception("No Space Marines with $chapter were found")
            }

            return Answer(AnswerType.OK, filteredList.joinToString("\n"), receiver = args["sender"]!!)

        } catch (e: Exception) {
            return Answer(AnswerType.ERROR, e.message.toString(), receiver = args["sender"]!!)
        }
    }

    fun filterByWeapon(args: Map<String, String>) : Answer{
        try {
            val weapon = jsonCreator.stringToObject<MeleeWeapon>(args["weapon"]!!)
            val filteredList = mutableListOf<SpaceMarine>()

            for (i in collectionManager.filter { e -> e.getWeapon()!! == weapon }) {
                filteredList.add(i)
            }
            if (filteredList.isEmpty()) {
                throw Exception("No Space Marines with $weapon were found")
            }

            return Answer(AnswerType.OK, filteredList.joinToString("\n"), receiver = args["sender"]!!)

        } catch (e: Exception) {
            return Answer(AnswerType.ERROR, e.message.toString(), receiver = args["sender"]!!)
        }
    }

}
