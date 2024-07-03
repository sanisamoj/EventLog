package presentation.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import context.DefaultAppContainer
import data.models.generics.*
import data.models.interfaces.EventLoggerRepository
import data.models.interfaces.PreferencesRepository
import data.repository.DefaultEventRepository
import data.repository.DefaultPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val eventRepository: EventLoggerRepository = DefaultAppContainer.eventRepository,
    private val preferencesRepository: PreferencesRepository = DefaultAppContainer.preferences
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiStateData())
    val uiState: StateFlow<HomeUiStateData> = _uiState.asStateFlow()

    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)

    var filterType by mutableStateOf<EventType?>(null)
    var filterSeverity by mutableStateOf<EventSeverity?>(null)
    var filterRead by mutableStateOf<Boolean>(false)

    init {
        val preferences = preferencesRepository.getPreferences()
        val pageSize = preferences?.pageSize ?: 30
        loadAllReadEventLogs(pageSize)
        loadAllUnreadLogs()
    }

    fun loadAllReadEventLogs(pageSize: Int = 30) {
        var page = _uiState.value.readPage
        viewModelScope.launch {
            try {
                val eventLogsResponse = eventRepository.getAllEventLogs(true, page, pageSize)

                // Caso a página requisitada seja maior que o total de páginas, não prossegue
                val totalPages = eventLogsResponse.paginationResponse.totalPages
                if (page > totalPages) return@launch

                val listInApp = _uiState.value.readLogEvents.toMutableList()
                val updatedList = eventLogsResponse.content.filterNot { listInApp.contains(it) }
                listInApp.addAll(updatedList)
                updateReadEventLogs(listInApp)

                if (page < totalPages) {
                    readPageInc() // Incrementa a página se ainda não está na última página
                }

                homeUiState = HomeUiState.Success
            } catch (e: Throwable) {
                homeUiState = HomeUiState.Error
            }
        }
    }

    private fun readPageInc() {
        _uiState.update { currentState ->
            currentState.copy(readPage = currentState.readPage + 1)
        }
    }

    fun eraseAllLogEvents() {
        _uiState.update { currentState ->
            currentState.copy(
                readLogEvents = emptyList(),
                unreadLogEvents = emptyList(),
                readPage = 1,
                unReadPage = 1
            )
        }
    }

    fun loadAllUnreadLogs(pageSize: Int = 30) {
        var page = _uiState.value.unReadPage
        viewModelScope.launch {
            try {
                val eventLogsResponse = eventRepository.getAllEventLogs(false, page, pageSize)

                val totalPages = eventLogsResponse.paginationResponse.totalPages
                if (page > totalPages) return@launch

                val listInApp = _uiState.value.unreadLogEvents.toMutableList()
                val updatedList = eventLogsResponse.content.filterNot { listInApp.contains(it) }
                listInApp.addAll(updatedList)
                updateUnreadEventLogs(listInApp)

                if (page < totalPages) {
                    unreadPageInc() // Incrementa a página se ainda não está na última página
                }

                homeUiState = HomeUiState.Success
            } catch (e: Throwable) {
                homeUiState = HomeUiState.Error
            }
        }
    }

    private fun unreadPageInc() {
        _uiState.update { currentState ->
            currentState.copy(unReadPage = currentState.unReadPage + 1)
        }
    }

    private fun updateReadEventLogs(logs: List<LogEvent>) {
        _uiState.update { currentState ->
            currentState.copy(readLogEvents = logs)
        }
    }

    private fun updateUnreadEventLogs(logs: List<LogEvent>) {
        _uiState.update { currentState ->
            currentState.copy(unreadLogEvents = logs)
        }
    }

    fun read(logId: String) {
        var unreadListInApp = _uiState.value.unreadLogEvents.toMutableList()
        val readListInApp = _uiState.value.readLogEvents.toMutableList()

        viewModelScope.launch {
            try {
                eventRepository.readEventLog(logId, true)
                val log = unreadListInApp.find { it.id == logId }
                val index = unreadListInApp.indexOfFirst { it.id == logId }
                unreadListInApp.removeAt(index)

                if(log != null) {
                    val updatedLog = log.copy(read = true)
                    readListInApp.addFirst(updatedLog)
                    updateReadEventLogs(readListInApp)
                    updateUnreadEventLogs(unreadListInApp)
                }

            } catch (e: Throwable) {
                homeUiState = HomeUiState.Error
            }
        }
    }
}