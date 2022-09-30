package com.sanalkasif.movieapp.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
/**
 *Created by OsmanGul
 */

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewBinding()
        setContentView(binding.root)
    }

    fun navigateToActivity(activity:AppCompatActivity){
        startActivity(Intent(this,activity::class.java))
    }

    abstract fun getViewBinding(): VB
}