package application

import javafx.stage.Stage
import tornadofx.*

class GUI : App(MainView::class) {
    override fun start(stage: Stage) {
        stage.isResizable = true
        super.start(stage)
    }
}
