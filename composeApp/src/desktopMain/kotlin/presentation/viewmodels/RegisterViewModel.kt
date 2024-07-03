package presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.models.generics.RegisterUiState
import data.models.interfaces.EventLoggerRepository
import data.models.requests.CreateOperatorRequest
import error.errorResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import sanisamoj.eventlogger.library.resources.Res
import sanisamoj.eventlogger.library.resources.some_incorrect_data
import utils.buildHttpExceptionError

class RegisterViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    var allTextFieldsEnabled by mutableStateOf(true)
    var errorText by mutableStateOf(Res.string.some_incorrect_data)
    var registerError by mutableStateOf(false)
    var isLoading by mutableStateOf(false)

    fun updateName(name: String) {
        registerError = false
        _uiState.update { currentState ->
            currentState.copy(name = name)
        }
    }

    fun updateEmail(email: String) {
        registerError = false
        _uiState.update { currentState ->
            currentState.copy(email = email)
        }
    }

    fun updatePhone(phone: String) {
        registerError = false
        _uiState.update { currentState ->
            currentState.copy(phone = phone)
        }
    }

    fun updatePassword(password: String) {
        registerError = false
        _uiState.update { currentState ->
            currentState.copy(password = password)
        }
    }

    fun register(
        eventRepository: EventLoggerRepository,
        onRegisterSuccess: () -> Unit = {}
    ) {
        isLoading = true
        allTextFieldsEnabled = false

        val createOperatorRequest = CreateOperatorRequest(
            name = uiState.value.name,
            email = uiState.value.email,
            phone = uiState.value.phone,
            password =  uiState.value.password
        )

        viewModelScope.launch {
            try {
                eventRepository.createOperator(createOperatorRequest)
                eventRepository.generateValidationCode(createOperatorRequest.email)
                onRegisterSuccess()

            } catch (e: HttpException) {
                val error = buildHttpExceptionError(e).error
                errorText = errorResponse(error)
            } catch (e: Throwable) {
                errorText = errorResponse(null)
            } finally {
                allTextFieldsEnabled = true
                registerError = true
                isLoading = false
            }
        }
    }
}