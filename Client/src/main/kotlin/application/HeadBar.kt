package application

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.image.Image
import javafx.scene.paint.Color
import tornadofx.*
import utils.JsonCreator
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


class HeadBar(needAccountIcon: Boolean, currentView: View) : View() {

    override val root = pane {
        setPrefSize(1440.0, 64.0)
        style = "-fx-background-color: #000000; -fx-border-radius: 20px;"

        text {
            text = "Space Marine Client"
            style =
                "-fx-text-alignment: left; -fx-vertical-alignment: top; -fx-font-size: 24px; -fx-font-family: 'IBM Plex Sans'; -fx-fill: #ffffff;"
            x = 20.0
            y = 40.0
        }

        /*
        List of locales
         */
        val observableLocales = FXCollections.observableArrayList(GUI.settings.availableLanguages)
        val selectedLanguage = SimpleStringProperty()
        val selectableList = combobox(selectedLanguage, observableLocales){
            layoutX = 1330.0
            layoutY = 42.0
            isVisible = false
        }
        selectableList.valueProperty().addListener { obs, oldValue, newValue ->
            if (newValue != null) {
                GUI.RESOURCE_FACTORY.setResources(ResourceBundle.getBundle(GUI.RESOURCE_NAME, Locale(newValue)))
                GUI.settings.language = newValue
                selectableList.isVisible = false
            }
        }

        /*
        Language button
         */
        pane {
            layoutX = 1330.0
            layoutY = 22.0
            setPrefSize(20.0, 20.0)
            shape = svgpath {
                content = "M16.417 9.57a7.917 7.917 0 1 1-8.144-7.908 1.758 1.758 0 0 1 .451 0 7.913 7.913 0 0 1 7.693 7.907zM5.85 15.838q.254.107.515.193a11.772 11.772 0 0 1-1.572-5.92h-3.08a6.816 6.816 0 0 0 4.137 5.727zM2.226 6.922a6.727 6.727 0 0 0-.511 2.082h3.078a11.83 11.83 0 0 1 1.55-5.89q-.249.083-.493.186a6.834 6.834 0 0 0-3.624 3.622zm8.87 2.082a14.405 14.405 0 0 0-.261-2.31 9.847 9.847 0 0 0-.713-2.26c-.447-.952-1.009-1.573-1.497-1.667a8.468 8.468 0 0 0-.253 0c-.488.094-1.05.715-1.497 1.668a9.847 9.847 0 0 0-.712 2.26 14.404 14.404 0 0 0-.261 2.309zm-.974 5.676a9.844 9.844 0 0 0 .713-2.26 14.413 14.413 0 0 0 .26-2.309H5.903a14.412 14.412 0 0 0 .261 2.31 9.844 9.844 0 0 0 .712 2.259c.487 1.036 1.109 1.68 1.624 1.68s1.137-.644 1.623-1.68zm4.652-2.462a6.737 6.737 0 0 0 .513-2.107h-3.082a11.77 11.77 0 0 1-1.572 5.922q.261-.086.517-.194a6.834 6.834 0 0 0 3.624-3.621zM11.15 3.3a6.82 6.82 0 0 0-.496-.187 11.828 11.828 0 0 1 1.55 5.89h3.081A6.815 6.815 0 0 0 11.15 3.3z"
                fill = Color.WHITE
            }
            setOnMouseClicked {
                selectableList.isVisible = true
            }
            setMaxSize(20.0,20.0)
            useMaxSize = true
        }

        if (needAccountIcon) {
            imageview {
                image = Image("file:Client/src/main/resources/account_icon.png")
                style = "-fx-text-alignment: left; -fx-vertical-alignment: top;"
                layoutX = 1270.0
                layoutY = 23.0
                fitHeight = 20.0
                fitWidth = 20.0

                setOnMouseClicked {
                    currentView.replaceWith(SettingsView(), ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                }
            }
        }

        /*
        Exit button
         */
        pane {
            layoutX = 1380.0
            layoutY = 16.0
            setPrefSize(20.0, 20.0)
            shape = svgpath {
                content = "M18.8,16l5.5-5.5c0.8-0.8,0.8-2,0-2.8l0,0C24,7.3,23.5,7,23,7c-0.5,0-1,0.2-1.4,0.6L16,13.2l-5.5-5.5  c-0.8-0.8-2.1-0.8-2.8,0C7.3,8,7,8.5,7,9.1s0.2,1,0.6,1.4l5.5,5.5l-5.5,5.5C7.3,21.9,7,22.4,7,23c0,0.5,0.2,1,0.6,1.4  C8,24.8,8.5,25,9,25c0.5,0,1-0.2,1.4-0.6l5.5-5.5l5.5,5.5c0.8,0.8,2.1,0.8,2.8,0c0.8-0.8,0.8-2.1,0-2.8L18.8,16z"
//                layoutX = 1380.0
//                layoutY = 19.0
                fill = Color.WHITE

            }
            setOnMouseClicked {
                val settingsString = JsonCreator().objectToString(GUI.settings)
                Files.write(Paths.get("Client/src/main/resources/settings.json"),settingsString.encodeToByteArray())

                close()
            }
        }


    }

}