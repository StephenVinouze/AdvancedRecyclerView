package com.github.stephenvinouze.advancedrecyclerview.sample.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview.sample.R

/**
 * Created by stephenvinouze on 10/11/2017.
 */
class PaginationSampleAdapter(context: Context) : SampleAdapter(context) {

    companion object {
        private const val LOADING_VIEW_TYPE = 111
    }

    var isLoading = false
        set(value) {
            if (field != value) {
                field = value
                notifyItemRemoved(items.size)
            }
        }

    override fun getItemCount(): Int =
            if (isLoading) super.getItemCount() + 1 else super.getItemCount()

    override fun getItemViewType(position: Int): Int =
            if (isLoading && position == itemCount - 1) LOADING_VIEW_TYPE else super.getItemViewType(position)

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): View {
        return if (viewType == LOADING_VIEW_TYPE)
            LayoutInflater.from(context).inflate(R.layout.view_progress, parent, false)
        else
            super.onCreateItemView(parent, viewType)
    }

    override fun onBindItemView(v: View, position: Int) {
        if (getItemViewType(position) != LOADING_VIEW_TYPE) {
            super.onBindItemView(v, position)
        }
    }

}