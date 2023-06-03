package application

import javafx.scene.control.TextField
import java.io.InputStream

class TextAreaInputStream(textField: TextField) : InputStream() {
    private var index = 0
    private val text = textField.text

    override fun read(): Int {
        return if (index < text.length) {
            text[index++].code
        } else {
            -1
        }
    }


}