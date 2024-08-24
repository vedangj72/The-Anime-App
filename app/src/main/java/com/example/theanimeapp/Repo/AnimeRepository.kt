package com.example.theanimeapp.repository

import com.example.theanimeapp.Model.AnimeResponse
import com.example.theanimeapp.Model.Data
import com.example.theanimeapp.Network.AnimeInstance
import com.example.theanimeapp.Utils.Resource

class AnimeRepository {

    private val api = AnimeInstance.api

    suspend fun getAnimeData(
        page: Int,
        size: Int,
        search: String? = null,
        genres: String? = null,
        sortBy: String,
        sortOrder: String
    ): Resource<AnimeResponse> {
        return try {
            val response = api.getAnimeData(page, size, search, genres, sortBy, sortOrder)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }

    suspend fun getById(id: String): Resource<Data> {
        return try {
            val response = api.getAnimeById(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("No data found")
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}
