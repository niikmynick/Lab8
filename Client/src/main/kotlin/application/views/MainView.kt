package application.views

import clientUtils.Console
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
