package presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import context.AppContainer
import data.models.generics.AppUiState
import data.models.generics.Operator
import data.models.generics.UserPreferences
import data.models.interfaces.ContextUiState
import data.models.interfaces.EventLoggerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AppViewModel(appContainer: AppContainer) : ViewModel() {
    private val eventRepository = appContainer.eventRepository
    private val preferences = appContainer.preferences
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        initializePreferences()
    }

    private fun initializePreferences() {
        val userPreferences = getPreferences()

        runBlocking {
            if (userPreferences != null) {
                try {
                    val operator = eventRepository.session(userPreferences.token)
                    updateOperator(operator)
                    updateAllPreferences(userPreferences)
                    return@runBlocking
                } catch (_: Throwable) { }
            }
        }
    }

    fun updateOperator(operator: Operator) {
        _uiState.update { currentState -> currentState.copy(operator = operator) }
    }

    fun updateToken(token: String) {
        _uiState.update { currentState -> currentState.copy(token = token) }
        preferences.saveToken(token)
    }

    fun updateDarkMode(value: Boolean) {
        _uiState.update { currentState -> currentState.copy(darkMode = value) }
        preferences.updateDarkMode(value)
    }

    fun updatePageSize(value: Int) {
        _uiState.update { currentState -> currentState.copy(pageSize = value) }
        preferences.updatePageSize(value)
    }

    private fun updateAllPreferences(preferences: UserPreferences) {
        updateDarkMode(preferences.darkMode)
        updatePageSize(preferences.pageSize)
        updateToken(preferences.token)
    }

    private fun getPreferences(): UserPreferences? {
        return preferences.getPreferences()
    }

    fun getEventRepository(): EventLoggerRepository {
        return eventRepository
    }

    fun signOut() {
        val token = uiState.value.token!!

        try {
            viewModelScope.launch {
                eventRepository.signOut(token)
                preferences.deletePreferences()
            }
        } catch (_: Throwable) {}
    }
}