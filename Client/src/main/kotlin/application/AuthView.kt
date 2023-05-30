package application

import javafx.animation.Animation
import javafx.animation.Interpolator
import javafx.animation.PathTransition
import javafx.geometry.Pos
import javafx.geometry.VPos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.shape.ArcTo
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import javafx.util.Duration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tornadofx.*

class AuthView(form: AuthMode) : View() {

    val console = GUI.console

    override val root = anchorpane {
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        clear()
        add(HeadBar(false).root)

        pane {
            setPrefSize(450.0, 480.0)
            style =
                "-fx-background-color: #ffffff; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px;"
            layoutX = 495.0
            layoutY = 242.0

            text {
                text = "Auth"
                style =
                    "-fx-text-alignment: left; -fx-font-size: 32px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
                x = 28.0
                y = 52.0
            }

            text {
                text = "Username"
                style =
                    "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
                x = 28.0
                y = 120.0
            }

            val userName = textfield {
                promptText = "Enter your username here"
                style =
                    "-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
                layoutX = 28.0
                layoutY = 140.0
                setPrefSize(394.0, 42.0)
            }

            text {
                text = "Password"
                style =
                    "-fx-text-alignment: left; -fx-font-size: 16px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; "
                x = 28.0
                y = 230.0
            }

            val userPassword = passwordfield {
                promptText = "Enter your password here"
                style =
                    "-fx-text-alignment: left; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute; -fx-border-radius: 20px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-background-color: transparent;"
                layoutX = 28.0
                layoutY = 250.0
                setPrefSize(394.0, 42.0)
            }

            button {
                text = if (form == AuthMode.REGISTRATION) {
                    "Sign up"
                } else {
                    "Log In"
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

                    loadingAnimation()

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
                }
            }

            text {
                text = if (form == AuthMode.LOGIN) {
                    "Don't have an account?\nRegister"
                } else {
                    "Already have an account?\nLog in"
                }
                style =
                    "-fx-text-alignment: center; -fx-font-size: 14px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
                x = 152.0
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

    private fun loadingAnimation() {
        root.clear()

        val image = ImageView(Image("file:Client/src/main/resources/miniSpaceMarine.png", 53.8, 66.0, true, true))

        root.add(vbox {
            alignment = Pos.CENTER

            add(HeadBar(false).root)

            add(image)

            val path = Path().apply {
                val x = 0.0
                val y = 450.0
                val radius = 180.0
                elements.add(MoveTo(x+(radius/2), y-(radius/2)))
                elements.add(ArcTo(radius, radius, 0.0, x+(radius/2) -.1, y-(radius/2) -.1, true, true))
            }

            text {
                text = "loading ..."
                style =
                    "-fx-text-alignment: center; -fx-font-size: 24px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #000000; -fx-position: absolute;"
            }

            val pathTransition = PathTransition().apply {
                duration = Duration(2000.0)
                this.path = path
                node = image
                orientation = PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT
                cycleCount = Animation.INDEFINITE
                interpolator = Interpolator.LINEAR
            }

            pathTransition.play()

        })
    }

}