package com.github.stephenvinouze.advancedrecyclerview.pagination.extensions

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
 * @param threshold The threshold indicating when the pagination is triggered. If 0, will trigger when reaching the bottom of the list. Default is 5. This parameter is optional.
 * @param isLoading Indicate in this block whether a pagination is already ongoing. The application should retain a state and apply it in this block.
 * @param hasAllItems Indicate in this block if all items has been loaded.
 * @param onLoad The pagination has been triggered. You should handle the logic in this block.
 */
fun RecyclerView.enablePagination(threshold: Int = 5,
                                  isLoading: () -> Boolean,
                                  hasAllItems: () -> Boolean,
                                  onLoad: () -> Unit) {
    layoutManager?.let {
        addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalCount = it.itemCount
                val firstVisible = when (it) {
                    is LinearLayoutManager -> it.findFirstVisibleItemPosition()
                    is StaggeredGridLayoutManager -> {
                        if (layoutManager.childCount > 0) it.findFirstVisibleItemPositions(null)[0] else 0
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
}

/**
 * [JAVA VERSION with abstract class as callback instead of lambdas]
 * Enable your list to be paginable. Trigger an event to let the user fetch the next page
 * Note that pagination will be ignore whether you are using sections. Same if you are using a LayoutManager that does not extend LinearLayoutManager.
 * @param threshold The threshold indicating when the pagination is triggered. If 0, will trigger when reaching the bottom of the list. Default is 5. This parameter is optional.
 * @param callback The pagination callback that let you fetch your pages
 */
fun RecyclerView.enablePagination(threshold: Int = 5, callback: PaginationCallback) {
    layoutManager?.let {
        addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalCount = it.itemCount
                val firstVisible = when (it) {
                    is LinearLayoutManager -> it.findFirstVisibleItemPosition()
                    is StaggeredGridLayoutManager -> {
                        if (layoutManager.childCount > 0) it.findFirstVisibleItemPositions(null)[0] else 0
                    }
                    else -> throw IllegalStateException("LayourManager should derived either from LinearLayoutManager or StaggeredGridLayoutManager")
                }

                if (totalCount - childCount <= firstVisible + threshold) {
                    if (!callback.isLoading() && !callback.hasAllItems()) {
                        callback.onLoad()
                    }
                }
            }
        })
    }
}

/**
 * Append your items at the end of your list
 * @param itemsToAdd The items to be added in your list
 */
fun <T> RecyclerAdapter<T>.appendItems(itemsToAdd: List<T>) {
    addItems(itemsToAdd.toMutableList(), items.size)
}

abstract class PaginationCallback {

    abstract fun isLoading(): Boolean
    abstract fun hasAllItems(): Boolean
    abstract fun onLoad()

}