package application

import basicClasses.*
import clientUtils.Console
import exceptions.InvalidInputException
import exceptions.NotAuthorized
import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Text
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import tornadofx.*
import java.io.ByteArrayInputStream
import java.util.*

class MainView : View() {
    private val console = Console("localhost", 8061)

    override var root = AnchorPane()

    init {
        root.background = Background(BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))
        welcomeView()
    }

    private fun headPane(needAccountIcon: Boolean): Pane {

        val headBar = Pane()
        headBar.setPrefSize(1440.0, 64.0)
        headBar.style = "-fx-background-color: #000000; -fx-border-radius: 20px;"

        val appNameText = Text("Space Marine Collection")
        appNameText.style =
            "-fx-text-alignment: left; -fx-vertical-alignment: top; -fx-font-size: 24px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #ffffff;"
        appNameText.layoutX = 20.0
        appNameText.layoutY = 40.0

        headBar.add(appNameText)

        if (needAccountIcon) {
            val accountIcon = ImageView(Image("file:Client/src/main/resources/account_icon.png"))
            accountIcon.style = "-fx-text-alignment: left; -fx-vertical-alignment: top;"
            accountIcon.layoutX = 1380.0
            accountIcon.layoutY = 20.0
            accountIcon.fitHeight = 20.0
            accountIcon.fitWidth = 20.0

            accountIcon.setOnMouseClicked {
                showSettings()
            }

            headBar.add(accountIcon)
        }

        return headBar
    }

    private fun leftBox(): VBox {

        val homeButton = Button()
        homeButton.imageview("file:Client/src/main/resources/home_icon.png") {
            fitHeight = 25.0
            fitWidth = 30.0
        }
        homeButton.style = "-fx-background-color: transparent;"
        homeButton.setOnMouseClicked {
            showMenu()
        }

        val consoleButton = Button()
        consoleButton.imageview("file:Client/src/main/resources/console_icon.png") {
            fitHeight = 30.0
            fitWidth = 30.0

        }
        consoleButton.style = "-fx-background-color: transparent;"
        consoleButton.setOnMouseClicked {
            showConsole()
        }

        val collectionButton = Button()
        collectionButton.imageview("file:Client/src/main/resources/collection_icon.png") {
            fitHeight = 30.0
            fitWidth = 30.0
        }
        collectionButton.style = "-fx-background-color: transparent;"

        collectionButton.setOnMouseClicked {
            showCollection()
        }

        val leftMenu = VBox(20.0, homeButton, consoleButton, collectionButton)
        leftMenu.style = "-fx-background-color: #fff;"
        leftMenu.setPrefSize(72.0, 836.0)
        leftMenu.layoutX = 0.0
        leftMenu.layoutY = 64.0
        leftMenu.padding = Insets(20.0, 0.0, 0.0, 12.0)

        return leftMenu
    }

    private fun block(title: String, description: String, buttonText: String, buttonAction: () -> Unit): Pane {

        val block = Pane()
        block.setPrefSize(436.0, 211.0)
        block.style =
            "-fx-background-color: #ffffff; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px;"

        val blockTitle = Text(title)
        blockTitle.style =
            "-fx-text-alignment: left; -fx-font-size: 32px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000;"
        blockTitle.layoutX = 28.0
        blockTitle.layoutY = 52.0

        val blockDescription = Text(description)
        blockDescription.style =
            "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000;"
        blockDescription.layoutX = 28.0
        blockDescription.layoutY = 96.0

        val blockButton = Button(buttonText)
        blockButton.style =
            "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000;"
        blockButton.layoutX = 28.0
        blockButton.layoutY = 140.0
        blockButton.setPrefSize(160.0, 42.0)
        blockButton.setOnMouseClicked {
            buttonAction()
        }

        block.add(blockTitle)
        block.add(blockDescription)
        block.add(blockButton)

        return block
    }


    private fun welcomeView() {

        val headBar = headPane(false)

        root.add(headBar)

        val welcomePane = Pane()
        welcomePane.setPrefSize(1440.0, 836.0)
        welcomePane.style = "-fx-background-color: #ffffff; "
        welcomePane.layoutX = 0.0
        welcomePane.layoutY = 64.0

        val loginText = Text("Hello,\nBefore we start you have to log in to your account")
        loginText.style =
            "-fx-text-alignment: left; -fx-font-size: 24px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
        loginText.wrappingWidth = 600.0
        loginText.x = 420.0
        loginText.y = 388.0

        val loginButton = Button("Authorize")
        loginButton.style =
            "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
        loginButton.layoutX = 420.0
        loginButton.layoutY = 470.0
        loginButton.setPrefSize(160.0, 42.0)

        loginButton.setOnMouseEntered {
            loginButton.style =
                "-fx-text-fill: #555; -fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #777; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
        }

        loginButton.setOnMouseExited {
            loginButton.style =
                "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
        }

        loginButton.setOnMouseClicked {
            loginView()
        }

        welcomePane.add(loginText)
        welcomePane.add(loginButton)

        root.add(welcomePane)

    }

    private fun loginView() {

        root.clear()

        val headBar = headPane(false)

        root.add(headBar)

        val loginPane = Pane()
        loginPane.setPrefSize(450.0, 480.0)
        loginPane.style =
            "-fx-background-color: #ffffff; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px;"
        loginPane.layoutX = 495.0
        loginPane.layoutY = 242.0

        val title = Text("Auth")
        title.style =
            "-fx-text-alignment: left; -fx-font-size: 32px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
        title.x = 28.0
        title.y = 52.0

        val nameInvoke = Text("Username")
        nameInvoke.style =
            "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
        nameInvoke.x = 28.0
        nameInvoke.y = 120.0

        val username = TextField()
        username.promptText = "Enter your username here"
        username.style =
            "-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
        username.layoutX = 28.0
        username.layoutY = 140.0
        username.setPrefSize(394.0, 42.0)

        val passwordInvoke = Text("Password")
        passwordInvoke.style =
            "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
        passwordInvoke.x = 28.0
        passwordInvoke.y = 230.0

        val password = PasswordField()
        password.promptText = "Enter your password here"
        password.style =
            "-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
        password.layoutX = 28.0
        password.layoutY = 250.0
        password.setPrefSize(394.0, 42.0)

        val loginButton = Button("Log In")
        loginButton.style =
            "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
        loginButton.layoutX = 145.0
        loginButton.layoutY = 341.0
        loginButton.setPrefSize(160.0, 42.0)

        loginButton.setOnMouseEntered {
            loginButton.style =
                "-fx-text-fill: #777; -fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #777; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
        }

        loginButton.setOnMouseExited {
            loginButton.style =
                "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
        }

        loginButton.setOnMouseClicked {
            while (!console.authorized) {
                runBlocking {
                    if (!console.authorize(username.text, password.text)) {
                        loginView()
                    } else {
                        showMenu()
                    }
                }
            }
        }

        val registerInvoke = Text("Don't have an account?\nRegister")
        registerInvoke.style =
            "-fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
        registerInvoke.x = 152.0
        registerInvoke.y = 421.0

        registerInvoke.setOnMouseEntered {
            registerInvoke.style =
                "-fx-text-fill: #777; -fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
        }

        registerInvoke.setOnMouseExited {
            registerInvoke.style =
                "-fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
        }

        registerInvoke.setOnMouseClicked {
            registerView()
        }


        loginPane.add(title)
        loginPane.add(nameInvoke)
        loginPane.add(username)
        loginPane.add(passwordInvoke)
        loginPane.add(password)
        loginPane.add(loginButton)
        loginPane.add(registerInvoke)

        root.add(loginPane)
    }

    private fun registerView() {

        root.clear()

        val headBar = headPane(false)

        root.add(headBar)

        val registerPane = Pane()
        registerPane.setPrefSize(450.0, 480.0)
        registerPane.style =
            "-fx-background-color: #ffffff; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px;"
        registerPane.layoutX = 495.0
        registerPane.layoutY = 242.0

        val title = Text("Auth")
        title.style =
            "-fx-text-alignment: left; -fx-font-size: 32px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
        title.x = 28.0
        title.y = 52.0

        val nameInvoke = Text("Username")
        nameInvoke.style =
            "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
        nameInvoke.x = 28.0
        nameInvoke.y = 120.0

        val username = TextField()
        username.promptText = "Enter your username here"
        username.style =
            "-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
        username.layoutX = 28.0
        username.layoutY = 140.0
        username.setPrefSize(394.0, 42.0)

        val passwordInvoke = Text("Password")
        passwordInvoke.style =
            "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
        passwordInvoke.x = 28.0
        passwordInvoke.y = 230.0

        val password = PasswordField()
        password.promptText = "Enter your password here"
        password.style =
            "-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
        password.layoutX = 28.0
        password.layoutY = 250.0
        password.setPrefSize(394.0, 42.0)

        val registerButton = Button("Sign In")
        registerButton.style =
            "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
        registerButton.layoutX = 145.0
        registerButton.layoutY = 341.0
        registerButton.setPrefSize(160.0, 42.0)

        registerButton.setOnMouseEntered {
            registerButton.style =
                "-fx-text-fill: #555; -fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #777; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
        }

        registerButton.setOnMouseExited {
            registerButton.style =
                "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
        }

        registerButton.setOnMouseClicked {
            while (!console.authorized) {
                runBlocking {
                    if (!console.authorize(username.text, password.text)) {
                        registerView()
                    } else {
                        showMenu()
                    }
                }
            }
        }

        val registerInvoke = Text("Already have an account?\nLog In")
        registerInvoke.style =
            "-fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
        registerInvoke.x = 152.0
        registerInvoke.y = 421.0

        registerInvoke.setOnMouseEntered {
            registerInvoke.style =
                "-fx-text-fill: #777; -fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
        }

        registerInvoke.setOnMouseExited {
            registerInvoke.style =
                "-fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
        }

        registerInvoke.setOnMouseClicked {
            loginView()
        }


        registerPane.add(title)
        registerPane.add(nameInvoke)
        registerPane.add(username)
        registerPane.add(passwordInvoke)
        registerPane.add(password)
        registerPane.add(registerButton)
        registerPane.add(registerInvoke)

        root.add(registerPane)
    }

    private fun showMenu() {
        root.clear()

        val headBar = headPane(true)
        root.add(headBar)

        val leftMenu = leftBox()
        root.add(leftMenu)

        val consoleBlock = block("Console", "Manage your collection", "Open editor", { showConsole() })
        consoleBlock.layoutX = 72.0
        consoleBlock.layoutY = 84.0
        root.add(consoleBlock)

        val settingsBlock =
            block("Settings", "Set up your account and application preferences", "Open settings", { showSettings() })
        settingsBlock.layoutX = 528.0
        settingsBlock.layoutY = 84.0
        root.add(settingsBlock)

        val collectionBlock =
            block("Collection", "Check out all existing Space Marines", "Go to the collection", { showCollection() })
        collectionBlock.layoutX = 984.0
        collectionBlock.layoutY = 84.0
        root.add(collectionBlock)

        val memeBlock = block("Fun stuff", "Racoon is here", "Open the page", { showRacoon() })
        memeBlock.layoutX = 72.0
        memeBlock.layoutY = 315.0
        root.add(memeBlock)
    }

    private fun showConsole() {
        console.initialize()
        console.registerBasicCommands()

        root.clear()

        val headBar = headPane(true)
        root.add(headBar)

        val leftMenu = leftBox()
        root.add(leftMenu)

        val consoleTitle = Text("Console")
        consoleTitle.style =
            "-fx-font-size: 24px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
        consoleTitle.x = 72.0
        consoleTitle.y = 84.0

        val outputArea = TextArea()
        outputArea.isEditable = false
        outputArea.isWrapText = true
        outputArea.style =
            "-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 20px; -fx-position: absolute;"
        outputArea.setPrefSize(1256.0, 590.0)
        // make corners rounded
        outputArea.setShape(Rectangle(20.0, 20.0))

        val inputField = TextField()
        inputField.style =
            "-fx-background-color: #ffffff; -fx-text-fill: #000000; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 20px; -fx-position: absolute;"
        inputField.setPrefSize(1256.0, 42.0)

        val commandWindow = VBox(20.0, consoleTitle, outputArea, inputField)
        commandWindow.layoutX = 72.0
        commandWindow.layoutY = 84.0
        commandWindow.setPrefSize(1200.0, 600.0)
        commandWindow.style =
            "-fx-background-color: #ffffff; -fx-position: absolute;"

//        val scanner = Scanner(ByteArrayInputStream(inputField.text.toByteArray()))

        // Handle user input
        inputField.setOnAction {
            runBlocking {
                do {
                    try {
                        outputArea.appendText("$ ")

                        val query = inputField.text.trim().split(" ")
                        inputField.clear()

                        outputArea.appendText("$query\n")

                        if (query[0] != "") {
                            console.checkConnection()
                            console.executeCommand(query)
                        }

                    } catch (e: InvalidInputException) {
                        outputArea.appendText(e.message)
//                    logger.warn(e.message)
                    } catch (e: NotAuthorized) {
                        runBlocking {
                            loginView()
                        }
                    } catch (e: Exception) {
                        outputArea.appendText(e.message.toString())
//                    logger.warn(e.message)
                    }

                } while (console.executeFlag != false)

                // send the command to the server and get the response
            }
        }

        root.add(commandWindow)
    }

    private fun showSettings() {
        root.clear()

        val headBar = headPane(true)
        root.add(headBar)

        val leftMenu = leftBox()
        root.add(leftMenu)

        val settingsWindow = VBox()
        val settingsText = Text("Settings")
        settingsText.style = "-fx-font-size: 24px;"
        settingsText.style = "-fx-font_family: 'IBM Plex Sans';"
        settingsWindow.add(settingsText)
        root.add(settingsWindow)
    }


    class SpaceMarineModel() : ItemViewModel<SpaceMarine>() {
        val id = bind { item?.getId()?.toProperty() }
        val name = bind { item?.getName()?.toProperty() }
        val coordinates = bind { item?.getCoordinates()?.toProperty() }
        val creationDate = bind { item?.getCreationDate()?.toProperty() }
        val health = bind { item?.getHealth()?.toProperty() }
        val loyal = bind { item?.getLoyalty()?.toProperty() }
        val category = bind { item?.getCategory()?.toProperty() }
        val meleeWeapon = bind { item?.getWeapon()?.toProperty() }
        val chapter = bind { item?.getChapter()?.toProperty() }
    }

    class SpaceMarineController() : Controller() {
        var collection = listOf<SpaceMarine>().toObservable()
        val model = SpaceMarineModel()
    }

    private fun showCollection() {
        root.clear()

        val headBar = headPane(true)
        root.add(headBar)

        val leftMenu = leftBox()
        root.add(leftMenu)

        val controller = find(SpaceMarineController::class)

        val collectionWindow = VBox()

        collectionWindow.style = "-fx-background-color: #ffffff;"
        collectionWindow.layoutX = 72.0
        collectionWindow.layoutY = 84.0

        val collectionTitle = Text("Collection")
        collectionTitle.style =
            "-fx-font-size: 32px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
        collectionTitle.x = 72.0
        collectionTitle.y = 84.0
        collectionWindow.add(collectionTitle)

        val collectionView = tableview<SpaceMarine> {
            column("Id", SpaceMarine::getId)
            column("Name", SpaceMarine::getName)
            column("Coordinates", SpaceMarine::getCoordinates)
            column("Creation Date", SpaceMarine::getCreationDate)
            column("Health", SpaceMarine::getHealth)
            column("Loyalty", SpaceMarine::getLoyalty)
            column("Category", SpaceMarine::getCategory)
            column("Melee Weapon", SpaceMarine::getWeapon)
            column("Chapter", SpaceMarine::getChapter)

            bindSelected(controller.model)
            smartResize()
        }

        collectionView.style = "-fx-background-color: #ffffff; -fx-font-family: 'IBM Plex Sans'; -fx-font-size: 16px; -fx-fill: #000000;"
        collectionView.layoutX = 92.0
        collectionView.layoutY = 146.0
        collectionView.columnResizePolicy = SmartResize.POLICY
        collectionView.setPrefSize(1000.0, 600.0)

        val updateButton = Button("Update collection")
        updateButton.style = "-fx-background-color: #ffffff; -fx-font-family: 'IBM Plex Sans'; -fx-font-size: 16px; -fx-fill: #000000; -fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 20px;"
        updateButton.layoutX = 92.0
        updateButton.layoutY = 756.0

        updateButton.setOnMouseClicked {
            try {
                val input = console.loadCollection().keys.asSequence()
                val collection = input.map {
                    Json.decodeFromString(SpaceMarine.serializer(), it)
                }
                controller.collection = collection.toList().toObservable()
                println(controller.collection)
                collectionView.items = controller.collection
            } catch (e: NotAuthorized) {
                welcomeView()
            }
        }
        collectionWindow.add(collectionView)
        collectionWindow.add(updateButton)

        root.add(collectionWindow)
    }

    private fun showRacoon() {
        root.clear()

        val headBar = headPane(true)
        root.add(headBar)

        val leftMenu = leftBox()
        root.add(leftMenu)

        val image = ImageView(Image("file:Client/src/main/resources/racoon.jpeg", 1300.0, 800.0, true, true))
        image.x = 72.0
        image.y = 84.0
        root.add(image)

    }
}
