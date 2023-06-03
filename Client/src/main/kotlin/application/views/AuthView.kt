package application.views

import application.AuthMode
import application.GUI
import application.HeadBar
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tornadofx.*

class AuthView(form: AuthMode) : View() {

    val coroutineScope = CoroutineScope(Dispatchers.Default)
    override val root = anchorpane {
        style = "-fx-background-color: #ffffff; "
        clear()
        add(HeadBar(false, this@AuthView).root)

        pane {
            setPrefSize(450.0, 480.0)
            style =
                "-fx-background-color: #ffffff; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px;"
            layoutX = 495.0
            layoutY = 242.0

            text {
                style =
                    "-fx-text-alignment: left; -fx-font-size: 32px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
                x = 28.0
                y = 52.0
            }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("authView.auth"))

            text {
                style =
                    "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
                x = 28.0
                y = 120.0
            }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("authView.username"))

            val userName = textfield {
                style =
                    "-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
                layoutX = 28.0
                layoutY = 140.0
                setPrefSize(394.0, 42.0)
            }
            userName.promptTextProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("authView.enterUsername"))

            text {
                style =
                    "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
                x = 28.0
                y = 230.0
            }.textProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("authView.password"))

            val userPassword = passwordfield {
                style =
                    "-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
                layoutX = 28.0
                layoutY = 250.0
                setPrefSize(394.0, 42.0)
            }
            userPassword.promptTextProperty().bind(GUI.RESOURCE_FACTORY.getStringBinding("authView.enterPassword"))

            val sendButton = button {
                style =
                    "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
                layoutX = 145.0
                layoutY = 341.0
                setPrefSize(160.0, 42.0)

                setOnMouseEntered {
                    style =
                        "-fx-text-fill: #777; -fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #777; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
                }

                setOnMouseExited {
                    style =
                        "-fx-text-alignment: center; -fx-vertical-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent; -fx-fill: #000000; -fx-position: absolute;"
                }

                setOnMouseClicked {
                    sendAuthRequest(userName, userPassword, form)
                }
            }
            sendButton.textProperty().bind(
                if (form == AuthMode.REGISTRATION) {
                    GUI.RESOURCE_FACTORY.getStringBinding("authView.signUp")
                } else {
                    GUI.RESOURCE_FACTORY.getStringBinding("authView.logIn")
                })

            text {
                style =
                    "-fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
                x = 160.0
                y = 421.0

                setOnMouseEntered {
                    style =
                        "-fx-text-fill: #777; -fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
                }

                setOnMouseExited {
                    style =
                        "-fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
                }

                setOnMouseClicked {
                    if (form == AuthMode.REGISTRATION) {
                        replaceWith(AuthView(AuthMode.LOGIN), ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                    } else if (form == AuthMode.LOGIN) {
                        replaceWith(AuthView(AuthMode.REGISTRATION), ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                    }
                }
            }.textProperty().bind(
                if (form == AuthMode.LOGIN) {
                    GUI.RESOURCE_FACTORY.getStringBinding("authView.dontHaveAcc")
                } else {
                    GUI.RESOURCE_FACTORY.getStringBinding("authView.alreadyHaveAcc")
                })

        }

    }

    fun sendAuthRequest(userName: TextField, userPassword:PasswordField, form: AuthMode) {
        coroutineScope.launch {
            val auth = GUI.console.authorize(userName.text, userPassword.text)
            //val auth = true
            val homeView = GUI.viewsObjectPool.homeView
            runLater {
                if (auth) {
                    replaceWith(homeView, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                } else {
                    if (form == AuthMode.REGISTRATION) {
                        replaceWith(AuthView(AuthMode.REGISTRATION), ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                    } else if (form == AuthMode.LOGIN) {
                        replaceWith(AuthView(AuthMode.LOGIN), ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                    }

                }
            }
        }
        root.clear()
        root.add(GUI.viewsObjectPool.loadingView.root)
    }

}