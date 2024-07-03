package data.models.generics

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val stayConnected: Boolean = true
)
