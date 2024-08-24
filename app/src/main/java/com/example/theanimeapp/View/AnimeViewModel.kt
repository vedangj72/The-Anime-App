package com.example.theanimeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theanimeapp.Model.AnimeResponse
import com.example.theanimeapp.Model.Data
import com.example.theanimeapp.Utils.Resource
import com.example.theanimeapp.repository.AnimeRepository
import kotlinx.coroutines.launch

class AnimeViewModel : ViewModel() {
    private val repository = AnimeRepository()

    private val _animeData = MutableLiveData<Resource<AnimeResponse>>()
    val animeData: LiveData<Resource<AnimeResponse>> get() = _animeData

    private val _animeDetail = MutableLiveData<Resource<Data>>()
    val animeDetail: LiveData<Resource<Data>> get() = _animeDetail

    fun fetchAnimeData(
        page: Int,
        size: Int,
        search: String? = null,
        genres: String? = null,
        sortBy: String,
        sortOrder: String
    ) {
        viewModelScope.launch {
            _animeData.postValue(Resource.Loading())
            val response = repository.getAnimeData(page, size, search, genres, sortBy, sortOrder)
            _animeData.postValue(response)
        }
    }

    fun fetchAnimeById(id: String) {
        viewModelScope.launch {
            _animeDetail.postValue(Resource.Loading())
            val response = repository.getById(id)
            _animeDetail.postValue(response)
        }
    }
}
