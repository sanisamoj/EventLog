package utils

import data.models.responses.ErrorResponse
import org.jetbrains.compose.resources.stringResource
import retrofit2.HttpException

fun buildHttpExceptionError(error: HttpException): ErrorResponse {
    error.response()?.run {
        errorBody()?.let {
            val errorJsonInString = it.string()
            val errorResponse = ObjectConverter().stringToObject<ErrorResponse>(errorJsonInString)
            return errorResponse
        }
    }

    return ErrorResponse(
        error = "Erro interno do servidor, por favor tente novamente mais tarde, ou entre em contato com o suporte!"
    )
}