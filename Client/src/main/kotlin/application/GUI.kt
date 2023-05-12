package application

import javafx.stage.Stage
import tornadofx.*

class GUI : App(MainView::class) {
    override fun start(stage: Stage) {
        stage.isResizable = false
        super.start(stage)
    }
}
