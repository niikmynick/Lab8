package application

import javafx.animation.Animation
import javafx.animation.Interpolator
import javafx.animation.PathTransition
import javafx.geometry.Pos
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.shape.ArcTo
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import javafx.util.Duration
import tornadofx.*

class LoadingView : View() {
    override val root = anchorpane {
        clear()

        vbox {
            style = "-fx-background-color: #ffffff; "
            val image = ImageView(Image("file:Client/src/main/resources/miniSpaceMarine.png", 53.8, 66.0, true, true))
            alignment = Pos.CENTER

            add(HeadBar(false, this@LoadingView).root)

            add(image)

            val path = Path().apply {
                val x = 0.0
                val y = 450.0
                val radius = 180.0
                elements.add(MoveTo(x+(radius/2), y-(radius/2)))
                elements.add(ArcTo(radius, radius, 0.0, x+(radius/2) -.1, y-(radius/2) -.1, true, true))
            }

            text {
                text = GUI.rb.getString("authView.loading")
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

        }
    }

}