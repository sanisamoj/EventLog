package utils

import java.text.SimpleDateFormat
import java.util.*

fun formatTimestamp(timestamp: String): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return try {
        val date = parser.parse(timestamp)
        if (date != null) formatter.format(date) else timestamp
    } catch (e: Exception) {
        timestamp
    }
}