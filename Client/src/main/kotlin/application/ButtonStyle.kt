package application

import tornadofx.*

class ButtonStyle : Stylesheet() {
    companion object {
        val button by cssclass()
    }

    init {
        button {
            backgroundColor += c("#000000")
            textFill = c("#ffffff")
            and(hover) {
                backgroundColor += c("#ffffff")
                textFill = c("#000000")
            }
        }
    }
}