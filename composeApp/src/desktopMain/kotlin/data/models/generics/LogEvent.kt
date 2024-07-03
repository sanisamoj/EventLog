package data.models.generics

data class LogEvent(
    val id: String,
    val number: Int,
    val applicationId: String,
    val applicationName: String,
    val serviceName: String? = null,
    val eventType: EventType,
    val errorCode: String? = null,
    val message: String,
    val description: String? = null,
    val severity: EventSeverity,
    val stackTrace: String? = null,
    val additionalData: Map<String, String>? = null,
    val read: Boolean,
    val timestamp: String
)
