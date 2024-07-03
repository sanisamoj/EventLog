import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import context.DefaultAppContainer
import data.models.generics.HomeUiState
import data.models.generics.NavigationItemData
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.components.NavigationRailMenu
import presentation.screens.*
import presentation.viewmodels.AppViewModel
import sanisamoj.eventlogger.library.resources.*

@Composable
@Preview
fun App(
    appViewModel: AppViewModel = viewModel { AppViewModel(DefaultAppContainer) }
) {
    val appUiState by appViewModel.uiState.collectAsState()

    val startDestination = if (appUiState.token == null) {
        EventLoggerScreens.Login
    } else {
        EventLoggerScreens.Home
    }
    var currentRoute by remember { mutableStateOf(startDestination) }
    var showUnreadLogs by remember { mutableStateOf(true) }

    val navigationRailItemsList = listOf(
        NavigationItemData(
            title = stringResource(Res.string.home),
            onClick = { currentRoute = EventLoggerScreens.Home; showUnreadLogs = true },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
        ),
        NavigationItemData(
            title = "Readed",
            onClick = { currentRoute = EventLoggerScreens.Home; showUnreadLogs = false },
            icon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.archive_eye),
                    contentDescription = "Readed"
                )
            }
        ),
        NavigationItemData(
            title = if (appUiState.darkMode) stringResource(Res.string.light) else stringResource(Res.string.dark),
            onClick = { appViewModel.updateDarkMode(!appUiState.darkMode) },
            icon = {
                Icon(
                    painterResource(
                        resource = if (appUiState.darkMode) {
                            Res.drawable.wb_sunny_24dp_FILL0_wght400_GRAD0_opsz24
                        } else {
                            Res.drawable.dark_mode_24dp_FILL0_wght400_GRAD0_opsz24
                        }
                    ),
                    contentDescription = stringResource(Res.string.theme)
                )
            }
        ),
        NavigationItemData(
            title = stringResource(Res.string.settings),
            onClick = { currentRoute = EventLoggerScreens.Settings },
            icon = { Icon(Icons.Filled.Settings, contentDescription = stringResource(Res.string.settings)) }
        ),
        NavigationItemData(
            title = stringResource(Res.string.leave),
            onClick = { appViewModel.signOut(); currentRoute = EventLoggerScreens.Login },
            icon = {
                Icon(
                    painterResource(Res.drawable.logout_24dp_FILL0_wght400_GRAD0_opsz24),
                    contentDescription = stringResource(Res.string.signout)
                )
            }
        )
    )

    Scaffold(
        content = { innerPadding ->
            Row(Modifier.fillMaxSize()) {
                val showNavigation: Boolean = when(currentRoute) {
                    EventLoggerScreens.Home -> true
                    EventLoggerScreens.Settings -> true
                    else -> { false }
                }
                if(showNavigation) { NavigationRailMenu(navigationRailItemsList) }

                Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                    when (currentRoute) {
                        EventLoggerScreens.Login -> {
                            LoginScreen(
                                saveOperator = { operator -> appViewModel.updateOperator(operator) },
                                onRegisterClick = { currentRoute = EventLoggerScreens.SignUp },
                                saveToken = { token -> appViewModel.updateToken(token) },
                                onLogin = { currentRoute = EventLoggerScreens.Home },
                                eventRepository = appViewModel.getEventRepository()
                            )
                        }

                        EventLoggerScreens.SignUp -> {
                            RegisterScreen(
                                onRegisterSuccess = { currentRoute = EventLoggerScreens.Validation },
                                onLoginButton = { currentRoute = EventLoggerScreens.Login },
                                eventRepository = appViewModel.getEventRepository()
                            )
                        }

                        EventLoggerScreens.Validation -> {
                            VerificationScreen(
                                onReturnToLogin = { currentRoute = EventLoggerScreens.Login }
                            )
                        }

                        EventLoggerScreens.Home -> {
                            HomeScreen(
                                unreadPage = showUnreadLogs,
                            )
                        }

                        EventLoggerScreens.Settings -> {
                            SettingsScreen(
                                isDarkMode = appUiState.darkMode,
                                pageSize = appUiState.pageSize,
                                updateDarkMode = { appViewModel.updateDarkMode(!appUiState.darkMode) },
                                updatePageSize = { appViewModel.updatePageSize(it) },
                                deletesUserPreferences = { appViewModel.signOut() }
                            )
                        }
                    }
                }
            }
        }
    )
}
