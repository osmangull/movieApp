package com.sanalkasif.movieapp.ui.movieDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sanalkasif.movieapp.model.api.MovieApi
import com.sanalkasif.movieapp.model.entities.details.MovieDetailsResponse
import com.sanalkasif.movieapp.model.entities.enums.ResponseType
import com.sanalkasif.movieapp.utils.FinishListener
import com.sanalkasif.movieapp.utils.ResponseManager
import com.sanalkasif.movieapp.utils.extensions.makeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieApi: MovieApi): ViewModel() {

    private val _movieDetails = MutableLiveData<ResponseManager<MovieDetailsResponse>>()
    val movieDetails: LiveData<ResponseManager<MovieDetailsResponse>> get() = _movieDetails


    fun getMovieDetails(
        movieId: Int, api_key: String) {
        _movieDetails.postValue(ResponseManager(ResponseType.LOADING,null,null))
        val call = movieApi.getMovieDetails(movieId, api_key)
        call.makeApiCall(object : FinishListener<ResponseManager<MovieDetailsResponse>> {
            override fun onFinish(t: ResponseManager<MovieDetailsResponse>) {
                _movieDetails.postValue(t)
            }
        })
    }
}