package error

import org.jetbrains.compose.resources.StringResource
import sanisamoj.eventlogger.library.resources.*

fun errorResponse(error: String?): StringResource {
    return when(error) {
        Errors.InvalidEmailOrPassword.description -> Res.string.invalid_email_or_password
        Errors.OperatorDoesNotExist.description -> Res.string.invalid_email_or_password
        Errors.AccountDoesNotExist.description -> Res.string.invalid_email_or_password
        Errors.OperatorAlreadyExist.description -> Res.string.operator_already_exist
        Errors.AccountNotActivated.description -> Res.string.account_not_activated
        Errors.SomeDataIsMissing.description -> Res.string.some_data_is_missing
        Errors.BlockedAccount.description -> Res.string.blocked_account
        Errors.TooManyRequest.description -> Res.string.try_again_in_1h
        else -> Res.string.internal_server_error
    }
}