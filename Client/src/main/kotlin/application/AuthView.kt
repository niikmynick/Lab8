package application

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tornadofx.*

class AuthView(form: AuthMode) : View() {

    val console = GUI.console

    override val root = anchorpane {
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        clear()
        add(HeadBar(false, this@AuthView).root)

        setOnKeyTyped {

        }
        pane {
            setPrefSize(450.0, 480.0)
            style =
                "-fx-background-color: #ffffff; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px;"
            layoutX = 495.0
            layoutY = 242.0

            text {
                text = GUI.rb.getString("authView.auth")
                style =
                    "-fx-text-alignment: left; -fx-font-size: 32px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
                x = 28.0
                y = 52.0
            }

            text {
                text = GUI.rb.getString("authView.username")
                style =
                    "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
                x = 28.0
                y = 120.0
            }

            val userName = textfield {
                promptText = GUI.rb.getString("authView.enterUsername")
                style =
                    "-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
                layoutX = 28.0
                layoutY = 140.0
                setPrefSize(394.0, 42.0)
            }

            text {
                text = GUI.rb.getString("authView.password")
                style =
                    "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
                x = 28.0
                y = 230.0
            }

            val userPassword = passwordfield {
                promptText = GUI.rb.getString("authView.enterPassword")
                style =
                    "-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
                layoutX = 28.0
                layoutY = 250.0
                setPrefSize(394.0, 42.0)
            }

            button {
                text = if (form == AuthMode.REGISTRATION) {
                    GUI.rb.getString("authView.signUp")
                } else {
                    GUI.rb.getString("authView.logIn")
                }
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
                    coroutineScope.launch {
                        val auth = console.authorize(userName.text, userPassword.text)
                        //val auth = true
                        runLater {
                            if (auth) {
                                replaceWith(HomeView())
                            } else {
                                if (form == AuthMode.REGISTRATION) {
                                    replaceWith(AuthView(AuthMode.REGISTRATION))
                                } else if (form == AuthMode.LOGIN) {
                                    replaceWith(AuthView(AuthMode.LOGIN))
                                }

                            }
                        }
                    }
                    this@anchorpane.add(LoadingView().root)
                }
            }

            text {
                text = if (form == AuthMode.LOGIN) {
                    GUI.rb.getString("authView.dontHaveAcc")
                } else {
                    GUI.rb.getString("authView.alreadyHaveAcc")
                }
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
                        replaceWith(AuthView(AuthMode.LOGIN))
                    } else if (form == AuthMode.LOGIN) {
                        replaceWith(AuthView(AuthMode.REGISTRATION))
                    }
                }
            }

        }

    }

}