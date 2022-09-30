package com.sanalkasif.movieapp.ui.activities

import com.sanalkasif.movieapp.base.BaseActivity
import com.sanalkasif.movieapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
/**
 *Created by OsmanGul
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}