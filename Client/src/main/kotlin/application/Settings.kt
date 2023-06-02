package application

import kotlinx.serialization.Serializable

@Serializable
data class Settings(var language: String, var availableLanguages: List<String>, var host: String, var port: Int) {
}