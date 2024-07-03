package presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun <T> FilterMenu(
    title: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    options: List<T>,
    selectedOption: T?,
    onOptionSelect: (T?) -> Unit,
    onClick: () -> Unit
) {
    Box {
        TextButton(
            onClick = onClick,
            colors = ButtonColors(
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.onSecondary,
                disabledContentColor =  MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text(text = "$title: ${selectedOption?.toString() ?: "All"}")
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            DropdownMenuItem(onClick = { onOptionSelect(null); onExpandedChange(false) }) {
                Text(text = "All")
            }
            options.forEach { option ->
                DropdownMenuItem(onClick = { onOptionSelect(option); onExpandedChange(false) }) {
                    Text(text = option.toString())
                }
            }
        }
    }
}