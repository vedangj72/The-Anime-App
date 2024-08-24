package com.example.theanimeapp.Network

import com.example.theanimeapp.Utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AnimeInstance {

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-rapidapi-host", Constants.API_HOST)
                .addHeader("x-rapidapi-key", Constants.API_KEY)
                .build()
            chain.proceed(request)
        }
        .build()

    val api: AnimeApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AnimeApi::class.java)
    }
}
