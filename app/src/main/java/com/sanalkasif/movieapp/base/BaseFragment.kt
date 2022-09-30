package com.sanalkasif.movieapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.sanalkasif.movieapp.utils.LottieProgress
/**
 *Created by OsmanGul
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    lateinit var progress: LottieProgress
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        progress= LottieProgress(this)
        return binding.root
    }

    abstract fun getViewBinding(): VB

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun showProgress(){
        progress.showProgress()
    }

    fun hideProgress(){
        progress.hideProgress()
    }
    fun navigateAction(actionId: NavDirections) {
        findNavController().navigate(actionId)
    }


}