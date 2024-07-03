package data.models.generics

sealed interface HomeUiState {
    data object Success : HomeUiState
    data object Loading : HomeUiState
    data object Error : HomeUiState
}
