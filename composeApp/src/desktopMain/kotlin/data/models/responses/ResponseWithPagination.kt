package data.models.responses

data class ResponseWithPagination<T>(
    val content: List<T>,
    val paginationResponse: PaginationResponse
)