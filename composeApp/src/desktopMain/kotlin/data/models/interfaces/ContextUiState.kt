package data.models.interfaces

sealed interface ContextUiState {
    data object Error : ContextUiState
    data object Loading : ContextUiState
}
