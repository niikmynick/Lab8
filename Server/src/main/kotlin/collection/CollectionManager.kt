package collection

import basicClasses.SpaceMarine
import exceptions.NotAuthorized
import exceptions.SpaceMarineIdAlreadyExists
import serverUtils.DBManager
import java.util.*
import java.util.concurrent.locks.ReentrantLock
import java.util.function.Predicate

/**
 * A [TreeSet] collection of [SpaceMarine]
 * Implements methods used in commands
 * @property date Saves creation date
 */
class CollectionManager(private val dbManager: DBManager) {
    private val collection = Collections.synchronizedSortedSet(TreeSet<SpaceMarine>())

    /**
     * Element_id to user_login
     */
    private val relationship = Collections.synchronizedMap(mutableMapOf<Long, String>())

    private val date: Date = Date()
    private val lock = ReentrantLock()

    fun getCollection(): SortedSet<SpaceMarine> {
        return collection as SortedSet<SpaceMarine>
    }

    fun getRelationship(): MutableMap<Long, String> {
        return relationship
    }

    fun add(element: SpaceMarine, username: String) {
        if (element == this.getByID(element.getId())) throw SpaceMarineIdAlreadyExists("Space Marine" +
                "$element cannot be added to collection as a Space Marine with this id already exists")
        lock.lock()
        try {
            dbManager.saveSpacemarine(element, username)
            val id = dbManager.getIdBySpacemarine(element)
            element.setId(id)
            collection.add(element)
            relationship[element.getId()] = username
            dbManager.saveSpacemarine(element, username)
        } finally {
            lock.unlock()
        }
    }

    /**
     * Creates a formatted string with info about collection
     * @return Formatted string with size and [date] values
     */
    fun getInfo() : String {
        return "Tree Set of SpaceMarine: size=${collection.size}, date=${date}"
    }

    /**
     * Prints all elements of the collection
     */
    fun show(): MutableList<String> {
        return if (collection.isEmpty()) {
            mutableListOf("Collection is empty")
        } else {
            val output = mutableListOf<String>()
            lock.lock()
            try {
                for (spaceMarine in collection) {
                    output.add(spaceMarine.toString())
                }
            } finally {
                lock.unlock()
            }
            output
        }
    }

    /**
     * Updates values of an element
     */
    fun update(data: SpaceMarine, spaceMarine: SpaceMarine, username: String) {
        if ((relationship[spaceMarine.getId()] != username) or (username != "admin")) throw NotAuthorized("You don't have permission to update this element")

        lock.lock()
        try {
            spaceMarine.setName(data.getName())
            spaceMarine.setCoordinates(data.getCoordinates())
            spaceMarine.setCategory(data.getCategory())
            spaceMarine.setChapter(data.getChapter())
            spaceMarine.setHealth(data.getHealth())
            spaceMarine.setLoyalty(data.getLoyalty())
            spaceMarine.setMeleeWeapon(data.getWeapon())
            dbManager.saveSpacemarine(spaceMarine, username)
        } finally {
            lock.unlock()
        }
    }

    /**
     * Removes element
     * @param spaceMarine element in the collection
     */
    fun remove(spaceMarine: SpaceMarine, username: String) {
        if ((relationship[spaceMarine.getId()] != username) and (username != "admin")) throw NotAuthorized("You don't have permission to remove this element")
        lock.lock()
        try {
            dbManager.deleteSpaceMarine(spaceMarine.getId())
            relationship.remove(spaceMarine.getId())
            collection.remove(spaceMarine)
        } finally {
            lock.unlock()
        }
    }

    fun clear(username: String) {
        val toBeCleared = mutableListOf<SpaceMarine>()
        lock.lock()
        for (spacemarine in collection) {
            if ((relationship[spacemarine.getId()] == username) or (username == "admin")) {
                toBeCleared.add(spacemarine)
            }
        }
        lock.unlock()
        for (spacemarine in toBeCleared) {
            try {
                remove(spacemarine, username)
            } catch (_:Exception) {}

        }

    }

    /**
     * Searches for element with provided [id]
     * @param id id of the element to search
     * @return Found element or null
     */
    fun getByID(id: Long) : SpaceMarine? {
        lock.lock()
        try {
            for (spaceMarine in collection) {
                if (spaceMarine.getId() == id) {
                    return spaceMarine
                }
            }
        } finally {
            lock.unlock()
        }
        return null
    }

    fun filter(predicate: Predicate<SpaceMarine>): List<SpaceMarine> {
        lock.lock()
        try {
            return collection.filter { e -> predicate.test(e) }
        } finally {
            lock.unlock()
        }
    }
}
