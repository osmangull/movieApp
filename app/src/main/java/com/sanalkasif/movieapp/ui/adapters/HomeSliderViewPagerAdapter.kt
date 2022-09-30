package com.sanalkasif.movieapp.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sanalkasif.movieapp.ui.home.NowPlayingMoviesFragment
import com.sanalkasif.movieapp.model.entities.common.Result
import com.sanalkasif.movieapp.utils.Constants
/**
 *Created by OsmanGul
 */

class HomeSliderViewPagerAdapter(fm: FragmentManager, private val results: List<Result?>? ) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return results?.size ?: 0
    }
    override fun getItem(position: Int): Fragment {
        val nowPlayingMoviesFragment = NowPlayingMoviesFragment()
        val bundle = Bundle()
        if (results != null) {
            if (results.isNotEmpty() && results[position] != null) {
                bundle.putParcelable(Constants.API_KEY, results[position])
            }
        }
        nowPlayingMoviesFragment.arguments = bundle
        return nowPlayingMoviesFragment
    }


}