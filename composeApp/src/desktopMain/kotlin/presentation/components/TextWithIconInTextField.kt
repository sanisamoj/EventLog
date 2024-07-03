package presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextWithIconInTextField(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.error,
    icon: Painter,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = color
        )
        Spacer(modifier = Modifier.padding(start = 10.dp))
        Text(
            modifier = modifier,
            text = text,
            color = color,
            fontSize = 12.sp,
            lineHeight = 14.sp
        )
    }
}