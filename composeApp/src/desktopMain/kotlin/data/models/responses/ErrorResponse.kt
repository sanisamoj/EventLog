package data.models.responses

data class ErrorResponse(
    val error: String,
    val details: String? = null
)