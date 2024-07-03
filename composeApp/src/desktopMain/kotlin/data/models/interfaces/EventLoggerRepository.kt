package data.models.interfaces

import data.models.generics.EventLoggerFilter
import data.models.generics.LogEvent
import data.models.generics.Operator
import data.models.requests.CreateOperatorRequest
import data.models.responses.LoginResponse
import data.models.responses.ResponseWithPagination

interface EventLoggerRepository {
    suspend fun createOperator(createOperatorRequest: CreateOperatorRequest): Operator
    suspend fun login(email: String, password: String): LoginResponse
    suspend fun generateValidationCode(identification: String)
    suspend fun activateAccount(email: String, code: Int)
    suspend fun session(token: String): Operator
    suspend fun signOut(token: String)
    suspend fun getAllEventLogs(read: Boolean?, page: Int, size: Int): ResponseWithPagination<LogEvent>
    suspend fun getAllEventLogsWithFilter(filter: EventLoggerFilter): ResponseWithPagination<LogEvent>
    suspend fun readEventLog(logId: String, read: Boolean)
}