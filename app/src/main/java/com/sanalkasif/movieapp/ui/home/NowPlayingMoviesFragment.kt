package com.sanalkasif.movieapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.sanalkasif.movieapp.base.BaseFragment
import com.sanalkasif.movieapp.databinding.FragmentNowPlayingMoviesBinding
import com.sanalkasif.movieapp.model.entities.common.Result
import com.sanalkasif.movieapp.utils.Constants
import com.sanalkasif.movieapp.utils.Functions.getYearFromDateString
import com.sanalkasif.movieapp.utils.ImageLoader

/**
 *Created by OsmanGul
 */

class NowPlayingMoviesFragment : BaseFragment<FragmentNowPlayingMoviesBinding>() {

    var nowPlayingResult: Result? = null

    override fun getViewBinding(): FragmentNowPlayingMoviesBinding {
        return FragmentNowPlayingMoviesBinding.inflate(layoutInflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNowPlayingMovies()
    }

    @SuppressLint("SetTextI18n")
    private fun initNowPlayingMovies(){
        arguments?.let {
            nowPlayingResult  = requireArguments().getParcelable(Constants.API_KEY)
            ImageLoader.loadImage(nowPlayingResult?.backdrop_path,binding.image)
            binding.infoTitle.text = nowPlayingResult?.original_title + " (" + nowPlayingResult?.release_date?.getYearFromDateString()+ ")"
            binding.info.text = nowPlayingResult?.overview
        }
    }


}