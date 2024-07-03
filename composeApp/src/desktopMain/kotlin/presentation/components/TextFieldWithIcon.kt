package presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldWithIcon(
    modifier: Modifier = Modifier,
    currentText: String,
    label: String,
    leadingIcon: Painter? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    updateTextField: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.None
    ),
    keyboardActions: () -> Unit = {},
    hiddenKeyboard: Boolean = false
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = modifier.fillMaxWidth(),
        value = currentText,
        enabled = enabled,
        singleLine = true,
        label = { Text(text = label, style = MaterialTheme.typography.bodyMedium) },
        leadingIcon = {
            if (leadingIcon != null && !isError) {
                Icon(
                    painter = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            } else if (leadingIcon != null && isError) {
                Icon(
                    painter = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        isError = isError,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            errorContainerColor = MaterialTheme.colorScheme.surface,
            errorIndicatorColor = Color.Red
        ),
        onValueChange = { updateTextField(it) },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                if (hiddenKeyboard) keyboardController?.hide()
                keyboardActions()
            }
        )
    )
}