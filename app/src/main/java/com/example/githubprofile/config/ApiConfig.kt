package com.example.githubprofile.config

import com.example.githubprofile.BuildConfig
import com.example.githubprofile.interfaces.ApiServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        private const val BASE_URL = "https://api.github.com/"
        fun getApiService(): ApiServices{
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .header("Authorization", "token ${BuildConfig.API_KEY}")
                        .method(original.method, original.body)
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .build()
            val retrofit = Retrofit.Builder().apply {
                this.baseUrl(BASE_URL)
                this.addConverterFactory(GsonConverterFactory.create())
                this.client(client)
            }.build()
            return retrofit.create(ApiServices::class.java)
        }
    }
}