package com.github.stephenvinouze.advancedrecyclerview.extensions

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.github.stephenvinouze.advancedrecyclerview.adapters.RecyclerAdapter
import com.github.stephenvinouze.advancedrecyclerview.adapters.RecyclerSectionAdapter
import com.github.stephenvinouze.advancedrecyclerview.callbacks.PaginationCallback

/**
 * Created by stephenvinouze on 26/04/16.
 */

val RecyclerView.canPaginate: Boolean
    get() {
        val sectionAdapter = adapter as? RecyclerSectionAdapter<*, *>
        if (sectionAdapter != null) {
            return sectionAdapter.numberOfSections() > 0
        }
        return true
    }

private var isLoading: Boolean = false
private var currentPage: Int = 1

/**
 * Enable your list to be paginable. Trigger an event to let the user fetch the next page
 * Note that pagination will be ignore whether you are using sections. Same if you are using a LayoutManager that does not extend LinearLayoutManager.
 * @param callback The pagination callback that let you fetch your pages
 */
fun RecyclerView.handlePagination(callback: PaginationCallback) {
    if (canPaginate) {
        val linearLayoutManager: LinearLayoutManager? = layoutManager as? LinearLayoutManager
        if (linearLayoutManager != null) {
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (!isLoading && linearLayoutManager.findLastVisibleItemPosition() > paginationTrigger(linearLayoutManager.itemCount)) {
                        callback.fetchNextPage(++currentPage)
                    }
                }
            })
        }
    }
}

/**
 * Display your items inside your configured adapter and let it fill it depending on the pagination configuration
 * @param items The items to be displayed in your list
 * @param page The page to display
 */
fun <T> RecyclerAdapter<T>.setItems(items: MutableList<T>, page: Int) {
    currentPage = page

    if (page == 1) {
        this.items = items
    }
    else {
        addItems(items, itemCount)
    }

    isLoading = false
}

/**
 * Allow smart pagination to give a smooth user experience while paginating by triggering the pagination given the total amount of items in the list
 * @param totalItemCount Total amount of items in the list
 * @return The computed pagination trigger
 */
private fun paginationTrigger(totalItemCount: Int): Int {
    var offset = 0.6f
    if (totalItemCount > 50 && totalItemCount <= 100) {
        offset = 0.7f
    } else if (totalItemCount > 100 && totalItemCount <= 150) {
        offset = 0.8f
    } else if (totalItemCount > 150) {
        offset = 0.9f
    }
    return Math.floor((offset * totalItemCount).toDouble()).toInt()
}
