package data.models.generics

import androidx.compose.runtime.Composable

data class NavigationItemData(
    val title: String,
    val icon:  @Composable () -> Unit,
    val onClick: () -> Unit,
)
