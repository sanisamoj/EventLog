package data.repository

import context.DefaultAppContainer
import data.models.generics.EventLoggerFilter
import data.models.generics.LogEvent
import data.models.generics.Operator
import data.models.interfaces.EventLoggerRepository
import data.models.requests.CreateOperatorRequest
import data.models.requests.LoginRequest
import data.models.requests.ValidationAccountRequest
import data.models.responses.LoginResponse
import data.models.responses.ResponseWithPagination
import network.EventLoggerApi

class DefaultEventRepository(private val eventLoggerApi: EventLoggerApi): EventLoggerRepository {
    private val _token: String by lazy { DefaultAppContainer.preferences.getPreferences()!!.token }
    private val token by lazy { _token }

    override suspend fun createOperator(createOperatorRequest: CreateOperatorRequest): Operator {
        val operator = eventLoggerApi.retrofitService.createOperator(createOperatorRequest)
        return operator
    }

    override suspend fun login(email: String, password: String): LoginResponse {
        val loginRequest = LoginRequest(email, password)
        val loginResponse = eventLoggerApi.retrofitService.login(loginRequest)
        return loginResponse
    }

    override suspend fun generateValidationCode(identification: String) {
        eventLoggerApi.retrofitService.generateValidationCode(identification)
    }

    override suspend fun activateAccount(email: String, code: Int) {
        val validationCodeRequest = ValidationAccountRequest(email, code)
        eventLoggerApi.retrofitService.activateOperator(validationCodeRequest)
    }

    override suspend fun session(token: String): Operator {
        val operator = eventLoggerApi.retrofitService.session("Bearer $token")
        return operator
    }

    override suspend fun signOut(token: String) {
        eventLoggerApi.retrofitService.signOut("Bearer $token")
    }

    override suspend fun getAllEventLogs(read: Boolean?, page: Int, size: Int): ResponseWithPagination<LogEvent> {
        return eventLoggerApi.retrofitService.getAllEventLogs(
            token = "Bearer $token",
            read = read?.toString(),
            page = page.toString(),
            size = size.toString()
        )
    }

    override suspend fun getAllEventLogsWithFilter(filter: EventLoggerFilter): ResponseWithPagination<LogEvent> {
        return eventLoggerApi.retrofitService.getEventLogsWithFilter(
            token = "Bearer $token",
            type = filter.eventType,
            severity = filter.eventSeverity,
            mode = filter.ordering.toString(),
            read = filter.read.toString(),
            page = filter.page.toString(),
            size = filter.size.toString()
        )
    }

    override suspend fun readEventLog(logId: String, read: Boolean) {
        eventLoggerApi.retrofitService.readEventLog(
            token = "Bearer $token",
            logId = logId,
            read = read
        )
    }
}