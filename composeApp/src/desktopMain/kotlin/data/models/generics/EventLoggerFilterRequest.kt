package data.models.generics

data class EventLoggerFilterRequest(
    val type: String = EventType.ERROR.name.lowercase(),
    val severity: String = EventSeverity.HIGH.name.lowercase(),
    val mode: String = "dsc",
    val read: Boolean = false,
    val page: Int = 1,
    val size: Int = 30
)