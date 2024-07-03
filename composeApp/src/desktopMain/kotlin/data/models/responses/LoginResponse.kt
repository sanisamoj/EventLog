package data.models.responses

import data.models.generics.Operator

data class LoginResponse(
    val account: Operator,
    val token: String
)