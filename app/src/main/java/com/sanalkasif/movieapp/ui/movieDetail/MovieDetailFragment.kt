package com.sanalkasif.movieapp.ui.movieDetail

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.sanalkasif.movieapp.R
import com.sanalkasif.movieapp.base.BaseFragment
import com.sanalkasif.movieapp.databinding.FragmentMovieDetailBinding
import com.sanalkasif.movieapp.model.entities.details.MovieDetailsResponse
import com.sanalkasif.movieapp.model.entities.enums.ResponseType
import com.sanalkasif.movieapp.utils.Constants
import com.sanalkasif.movieapp.utils.Functions.getDateFromDateString
import com.sanalkasif.movieapp.utils.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 *Created by OsmanGul
 */
@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {

    private val viewModel: MovieDetailViewModel by hiltNavGraphViewModels(R.id.nav_graph)


    override fun getViewBinding(): FragmentMovieDetailBinding {
        return FragmentMovieDetailBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
        arguments?.let {
            val movieId = MovieDetailFragmentArgs.fromBundle(it).movieId
            viewModel.getMovieDetails(movieId, Constants.API_KEY)
        }


    }

    private fun observers() {
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            when (it.status) {
                ResponseType.SUCCESS -> {
                    hideProgress()
                    initMovieDetails(it.data)
                }
                ResponseType.ERROR -> {
                    hideProgress()
                }
                ResponseType.LOADING -> {
                    showProgress()
                }
                else -> {
                    Timber.d("MovieDetail Pull Error")
                }
            }
        }

    }

    private fun initMovieDetails(data: MovieDetailsResponse?) {
        data?.let {movieData->
            ImageLoader.loadImage(movieData.backdrop_path,binding.movieImage)
            binding.rate.text = movieData.vote_average.toString()
            binding.date.text = movieData.release_date.getDateFromDateString()
            binding.movieName.text = movieData.original_title
            binding.movieDetails.text = movieData.overview
        }

    }
}