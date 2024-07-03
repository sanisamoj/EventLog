package presentation.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import data.models.generics.AppUiState
import data.models.generics.Operator
import data.models.interfaces.EventLoggerRepository
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.components.ButtonWithLoading
import presentation.components.TextFieldWithIcon
import presentation.viewmodels.LoginViewModel
import sanisamoj.eventlogger.library.resources.*

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    eventRepository: EventLoggerRepository,
    saveOperator: (Operator) -> Unit = {},
    saveToken: (String) -> Unit = {},
    onRegisterClick: () -> Unit =  {},
    onLogin: () -> Unit = {}
) {
    val loginUiState by loginViewModel.uiState.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .statusBarsPadding()
            .safeDrawingPadding()
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onSecondary,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 25.dp)
                .size(width = 500.dp, height = 420.dp)
                .wrapContentSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 45.dp)
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(resource = Res.string.login),
                    fontFamily = FontFamily(Font(Res.font.inria_sans_bold)),
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextFieldWithIcon(
                    enabled = loginViewModel.allTextFieldsEnabled,
                    isError = loginViewModel.loginError,
                    currentText = loginUiState.email,
                    label = stringResource(resource = Res.string.email),
                    updateTextField = { loginViewModel.updateEmail(it) },
                    leadingIcon = painterResource(resource = Res.drawable.at),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextFieldWithIcon(
                    enabled = loginViewModel.allTextFieldsEnabled,
                    isError = loginViewModel.loginError,
                    currentText = loginUiState.password,
                    label = stringResource(resource = Res.string.password),
                    updateTextField = { loginViewModel.updatePassword(it) },
                    leadingIcon = painterResource(resource = Res.drawable.lock),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                if (loginViewModel.loginError) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            fontFamily = FontFamily(Font(Res.font.inria_sans_regular)),
                            text = stringResource(loginViewModel.loginErrorText),
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Checkbox(
                        enabled = loginViewModel.allTextFieldsEnabled,
                        checked = loginUiState.stayConnected,
                        onCheckedChange = { loginViewModel.updateStayConnected(!loginUiState.stayConnected) }
                    )
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(resource = Res.string.stay_connected),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                ButtonWithLoading(
                    enabled = loginViewModel.allTextFieldsEnabled && !loginViewModel.loginError,
                    text = stringResource(resource = Res.string.sign_in),
                    onClick = {
                        loginViewModel.login(
                            keepConnected = loginUiState.stayConnected,
                            repository = eventRepository,
                            saveOperator = saveOperator,
                            saveToken = saveToken,
                            onLogin = onLogin
                        )
                    },
                    loading = loginViewModel.isLoading
                )

                if(loginViewModel.allTextFieldsEnabled) {
                    Text(
                        text = stringResource(resource = Res.string.dont_have_account),
                        modifier = Modifier.clickable { onRegisterClick() },
                        style = TextStyle(
                            fontFamily = FontFamily(Font(Res.font.inria_sans_bold)),
                            textDecoration = TextDecoration.Underline,
                            fontSize = 18.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }

}