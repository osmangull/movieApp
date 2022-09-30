package com.sanalkasif.movieapp.ui.home

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.sanalkasif.movieapp.R
import com.sanalkasif.movieapp.base.BaseFragment
import com.sanalkasif.movieapp.databinding.FragmentHomeBinding
import com.sanalkasif.movieapp.utils.Constants
import com.sanalkasif.movieapp.ui.adapters.HomeSliderViewPagerAdapter
import com.sanalkasif.movieapp.ui.adapters.UpcomingMoviesAdapter
import com.sanalkasif.movieapp.model.entities.common.Result
import com.sanalkasif.movieapp.model.entities.enums.ResponseType

import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
/**
 *Created by OsmanGul
 */
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private var isUpdate: Boolean = false
    private val viewModel: HomeFragmentViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private var nowPlayingPageCounter: Int? = 1
    private var upcomingPageCounter: Int? = 1
    private var position: Int = 0

    private var isLoading: Boolean = true
    private var isLastPage: Boolean = false
    var adapter: UpcomingMoviesAdapter? = null

    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNowPlayingMovies(nowPlayingPageCounter ?: 1, Constants.API_KEY)
        isUpdate = false
        viewModel.getUpcomingMovies(upcomingPageCounter ?: 1, Constants.API_KEY)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            isUpdate = false
            viewModel.getNowPlayingMovies(nowPlayingPageCounter ?: 1, Constants.API_KEY)
            viewModel.getUpcomingMovies(upcomingPageCounter ?: 1, Constants.API_KEY)
        }
        observers()
    }
    private fun observers() {

        viewModel.nowPlayingMovies.observe(viewLifecycleOwner) {
            when (it.status) {
                ResponseType.SUCCESS -> {
                    hideProgress()
                    initNowPlayingMovies(it.data?.results ?: arrayListOf())
                    viewModel.removeObserving()
                }
                ResponseType.ERROR -> {
                    hideProgress()
                }
                ResponseType.LOADING -> {
                    showProgress()
                }
                else -> {
                    Timber.d("Now Playing Pull Error")
                }
            }
        }


        viewModel.upComingMovies.observe(viewLifecycleOwner) {
            when (it.status) {
                ResponseType.SUCCESS -> {
                    isLoading = false
                    hideProgress()
                    if (!isUpdate)
                        initUpComingEvents(it.data?.results ?: arrayListOf())
                    else
                        updateUpComingEvents(it.data?.results ?: arrayListOf())

                }
                ResponseType.ERROR -> {
                    isLoading = false
                    hideProgress()
                }
                ResponseType.LOADING -> {
                    isLoading = true
                    showProgress()
                }
                else -> {
                    Timber.d("Now Playing Pull Error")
                }
            }

        }
    }

    private fun initUpComingEvents(list: List<Result>) {

        adapter = UpcomingMoviesAdapter(context, list.toMutableList())
        adapter?.listener?.observe(viewLifecycleOwner) {
            navigateAction(HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(it.id))
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.isNestedScrollingEnabled = false




        binding.scrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->

            val nestedScrollView = checkNotNull(v) {
                return@setOnScrollChangeListener
            }

            val lastChild = nestedScrollView.getChildAt(nestedScrollView.childCount - 1)

            if (lastChild != null) {

                if ((scrollY >= (lastChild.measuredHeight - nestedScrollView.measuredHeight)) && scrollY > oldScrollY && !isLoading && !isLastPage) {
                    upcomingPageCounter = upcomingPageCounter?.plus(1)
                    isUpdate = true
                    viewModel.getUpcomingMovies(upcomingPageCounter ?: -1, Constants.API_KEY)
                }
            }
        }
    }

    private fun updateUpComingEvents(list: List<Result>) {
        adapter?.addItems(list)
    }

    private fun pageChangeListener(mPager: ViewPager, size: Int) {
        mPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(pos: Int) {
                position = pos
                createDots(position, size)
            }
        })
    }

    private fun initNowPlayingMovies(list: List<Result>) {
        val adapter = HomeSliderViewPagerAdapter(childFragmentManager, list)
        binding.viewPager.adapter = adapter
        createDots(position, list.size)
        pageChangeListener(binding.viewPager, list.size)
    }

    private fun createDots(current_position: Int, size: Int) {
        binding.dotsLayout.removeAllViews()
        val dots = arrayOfNulls<ImageView>(size)
        for (i in 0 until size) {
            dots[i] = ImageView(context)
            if (i == current_position)
                dots[i]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.active_dots
                    )
                )
            else
                dots[i]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.default_dots
                    )
                )
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.height = 15
            params.width = 15
            params.setMargins(4, 0, 4, 0)

            binding.dotsLayout.addView(dots[i], params)
        }
    }

}