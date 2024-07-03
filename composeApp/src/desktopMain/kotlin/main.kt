import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.lifecycle.viewmodel.compose.viewModel
import context.DefaultAppContainer
import presentation.viewmodels.AppViewModel
import ui.theme.EventLoggerTheme

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "EventLog",
    ) {
        val appViewModel: AppViewModel = viewModel { AppViewModel(DefaultAppContainer) }
        val appUiState by appViewModel.uiState.collectAsState()

        EventLoggerTheme(darkTheme = appUiState.darkMode) {
            App(appViewModel = appViewModel)
        }
    }
}