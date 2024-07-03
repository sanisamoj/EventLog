package data.models.generics

data class UserPreferences(
    val token: String = "",
    val darkMode: Boolean = false,
    val pageSize: Int = 30
)
