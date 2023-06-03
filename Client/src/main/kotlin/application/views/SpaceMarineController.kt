package application.views

import basicClasses.*
import clientUtils.Console
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import tornadofx.*
import java.time.LocalDate

class SpaceMarineController() : Controller() {
    var collection = listOf<SpaceMarine>().toObservable()
    val model = SpaceMarineModel()
    val relationships = mutableMapOf<Long, String>()
    var observableCollection = listOf<SpaceMarineWithAuthor>().toObservable()

    fun updateCollection(console: Console) {
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        try {
            coroutineScope.launch() {
                withTimeout(5000) {
                    val map: Map<String, String> = console.loadCollection()

                    val spaceMarines = map.keys.asSequence()
                    val collection = spaceMarines.map {
                        Json.decodeFromString(SpaceMarine.serializer(), it)
                    }
                    this@SpaceMarineController.collection.setAll(collection.toList().toObservable())
                    for (sm in collection) {
                        this@SpaceMarineController.relationships[sm.getId()] = map.getValue(Json.encodeToString(SpaceMarine.serializer(), sm))
                    }
                    val tempList = mutableListOf<SpaceMarineWithAuthor>()
                    collection.forEach {
                        tempList.add(SpaceMarineWithAuthor(it, this@SpaceMarineController))
                    }
                    observableCollection.setAll(tempList.toObservable())
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    fun spaceMarineEdit(console: Console, spaceMarine: SpaceMarine, action: String) {
        CoroutineScope(Dispatchers.Default).launch {
            withTimeout(5000) {
                try {
                    console.spaceMarineAction(spaceMarine, action)
                } catch (e: Exception) {
                    throw e
                }
            }
        }
    }

//    class SpaceMarineModel() : ItemViewModel<SpaceMarine>() {
//        val id = bind {item?.getId()?.toProperty()}
//        val name = bind {item?.getName()?.toProperty()}
//        val coordinates = bind {item?.getCoordinates()?.toProperty()}
//        val creationDate = bind {item?.getCreationDate()?.toProperty()}
//        val health = bind {item?.getHealth()?.toProperty()}
//        val loyal = bind {item?.getLoyalty()?.toProperty()}
//        val category = bind {item?.getCategory()?.toProperty()}
//        val meleeWeapon = bind {item?.getWeapon()?.toProperty()}
//        val chapter = bind {item?.getChapter()?.toProperty()}
//        //val author = bind {smController.relationships[item?.getId()].toProperty()}
//    }

    class SpaceMarineModel() : ItemViewModel<SpaceMarineWithAuthor>() {
        val id = bind {item?.getId().toProperty()}
        val name = bind {item?.getName().toProperty()}
        val coordinates = bind {item?.getCoordinates().toProperty()}
        val creationDate = bind {item?.getCreationDate().toProperty()}
        val health = bind {item?.getHealth().toProperty()}
        val loyal = bind {item?.getLoyalty().toProperty()}
        val category = bind {item?.getCategory().toProperty()}
        val meleeWeapon = bind {item?.getWeapon().toProperty()}
        val chapter = bind {item?.getChapter().toProperty()}
        val author = bind {item?.getAuthor().toProperty()}
    }

    class SpaceMarineWithAuthor(spaceMarine: SpaceMarine, smController: SpaceMarineController) {
        private val id = spaceMarine.getId()
        private val name = spaceMarine.getName()
        private val coordinates = spaceMarine.getCoordinates()
        private val creationDate = spaceMarine.getCreationDate()
        private val health = spaceMarine.getHealth()
        private val loyal = spaceMarine.getLoyalty()
        private val category = spaceMarine.getCategory()
        private val meleeWeapon = spaceMarine.getWeapon()
        private val chapter = spaceMarine.getChapter()
        private val author = smController.relationships[spaceMarine.getId()]


        fun getId(): Long {
            return id
        }

        fun getName(): String {
            return name
        }

        fun getCoordinates(): Coordinates {
            return coordinates
        }

        fun getCreationDate(): LocalDate {
            return creationDate
        }

        fun getHealth(): Float? {
            return health
        }

        fun getLoyalty(): Boolean {
            return loyal
        }

        fun getCategory(): AstartesCategory {
            return category
        }

        fun getWeapon(): MeleeWeapon? {
            return meleeWeapon
        }

        fun getChapter(): Chapter? {
            return chapter
        }

        fun getAuthor(): String? {
            return author
        }
        fun getSpaceMarine() : SpaceMarine {
            return SpaceMarine(
                this.id,
                this.name,
                this.coordinates,
                this.creationDate,
                this.health,
                this.loyal,
                this.category,
                this.meleeWeapon,
                this.chapter
            )
        }
    }

}