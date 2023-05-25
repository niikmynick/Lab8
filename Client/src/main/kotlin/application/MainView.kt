package application

import basicClasses.SpaceMarine
import clientUtils.Console
import exceptions.InvalidInputException
import exceptions.NotAuthorized
import javafx.animation.Animation
import javafx.animation.Interpolator
import javafx.animation.PathTransition
import javafx.animation.PathTransition.OrientationType
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.shape.*
import javafx.scene.text.Text
import javafx.util.Duration
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import tornadofx.*


class MainView : View() {
    private val console = Console("localhost", 8061)

    override var root = anchorpane {
        prefWidth = 1440.0
        prefHeight = 900.0
    }

    private fun loadingAnimation() {

    }


}
