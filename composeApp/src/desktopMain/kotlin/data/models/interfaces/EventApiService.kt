package data.models.interfaces

import data.models.generics.LogEvent
import data.models.generics.Operator
import data.models.requests.CreateOperatorRequest
import data.models.requests.LoginRequest
import data.models.requests.ValidationAccountRequest
import data.models.responses.LoginResponse
import data.models.responses.ResponseWithPagination
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApiService {

    @POST("operator")
    suspend fun createOperator(@Body createOperatorRequest: CreateOperatorRequest): Operator

    @POST("operator/login")
    suspend fun login(@Body login: LoginRequest): LoginResponse

    @POST("operator/generate")
    suspend fun generateValidationCode(@Query("operator") identification: String)

    @POST("operator/Activate")
    suspend fun activateOperator(@Body validation: ValidationAccountRequest)

    @POST("operator/session")
    suspend fun session(@Header("Authorization") token: String): Operator

    @DELETE("operator/session")
    suspend fun signOut(@Header("Authorization") token: String): Operator

    @GET("log")
    suspend fun getEventLogsWithFilter(
        @Header("Authorization") token: String,
        @Query("type") type: String,
        @Query("severity") severity: String,
        @Query("mode") mode: String,
        @Query("read") read: String,
        @Query("page") page: String,
        @Query("size") size: String,
    ) : ResponseWithPagination<LogEvent>

    @GET("log")
    suspend fun getAllEventLogs(
        @Header("Authorization") token: String,
        @Query("read") read: String?,
        @Query("page") page: String,
        @Query("size") size: String,
    ) : ResponseWithPagination<LogEvent>

    @PUT("log/{logId}")
    suspend fun readEventLog(
        @Header("Authorization") token: String,
        @Path("logId") logId: String,
        @Query("read") read: Boolean
    )
}