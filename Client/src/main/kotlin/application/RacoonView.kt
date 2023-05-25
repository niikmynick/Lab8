package application

import javafx.scene.image.Image
import tornadofx.*

class RacoonView() : View() {
    override val root = pane {

        add(HeadBar(true).root)

        add(LeftMenu().root)

        imageview {
            image = Image("file:Client/src/main/resources/racoon.jpeg", 1300.0, 800.0, true, true)
            fitHeight = 500.0
            fitWidth = 500.0
            x = 72.0
            y = 84.0
        }
    }
}