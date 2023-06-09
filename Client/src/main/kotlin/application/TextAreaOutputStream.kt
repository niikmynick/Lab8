package application

import javafx.scene.control.TextArea
import java.io.IOException
import java.io.OutputStream
import java.nio.charset.Charset

class TextAreaOutputStream(private val textArea: TextArea): OutputStream() {

    /**
     * This doesn't support multibyte characters streams like utf8
     */
    @Throws(IOException::class)
    override fun write(b: Int) {
        throw UnsupportedOperationException()
    }

    /**
     * Supports multibyte characters by converting the array buffer to String
     */
    @Throws(IOException::class)
    override fun write(b: ByteArray, off: Int, len: Int) {
        // redirects data to the text area
        textArea.appendText(String(b.copyOf(len), Charset.defaultCharset()))
        // scrolls the text area to the end of data
        textArea.scrollTop = java.lang.Double.MAX_VALUE
    }

}