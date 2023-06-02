package application

import javafx.beans.binding.StringBinding
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import java.util.*


class ObservableResourceFactory {
    private val resources: ObjectProperty<ResourceBundle> = SimpleObjectProperty()
    fun resourcesProperty(): ObjectProperty<ResourceBundle> {
        return resources
    }

    fun getResources(): ResourceBundle {
        return resourcesProperty().get()
    }

    fun setResources(resources: ResourceBundle) {
        resourcesProperty().set(resources)
    }

    fun getStringBinding(key: String?): StringBinding {
        return object : StringBinding() {
            init {
                bind(resourcesProperty())
            }

            public override fun computeValue(): String {
                return getResources().getString(key)
            }
        }
    }
}