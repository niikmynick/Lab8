package application

import basicClasses.SpaceMarine
import clientUtils.Console
import exceptions.InvalidInputException
import exceptions.NotAuthorized
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import kotlinx.coroutines.*
import tornadofx.*
import java.io.ByteArrayInputStream
import java.util.*

class MainView : View() {
    private val console = Console("localhost", 8061)

    override var root = AnchorPane()

    init {
        root.style = "-fx-background-color: #ffffff; -fx-border-radius: 20px;"
        welcomeView()
    }

    private fun headPane(needAccountIcon: Boolean) : Pane {

        val headBar = Pane()
        headBar.setPrefSize(1440.0, 64.0)
        headBar.style = "-fx-background-color: #000000; -fx-border-radius: 20px;"

        val appNameText = Text("Space Marine Collection")
        appNameText.style = "-fx-text-alignment: left; -fx-vertical-alignment: top; -fx-font-size: 24px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #ffffff;"
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

            headBar.add(accountIcon)
        }

        return headBar
    }

    private fun welcomeView() = runBlocking {

        val headBar = headPane(false)

        root.add(headBar)

        val welcomePane = Pane()
        welcomePane.setPrefSize(1440.0, 836.0)
        welcomePane.style = "-fx-background-color: #ffffff; "
        welcomePane.layoutX = 0.0
        welcomePane.layoutY = 64.0

        val loginText = Text("Hello,\nBefore we start you have to log in to your account")
        loginText.style = "-fx-text-alignment: left; -fx-font-size: 24px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
        loginText.wrappingWidth = 600.0
        loginText.x = 420.0
        loginText.y = 388.0

        val loginButton = Button("Authorize")
        loginButton.style = "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
        loginButton.layoutX = 420.0
        loginButton.layoutY = 470.0
        loginButton.setPrefSize(160.0, 42.0)

        loginButton.setOnMouseEntered {
            loginButton.style = "-fx-text-fill: #555; -fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #777; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
        }

        loginButton.setOnMouseExited {
            loginButton.style = "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
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
        loginPane.style = "-fx-background-color: #ffffff; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px;"
        loginPane.layoutX = 495.0
        loginPane.layoutY = 242.0

        val title = Text("Auth")
        title.style = "-fx-text-alignment: left; -fx-font-size: 32px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
        title.x = 28.0
        title.y = 52.0

        val nameInvoke = Text("Username")
        nameInvoke.style = "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
        nameInvoke.x = 28.0
        nameInvoke.y = 120.0

        val username = TextField()
        username.promptText = "Enter your username here"
        username.style = "-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
        username.layoutX = 28.0
        username.layoutY = 140.0
        username.setPrefSize(394.0, 42.0)

        val passwordInvoke = Text("Password")
        passwordInvoke.style = "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
        passwordInvoke.x = 28.0
        passwordInvoke.y = 230.0

        val password = PasswordField()
        password.promptText = "Enter your password here"
        password.style = "-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
        password.layoutX = 28.0
        password.layoutY = 250.0
        password.setPrefSize(394.0, 42.0)

        val loginButton = Button("Log In")
        loginButton.style = "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
        loginButton.layoutX = 145.0
        loginButton.layoutY = 341.0
        loginButton.setPrefSize(160.0, 42.0)

        loginButton.setOnMouseEntered {
            loginButton.style = "-fx-text-fill: #777; -fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #777; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
        }

        loginButton.setOnMouseExited {
            loginButton.style = "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
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
        registerInvoke.style = "-fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
        registerInvoke.x = 152.0
        registerInvoke.y = 421.0

        registerInvoke.setOnMouseEntered {
            registerInvoke.style = "-fx-text-fill: #777; -fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
        }

        registerInvoke.setOnMouseExited {
            registerInvoke.style = "-fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
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
        registerPane.style = "-fx-background-color: #ffffff; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px;"
        registerPane.layoutX = 495.0
        registerPane.layoutY = 242.0

        val title = Text("Auth")
        title.style = "-fx-text-alignment: left; -fx-font-size: 32px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
        title.x = 28.0
        title.y = 52.0

        val nameInvoke = Text("Username")
        nameInvoke.style = "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
        nameInvoke.x = 28.0
        nameInvoke.y = 120.0

        val username = TextField()
        username.promptText = "Enter your username here"
        username.style = "-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
        username.layoutX = 28.0
        username.layoutY = 140.0
        username.setPrefSize(394.0, 42.0)

        val passwordInvoke = Text("Password")
        passwordInvoke.style = "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
        passwordInvoke.x = 28.0
        passwordInvoke.y = 230.0

        val password = PasswordField()
        password.promptText = "Enter your password here"
        password.style = "-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
        password.layoutX = 28.0
        password.layoutY = 250.0
        password.setPrefSize(394.0, 42.0)

        val registerButton = Button("Sign In")
        registerButton.style = "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
        registerButton.layoutX = 145.0
        registerButton.layoutY = 341.0
        registerButton.setPrefSize(160.0, 42.0)

        registerButton.setOnMouseEntered {
            registerButton.style = "-fx-text-fill: #555; -fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #777; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
        }

        registerButton.setOnMouseExited {
            registerButton.style = "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
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
        registerInvoke.style = "-fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
        registerInvoke.x = 152.0
        registerInvoke.y = 421.0

        registerInvoke.setOnMouseEntered {
            registerInvoke.style = "-fx-text-fill: #777; -fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
        }

        registerInvoke.setOnMouseExited {
            registerInvoke.style = "-fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
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

        // Create a menu with options to go to console, settings, and a separate menu
        val menuBar = MenuBar()

        menuBar.style = "-fx-background-color: #000000;"
        menuBar.style = "-fx-font-size: 14px;"
        menuBar.style = "-fx-font_family: 'IBM Plex Sans';"

        val consoleMenu = Menu()
        val settingsMenu = Menu()
        val racoon = Menu()
        val collectionMenu = Menu()


        consoleMenu.button("Console") {
            action {
                root.clear()
                showMenu()
                showConsole()
            }
        }

        settingsMenu.button("Settings") {
            action {
                root.clear()
                showMenu()
                showSettings()
            }
        }

        racoon.button("Racoon") {
            action {
                root.clear()
                showMenu()
                showRacoon()
            }
        }

        collectionMenu.button("Collection") {
            action {
                root.clear()
                showMenu()
                showCollection()
            }
        }
        menuBar.menus.addAll(consoleMenu, settingsMenu, racoon, collectionMenu)

        root.add(menuBar)
    }

    private fun showConsole() {
        console.initialize()
        console.registerBasicCommands()

        val outputArea = TextArea()
        outputArea.isEditable = false
        outputArea.isWrapText = true

        val inputField = TextField()

        val commandWindow = VBox()
        commandWindow.children.addAll(outputArea, inputField)

        val scanner = Scanner(ByteArrayInputStream(inputField.text.toByteArray()))

        // Handle user input
        inputField.setOnAction {
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
                }
                catch (e: Exception) {
                    outputArea.appendText(e.message.toString())
//                    logger.warn(e.message)
                }

            } while (console.executeFlag != false)

            // send the command to the server and get the response
        }

        root.add(commandWindow)
    }


    private fun showSettings() {
        val settingsWindow = VBox()
        val settingsText = Text("Settings")
        settingsText.style = "-fx-font-size: 24px;"
        settingsText.style = "-fx-font_family: 'IBM Plex Sans';"
        settingsWindow.add(settingsText)
        root.add(settingsWindow)
    }


    class SpaceMarineModel : ItemViewModel<SpaceMarine>() {
        val id = bind {item?.getId()?.toProperty()}
        val name = bind {item?.getName()?.toProperty()}
        val coordinates = bind {item?.getCoordinates()?.toProperty()}
        val creationDate = bind {item?.getCreationDate()?.toProperty()}
        val health = bind {item?.getHealth()?.toProperty()}
        val loyal = bind {item?.getLoyalty()?.toProperty()}
        val category = bind {item?.getCategory()?.toProperty()}
        val meleeWeapon = bind {item?.getWeapon()?.toProperty()}
        val chapter = bind {item?.getChapter()?.toProperty()}
    }

    private fun showCollection() {
        val model = SpaceMarineModel()
        val collectionWindow = VBox()
        val collectionText = Text("Collection")
        val collection = listOf<SpaceMarine>().toObservable()
        collectionWindow.tableview<SpaceMarine> {
            column("Id", SpaceMarine::getId)
            column("Name", SpaceMarine::getName)
            column("Coordinates", SpaceMarine::getCoordinates)
            column("Creation Date", SpaceMarine::getCreationDate)
            column("Health", SpaceMarine::getHealth)
            column("Loyalty", SpaceMarine::getLoyalty)
            column("Category", SpaceMarine::getCategory)
            column("Melee Weapon", SpaceMarine::getWeapon)
            column("Chapter", SpaceMarine::getChapter)

            bindSelected(model)
            smartResize()
        }
        collectionWindow.button("Update Collection") {
            action {
                //TODO: get collection from server
            }
        }
        collectionText.style = "-fx-font-size: 24px;"
        collectionText.style = "-fx-font_family: 'IBM Plex Sans';"
        collectionWindow.add(collectionText)
        root.add(collectionWindow)
    }

    private fun showRacoon() {
        val image = Image("file:Client/src/main/resources/racoon.jpeg", 600.0, 300.0, true, true)
        root.imageview(image)
    }

}
