package com.sanalkasif.movieapp.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
/**
 *Created by OsmanGul
 */
abstract class BaseHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBindCreator(t: T, position: Int)


    open fun getContext(): Context? {
        return itemView.context
    }


}