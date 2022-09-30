package com.sanalkasif.movieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sanalkasif.movieapp.model.api.MovieApi
import com.sanalkasif.movieapp.model.entities.enums.ResponseType
import com.sanalkasif.movieapp.model.entities.nowplaying.NowPlayingMoviesResponse
import com.sanalkasif.movieapp.model.entities.upcoming.UpcomingResponse
import com.sanalkasif.movieapp.utils.FinishListener
import com.sanalkasif.movieapp.utils.ResponseManager
import com.sanalkasif.movieapp.utils.extensions.makeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val movieApi: MovieApi): ViewModel()  {
    private var _upComingMovies = MutableLiveData<ResponseManager<UpcomingResponse>>()
    val upComingMovies:LiveData<ResponseManager<UpcomingResponse>> get() = _upComingMovies

    private var _nowPlayingMovies = MutableLiveData<ResponseManager<NowPlayingMoviesResponse>>()
    val nowPlayingMovies:LiveData<ResponseManager<NowPlayingMoviesResponse>> get() = _nowPlayingMovies

    fun getUpcomingMovies(page: Int, api_key: String) {
        val call = movieApi.getUpcomingMovies(page, api_key)
        call.makeApiCall(object : FinishListener<ResponseManager<UpcomingResponse>> {
            override fun onFinish(t: ResponseManager<UpcomingResponse>) {
                _upComingMovies.postValue(t)
            }
        })
    }

    fun getNowPlayingMovies(page:Int, api_key: String){
        val call = movieApi.getNowPlayingMovies(page, api_key)
        call.makeApiCall(object : FinishListener<ResponseManager<NowPlayingMoviesResponse>>{
            override fun onFinish(t: ResponseManager<NowPlayingMoviesResponse>) {
                _nowPlayingMovies.postValue(t)
            }
        })
    }

    fun removeObserving(){
        _upComingMovies.postValue(ResponseManager(ResponseType.DEFAULT,null,null))
        _nowPlayingMovies.postValue(ResponseManager(ResponseType.DEFAULT,null,null))
    }

}