package presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.models.generics.LoginUiState
import data.models.generics.Operator
import data.models.interfaces.EventLoggerRepository
import error.errorResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import sanisamoj.eventlogger.library.resources.Res
import sanisamoj.eventlogger.library.resources.invalid_email_or_password
import utils.buildHttpExceptionError

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    var allTextFieldsEnabled by mutableStateOf(true)
    var isLoading by mutableStateOf(false)
    var loginError by mutableStateOf(false)
    var loginErrorText by mutableStateOf(Res.string.invalid_email_or_password)

    fun updateEmail(email: String) {
        loginError = false
        _uiState.update { currentState ->
            currentState.copy(email = email)
        }
    }

    fun updatePassword(password: String) {
        loginError = false
        _uiState.update { currentState ->
            currentState.copy(password = password)
        }
    }

    fun updateStayConnected(value: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(stayConnected = value)
        }
    }

    fun login(
        saveOperator: (Operator) -> Unit = {},
        repository: EventLoggerRepository,
        keepConnected: Boolean = true,
        saveToken: (String) -> Unit,
        onLogin: () -> Unit = {}
    ) {
        allTextFieldsEnabled = false
        isLoading = true

        viewModelScope.launch {
            val login = uiState.value

            try {
                val loginResponse = repository.login(login.email, login.password)
                if (keepConnected) saveToken(loginResponse.token)
                saveOperator(loginResponse.account)
                onLogin()

            } catch (e: HttpException) {
                val error = buildHttpExceptionError(e).error
                loginErrorText = errorResponse(error)
                loginError = true
            } catch (e: Throwable) {
                println(e)
                loginErrorText = errorResponse(null)
                loginError = true
            } finally {
                allTextFieldsEnabled = true
                isLoading = false
            }
        }
    }
}