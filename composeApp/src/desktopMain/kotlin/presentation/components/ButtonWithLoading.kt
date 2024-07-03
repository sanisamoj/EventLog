package presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonWithLoading(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    onClick: () -> Unit = {},
    loading: Boolean = false
) {
    Button(
        enabled = enabled,
        onClick = { onClick() },
        modifier = modifier
            .padding(top = 8.dp, bottom = 22.dp)
            .width(140.dp),
        shape = RoundedCornerShape(
            topStart = 10.dp,
            bottomStart = 10.dp,
            topEnd = 10.dp,
            bottomEnd = 10.dp
        )
    ) {
        if (!loading) {
            Text(text = text)
        } else {
            CircularProgressIndicator(
                trackColor = MaterialTheme.colorScheme.primaryContainer,
                strokeWidth = 2.dp,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}