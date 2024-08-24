package com.example.theanimeapp.Network

import com.example.theanimeapp.Model.AnimeResponse
import com.example.theanimeapp.Model.Data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {
    @GET("anime")
    suspend fun getAnimeData(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("search") search: String? = null,
        @Query("genres") genres: String? = null,
        @Query("sortBy") sortBy: String,
        @Query("sortOrder") sortOrder: String
    ): Response<AnimeResponse>

    @GET("anime/by-id/{id}")
    suspend fun getAnimeById(
        @Path("id") id: String
    ): Response<Data>
}
