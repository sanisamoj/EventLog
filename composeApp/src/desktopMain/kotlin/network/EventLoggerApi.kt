package network

import data.models.interfaces.EventApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object EventLoggerApi {
    private const val BASE_URL = "http://127.0.0.1:9096/" // "http://192.168.0.7:9096"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS) // Tempo de timeout de conexão
        .readTimeout(10, TimeUnit.SECONDS)    // Tempo de timeout de leitura
        .writeTimeout(10, TimeUnit.SECONDS)   // Tempo de timeout de escrita
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    val retrofitService: EventApiService by lazy {
        retrofit.create(EventApiService::class.java)
    }
}