package com.github.stephenvinouze.advancedrecyclerview.pagination

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.github.stephenvinouze.advancedrecyclerview.core.adapters.RecyclerAdapter

/**
 * Created by stephenvinouze on 26/04/16.
 */


/**
 * Enable your list to be paginable. Trigger an event to let the user fetch the next page
 * Note that pagination will be ignore whether you are using sections. Same if you are using a LayoutManager that does not extend LinearLayoutManager.
 * @param onLoad The pagination onLoad that let you fetch your pages
 */
fun RecyclerView.onPaginate(threshold: Int = 5,
                            isLoading: () -> Boolean,
                            hasAllItems: () -> Boolean,
                            onLoad: () -> Unit) {
    val layoutManager = layoutManager

    addOnScrollListener(object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val totalCount = layoutManager.itemCount
            val firstVisible = when (layoutManager) {
                is LinearLayoutManager -> layoutManager.findFirstVisibleItemPosition()
                is StaggeredGridLayoutManager -> {
                    if (layoutManager.childCount > 0) layoutManager.findFirstVisibleItemPositions(null)[0] else 0
                }
                else -> throw IllegalStateException("LayourManager should derived either from LinearLayoutManager or StaggeredGridLayoutManager")
            }

            if (totalCount - childCount <= firstVisible + threshold) {
                if (!isLoading() && !hasAllItems()) {
                    onLoad()
                }
            }
        }
    })
}

/**
 * Append your items at the end of your list
 * @param itemsToAdd The items to be added in your list
 */
fun <T> RecyclerAdapter<T>.appendItems(itemsToAdd: List<T>) {
    addItems(itemsToAdd.toMutableList(), items.size)
}