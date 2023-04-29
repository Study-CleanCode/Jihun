package com.kakao.kakaobank_assignment.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kakao.kakaobank_assignment.BuildConfig
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit

object ApiClient {
    private const val BASE_URL = BuildConfig.KAKAO_BASE_URL
    private var retrofit: Retrofit? = null
    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_API_KEY}")
                .build()
            proceed(newRequest)
        }
    }
    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(AppInterceptor())
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class, InternalCoroutinesApi::class)
    fun getApiClient(): Retrofit? {
        kotlinx.coroutines.internal.synchronized(this) {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                    .build()
            }
            return retrofit
        }
    }
}