package data.models.generics

data class EventLoggerFilter(
    val eventType: String,
    val eventSeverity: String,
    val ordering: Int = -1,
    val read: Boolean = false,
    val page: Int = 1,
    val size: Int = 20
)