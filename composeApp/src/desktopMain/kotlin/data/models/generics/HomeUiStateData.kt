package data.models.generics

data class HomeUiStateData(
    val unreadLogEvents: List<LogEvent> = emptyList(),
    val readLogEvents: List<LogEvent> = emptyList(),
    val unReadPage: Int = 1,
    val readPage: Int = 1
)
