package com.github.stephenvinouze.advancedrecyclerview.pagination.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview.core.adapters.RecyclerAdapter
import com.github.stephenvinouze.advancedrecyclerview.core.views.BaseViewHolder

/**
 * Created by stephenvinouze on 10/11/2017.
 */
abstract class RecyclerPaginationAdapter<MODEL>(context: Context) : RecyclerAdapter<MODEL>(context) {

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
            if (isLoaderAt(position)) LOADING_VIEW_TYPE else super.getItemViewType(position)

    override fun getItemId(position: Int): Long =
            if (isLoaderAt(position)) Long.MAX_VALUE - LOADING_VIEW_TYPE else super.getItemId(position)

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder = when (viewType) {
        LOADING_VIEW_TYPE -> BaseViewHolder(onCreateLoaderView(parent, viewType))
        else -> super.onCreateViewHolder(parent, viewType)
    }

    final override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (getItemViewType(position) != LOADING_VIEW_TYPE) {
            super.onBindViewHolder(holder, position)
        }
    }

    private fun isLoaderAt(position: Int): Boolean =
            isLoading && position == itemCount - 1

    protected abstract fun onCreateLoaderView(parent: ViewGroup, viewType: Int) : View

}