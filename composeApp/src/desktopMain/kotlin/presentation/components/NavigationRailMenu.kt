package presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import data.models.generics.NavigationItemData
import org.jetbrains.compose.resources.painterResource
import sanisamoj.eventlogger.library.resources.Res
import sanisamoj.eventlogger.library.resources.dark_mode_24dp_FILL0_wght400_GRAD0_opsz24
import sanisamoj.eventlogger.library.resources.logout_24dp_FILL0_wght400_GRAD0_opsz24

@Composable
fun NavigationRailMenu(items: List<NavigationItemData>) {
    var selectedItem by remember { mutableStateOf(0) }

    NavigationRail {
        items.forEachIndexed { index, item ->
            NavigationRailItem(
                icon = { item.icon() },
                label = { Text(item.title) },
                selected = selectedItem == index,
                onClick = {
                    item.onClick()
                    selectedItem = index
                }
            )
        }
    }
}