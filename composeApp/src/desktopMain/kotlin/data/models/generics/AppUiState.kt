package data.models.generics

data class AppUiState(
    val email: String = "",
    val operator: Operator = Operator(),
    val darkMode: Boolean = false,
    val token: String? = null,
    val pageSize: Int = 30
)
