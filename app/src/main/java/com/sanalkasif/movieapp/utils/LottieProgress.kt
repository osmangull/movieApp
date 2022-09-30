package com.sanalkasif.movieapp.utils


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.sanalkasif.movieapp.R

class LottieProgress internal constructor(fragment: Fragment) {

    private var context: Context? = null
    var dialog: Dialog? = null
    private var animation: Int = R.raw.progress

    init {
        context = fragment.activity
    }

    @SuppressLint("UseCompatLoadingForDrawables", "InflateParams")
    fun showProgress() {
        if (dialog == null) {
            dialog = Dialog(context!!)
            dialog!!.setCancelable(false)
            val v: View =
                LayoutInflater.from(context).inflate(R.layout.fragment_lottie_dialog, null, false)
            if (animation != R.raw.progress) {
                val animationView: LottieAnimationView = v.findViewById(R.id.animationView)
                animationView.setAnimation(animation)
            }
            dialog!!.setContentView(v)
            dialog!!.window!!.setBackgroundDrawable(context!!.resources.getDrawable(R.color.transparent))
            dialog!!.show()
        } else dialog!!.show()
    }

    fun hideProgress() {
        if (dialog != null) {
            Handler(Looper.getMainLooper()).postDelayed({
                dialog!!.dismiss()
            }, 1000)
        }
    }
}