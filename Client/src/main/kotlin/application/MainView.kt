package application

import clientUtils.Console
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import tornadofx.*

class MainView : View() {
//    private val client = Console("localhost", 8061)

    override val root = VBox()

    init {
        showWelcome()
    }

    private fun showWelcome() {
        val loginText = Text("Hello, Before we start you have to log in to your account")
        val loginButton = button ("Log in") {
            action {
                showLoginDialog()
            }
        }

        loginText.style = "-fx-font-size: 24px;"
        loginText.style = "-fx-font_family: 'IBM Plex Sans';"
        loginButton.style = "-fx-font-size: 14px;"
        loginButton.style = "-fx-font_family: 'IBM Plex Sans';"

        root.alignment = Pos.CENTER

        // Add text and login button to the root view
        root.add(loginText)
        root.add(loginButton)
    }

    private fun showLoginDialog() {
        // Create a new dialog for user to enter login details
        val dialog = Dialog<Pair<String, String>>()
        dialog.title = "Log in"
        dialog.headerText = "Please enter your username and password."

        // Set the button types
        val loginButtonType = ButtonType("Log in", ButtonBar.ButtonData.OK_DONE)
        dialog.dialogPane.buttonTypes.addAll(loginButtonType, ButtonType.CANCEL)

        // Create the username and password labels and fields
        val username = TextField()
        val password = PasswordField()
        val grid = GridPane()
        grid.add(Label("Username:"), 0, 0)
        grid.add(username, 1, 0)
        grid.add(Label("Password:"), 0, 1)
        grid.add(password, 1, 1)

        // Enable/disable login button depending on whether username and password are entered
        val loginButton = dialog.dialogPane.lookupButton(loginButtonType)
        loginButton.isDisable = true
        username.textProperty().addListener { _, _, newValue ->
            loginButton.isDisable = newValue.trim().isEmpty() || password.text.trim().isEmpty()
        }
        password.textProperty().addListener { _, _, newValue ->
            loginButton.isDisable = newValue.trim().isEmpty() || username.text.trim().isEmpty()
        }

        dialog.dialogPane.content = grid

        // Focus on the username field by default
        Platform.runLater { username.requestFocus() }

        // Convert the result to a username-password-pair when the login button is clicked
        dialog.setResultConverter { dialogButton ->
            if (dialogButton == loginButtonType) {
                Pair(username.text, password.text)
            } else {
                null
            }
        }

        val result = dialog.showAndWait()

        // Update the UI based on whether the user is logged in or not
        if (result.isPresent) {
            root.clear()
            root.alignment = Pos.TOP_LEFT
            showMenu()
        }
    }

    private fun showMenu() {
        // Create a menu with options to go to console, settings, and a separate menu
        val menuBar = MenuBar()

        menuBar.style = "-fx-background-color: #000000;"
        menuBar.style = "-fx-font-size: 14px;"
        menuBar.style = "-fx-font_family: 'IBM Plex Sans';"

        val consoleMenu = Menu()
        val settingsMenu = Menu()
        val separateMenu = Menu()

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

        separateMenu.button("Separate Menu") {
            action {
                root.clear()
                showMenu()
                showSeparateMenu()
            }
        }
        menuBar.menus.addAll(consoleMenu, settingsMenu, separateMenu)

        root.add(menuBar)
    }

    private fun showConsole() {
        val outputArea = TextArea()
        outputArea.isEditable = false
        outputArea.isWrapText = true

        val inputField = TextField()

        val commandWindow = VBox()
        commandWindow.children.addAll(outputArea, inputField)

        // Handle user input
        inputField.setOnAction {
            val command = inputField.text
            outputArea.appendText("> $command\n")
            // send the command to the server and get the response
            outputArea.appendText("Response\n")
            inputField.clear()
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

    private fun showSeparateMenu() {
        val image = Image("file:Client/src/main/resources/racoon.jpeg", 600.0, 300.0, true, true)
        root.imageview(image)
    }

}
