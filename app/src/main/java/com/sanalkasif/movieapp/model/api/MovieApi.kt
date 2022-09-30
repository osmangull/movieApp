package com.sanalkasif.movieapp.model.api
import com.sanalkasif.movieapp.model.entities.details.MovieDetailsResponse
import com.sanalkasif.movieapp.model.entities.nowplaying.NowPlayingMoviesResponse
import com.sanalkasif.movieapp.model.entities.upcoming.UpcomingResponse
import com.sanalkasif.movieapp.utils.Routes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
/**
 *Created by OsmanGul
 */
interface MovieApi {
    @GET(Routes.Movie.getUpcomingMovies)
    fun getUpcomingMovies(@Query("page") page: Int, @Query("api_key") api_key: String): Call<UpcomingResponse>

    @GET(Routes.Movie.getNowPlayingMovies)
    fun getNowPlayingMovies(@Query("page") page: Int, @Query("api_key") api_key: String): Call<NowPlayingMoviesResponse>

    @GET(Routes.Movie.getMovieDetails)
    fun getMovieDetails(@Path("movie_id") movie_id: Int, @Query("api_key") api_key: String): Call<MovieDetailsResponse>

}