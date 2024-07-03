package presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.models.generics.EventSeverity
import data.models.generics.EventType

@Composable
fun FiltersRow(
    filterType: EventType?,
    onFilterTypeClick: () -> Unit,
    onFilterTypeSelect: (EventType?) -> Unit,
    expandedType: Boolean,
    onExpandedTypeChange: (Boolean) -> Unit,
    filterSeverity: EventSeverity?,
    onFilterSeverityClick: () -> Unit,
    onFilterSeveritySelect: (EventSeverity?) -> Unit,
    expandedSeverity: Boolean,
    onExpandedSeverityChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterMenu(
            title = "Type",
            expanded = expandedType,
            onExpandedChange = onExpandedTypeChange,
            options = EventType.values().toList(),
            selectedOption = filterType,
            onOptionSelect = onFilterTypeSelect,
            onClick = onFilterTypeClick
        )
        FilterMenu(
            title = "Severity",
            expanded = expandedSeverity,
            onExpandedChange = onExpandedSeverityChange,
            options = EventSeverity.values().toList(),
            selectedOption = filterSeverity,
            onOptionSelect = onFilterSeveritySelect,
            onClick = onFilterSeverityClick
        )
    }
}
