package com.sanalkasif.movieapp.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.sanalkasif.movieapp.utils.NameValid
import com.sanalkasif.movieapp.utils.FinishListener
import java.util.*
/**
 *Created by OsmanGul
 */
abstract class BaseAdapter<T, VH : BaseHolder<T>>(
    context: Context?,
    list: MutableList<T?>?
) : RecyclerView.Adapter<VH>() {
    private var context: Context? = null
    private var originalList: MutableList<T?>? = null
    private var list: MutableList<T?>? = null
    private var emptyFinishListener: FinishListener<Boolean>? = null
    private var itemClickFinishListener: FinishListener<Any?>? = null
    private val TAG = "BaseRecyclerViewAdapter"

    fun setListener(FinishListener: FinishListener<Any?>) {
        this.itemClickFinishListener = FinishListener
    }

    var recentlyDeletedItem: T? = null
    var recentlyDeletedItemPosition = 0


    init {
        this.context = context
        this.list = list
        originalList = list
    }

    open fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(s: CharSequence): FilterResults {
                var result: MutableList<T?>? = ArrayList()
                if (s.isEmpty()) {
                    result = originalList
                } else {
                    for (t in originalList!!) {
                        if (t is NameValid) {
                            if ((t as NameValid).getNameString()!!.lowercase(Locale.forLanguageTag("tr")).contains(s.toString().lowercase(Locale.forLanguageTag("tr")))) {
                                result!!.add(t)
                            }
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = result
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                try {
                    list = results.values as MutableList<T?>?
                    notifyDataSetChanged()
                } catch (e: Exception) {
                    (getContext() as Activity?)!!.runOnUiThread {
                        list = results.values as MutableList<T?>
                    }
                    notifyDataSetChanged()
                }
                if (emptyFinishListener != null) {
                    emptyFinishListener?.onFinish(getList()!!.isEmpty())
                }
            }
        }
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBindCreator(getItem(position), position)
        holder.itemView.setOnClickListener {
            itemClickFinishListener?.onFinish(null)
        }
    }

    open fun getList(): List<T?>? {
        return list
    }

    open fun getOriginalList(): List<T?>? {
        return originalList
    }

    open fun getItem(i: Int): T {
        return list?.get(i)!!
    }

    open fun addItems(list: List<T>) {
        this.list!!.addAll(list)
        originalList!!.addAll(list)
        notifyItemRangeInserted(this.list!!.size - list.size, list.size)
    }


    open fun deleteItem(position: Int) {
        recentlyDeletedItem = list!![position]
        recentlyDeletedItemPosition = position
        list!!.removeAt(position)
        notifyItemRemoved(position)
    }

    open fun swapPositions(fromPosition: Int, toPosition: Int) {
        Collections.swap(list, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }


    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0
    }


    open fun getContext(): Context? {
        return context
    }

    open fun setEmptyListener(emptyFinishListener: FinishListener<Boolean>): BaseAdapter<T, VH>? {
        this.emptyFinishListener = emptyFinishListener
        return this
    }
}