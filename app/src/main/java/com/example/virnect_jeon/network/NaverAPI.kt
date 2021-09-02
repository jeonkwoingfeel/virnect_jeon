package com.example.virnect_jeon.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface NaverAPI {

    @GET("search/book.json")
    fun getSearchBooks(
        @Query("query") query: String,
        @Query("start")start: Int? = null,
        @Query("display") display: Int? = null,
        @Query("sort") sort: String? = "sim",
        @Query("filter") filter: String? = "all",
    ): Call<ResultGetSearchBooks>

    companion object {
        //  https://openapi.naver.com/v1/search/{serviceid}
        private const val BASE_URL_NAVER_API = "https://openapi.naver.com/v1/"
        private const val CLIENT_ID = "A3GrZrqeTCibEmQNSOOF"
        private const val CLIENT_SECRET = "cJmALgZEfB"

        fun create(): NaverAPI {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("X-Naver-Client-Id", CLIENT_ID)
                    .addHeader("X-Naver-Client-Secret", CLIENT_SECRET)
                    .build()
                return@Interceptor it.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL_NAVER_API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NaverAPI::class.java)
        }
    }
}