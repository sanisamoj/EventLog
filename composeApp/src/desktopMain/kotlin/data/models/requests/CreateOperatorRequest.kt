package data.models.requests

data class CreateOperatorRequest(
    val name: String,
    val email: String,
    val phone: String,
    val password: String
)