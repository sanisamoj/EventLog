package presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import sanisamoj.eventlogger.library.resources.*

@Composable
fun SettingsScreen(
    isDarkMode: Boolean,
    updateDarkMode: () -> Unit = {},
    pageSize: Int,
    updatePageSize: (Int) -> Unit = {},
    deletesUserPreferences: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var pageSizeOptionExpanded by remember { mutableStateOf(false) }
        var pageSizeObservable by remember { mutableStateOf(pageSize) }

        Text(
            text = "Settings",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ListItem(
            modifier = Modifier.clickable { updateDarkMode() },
            headlineContent = { Text("Theme") },
            supportingContent = { Text("Change the theme to light or dark") },
            leadingContent = {
                Icon(
                    painterResource(
                        resource = if (isDarkMode) {
                            Res.drawable.wb_sunny_24dp_FILL0_wght400_GRAD0_opsz24
                        } else {
                            Res.drawable.dark_mode_24dp_FILL0_wght400_GRAD0_opsz24
                        }
                    ),
                    contentDescription = stringResource(Res.string.theme)
                )
            },
            trailingContent = {
                Text(
                    text = if (isDarkMode) stringResource(Res.string.dark) else stringResource(Res.string.light),
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                )
            }
        )

        HorizontalDivider()

        ListItem(
            modifier = Modifier.clickable {
                pageSizeOptionExpanded = !pageSizeOptionExpanded
            },
            headlineContent = { Text("Page Size") },
            supportingContent = { Text("Number of items per page. It needs to be restarted to take effect.") },
            leadingContent = {
                Icon(
                    painter = painterResource(Res.drawable.dataset_24dp_FILL0_wght400_GRAD0_opsz24),
                    contentDescription = "Number of items per page"
                )
            },
            trailingContent = {
                DropdownMenu(
                    expanded = pageSizeOptionExpanded, // manage expanded state appropriately
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                    onDismissRequest = { pageSizeOptionExpanded = !pageSizeOptionExpanded }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            pageSizeObservable = 30
                            updatePageSize(pageSizeObservable)
                            pageSizeOptionExpanded = !pageSizeOptionExpanded
                        }
                    ) {
                        Text(
                            text = "30",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp
                        )
                    }
                    DropdownMenuItem(
                        onClick = {
                            pageSizeObservable = 60
                            updatePageSize(pageSizeObservable)
                            pageSizeOptionExpanded = !pageSizeOptionExpanded
                        }
                    ) {
                        Text(
                            text = "60",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp
                        )
                    }
                    DropdownMenuItem(
                        onClick = {
                            pageSizeObservable = 90
                            updatePageSize(pageSizeObservable)
                            pageSizeOptionExpanded = !pageSizeOptionExpanded
                        }
                    ) {
                        Text(
                            text = "90",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp
                        )
                    }
                    DropdownMenuItem(
                        onClick = {
                            pageSizeObservable = 120
                            updatePageSize(pageSizeObservable)
                            pageSizeOptionExpanded = !pageSizeOptionExpanded
                        }
                    ) {
                        Text(
                            text = "120",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp
                        )
                    }
                }
                Text(
                    fontSize = 16.sp,
                    text = pageSizeObservable.toString(),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        )

        HorizontalDivider()

        ListItem(
            modifier = Modifier.clickable {
                deletesUserPreferences()
            },
            headlineContent = { Text("Delete user preferences.") },
            supportingContent = { Text("Deletes user preferences") },
            leadingContent = {
                Icon(imageVector = Icons.Filled.Clear, contentDescription = "Deletes user preferences")
            }
        )

        HorizontalDivider()
    }
}