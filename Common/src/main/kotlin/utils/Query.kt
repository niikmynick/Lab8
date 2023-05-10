package utils

import kotlinx.serialization.Serializable

@Serializable
data class Query (val queryType: QueryType, val message: String, val args: MutableMap<String, String>, val token: String = "")
