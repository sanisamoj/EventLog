package data.models.requests

data class ValidationAccountRequest(
    val email: String,
    val code: Int
)