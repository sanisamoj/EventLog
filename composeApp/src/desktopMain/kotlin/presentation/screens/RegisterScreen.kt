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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import data.models.interfaces.EventLoggerRepository
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.components.ButtonWithLoading
import presentation.components.TextFieldWithIcon
import presentation.viewmodels.RegisterViewModel
import sanisamoj.eventlogger.library.resources.*

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = viewModel(),
    eventRepository: EventLoggerRepository,
    onLoginButton: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {}
) {
    val registerUiState by registerViewModel.uiState.collectAsState()

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
                .size(width = 500.dp, height = 510.dp)
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
                    text = stringResource(resource = Res.string.register_your_account),
                    fontFamily = FontFamily(Font(Res.font.inria_sans_bold)),
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextFieldWithIcon(
                    enabled = registerViewModel.allTextFieldsEnabled,
                    isError = registerViewModel.registerError,
                    currentText = registerUiState.name,
                    label = stringResource(Res.string.name),
                    updateTextField = { registerViewModel.updateName(it) },
                    leadingIcon = painterResource(resource = Res.drawable.account_tie),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextFieldWithIcon(
                    enabled = registerViewModel.allTextFieldsEnabled,
                    isError = registerViewModel.registerError,
                    currentText = registerUiState.email,
                    label = stringResource(Res.string.email),
                    updateTextField = { registerViewModel.updateEmail(it) },
                    leadingIcon = painterResource(resource = Res.drawable.at),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextFieldWithIcon(
                    enabled = registerViewModel.allTextFieldsEnabled,
                    isError = registerViewModel.registerError,
                    currentText = registerUiState.phone,
                    label = stringResource(Res.string.phone),
                    updateTextField = { registerViewModel.updatePhone(it) },
                    leadingIcon = painterResource(resource = Res.drawable.cellphone),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextFieldWithIcon(
                    enabled = registerViewModel.allTextFieldsEnabled,
                    isError = registerViewModel.registerError,
                    currentText = registerUiState.password,
                    label = stringResource(Res.string.password),
                    updateTextField = { registerViewModel.updatePassword(it) },
                    leadingIcon = painterResource(resource = Res.drawable.lock),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                if (registerViewModel.registerError) {
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
                            fontSize = 12.sp,
                            text = stringResource(registerViewModel.errorText),
                            color = MaterialTheme.colorScheme.error,
                            fontFamily = FontFamily(Font(Res.font.inria_sans_regular)),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                ButtonWithLoading(
                    enabled = (!registerViewModel.registerError && registerViewModel.allTextFieldsEnabled),
                    text = stringResource(Res.string.register),
                    onClick = {
                        registerViewModel.register(
                            eventRepository = eventRepository,
                            onRegisterSuccess = onRegisterSuccess
                        )
                    },
                    loading = registerViewModel.isLoading
                )

                if(registerViewModel.allTextFieldsEnabled) {
                    Text(
                        text = stringResource(resource = Res.string.i_already_have_an_account),
                        modifier = Modifier
                            .clickable {
                                if (registerViewModel.allTextFieldsEnabled) onLoginButton()
                            },
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