package presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.models.generics.EventSeverity
import data.models.generics.EventType
import data.models.generics.LogEvent
import org.jetbrains.compose.resources.painterResource
import sanisamoj.eventlogger.library.resources.*
import utils.formatTimestamp

@Composable
fun LogEventCard(
    logEvent: LogEvent,
    onReadPress: () -> Unit = {}
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = when (logEvent.eventType) {
                        EventType.ERROR -> painterResource(Res.drawable.warning_24dp_FILL0_wght400_GRAD0_opsz24)
                        EventType.INFO -> painterResource(Res.drawable.info_24dp_FILL0_wght400_GRAD0_opsz24)
                        EventType.ATTACK -> painterResource(Res.drawable.destruction_24dp_FILL0_wght400_GRAD0_opsz24)
                    },
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = logEvent.applicationName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Type: ${logEvent.eventType.name}")
            Text(text = "Service: ${logEvent.serviceName ?: "N/A"}")
            Text(text = "Message: ${logEvent.message}")
            Text(text = "Description: ${logEvent.description ?: "N/A"}")
            logEvent.errorCode?.let {
                Text(text = "Error Code: $it", color = Color.Red, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                Text(text = "Severity: ${logEvent.severity}")
                Text(text = "Timestamp: ${formatTimestamp(logEvent.timestamp)}")
            }
            logEvent.additionalData?.let { data ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Additional Data:", fontWeight = FontWeight.Bold)
                data.forEach { (key, value) ->
                    Text(text = "$key: $value")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (!logEvent.read) {
                Button(
                    onClick = { onReadPress() },
                ) {
                    Text(text = "Visto")
                }
            }
        }
    }
}