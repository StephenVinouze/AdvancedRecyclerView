package com.github.stephenvinouze.advancedrecyclerview.sample.fragments

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview.pagination.appendItems
import com.github.stephenvinouze.advancedrecyclerview.pagination.onPaginate
import com.github.stephenvinouze.advancedrecyclerview.sample.R
import com.github.stephenvinouze.advancedrecyclerview.sample.adapters.PaginationSampleAdapter
import com.github.stephenvinouze.advancedrecyclerview.sample.adapters.SampleAdapter
import kotlinx.android.synthetic.main.pagination_recycler_layout.*

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
class PaginationRecyclerFragment : Fragment() {

    private var currentPage = 0

    private val paginationAdapter: PaginationSampleAdapter by lazy {
        PaginationSampleAdapter(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.pagination_recycler_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paginationRecyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = paginationAdapter
            onPaginate(
                    isLoading = {
                        paginationAdapter.isLoading
                    },
                    hasAllItems = {
                        false
                    },
                    onLoad = {
                        populatePage(true)
                    })
        }

        refreshLayout.setOnRefreshListener { populatePage(true) }

        populatePage()
    }

    private fun populatePage(delayed: Boolean = false) {
        paginationAdapter.isLoading = true

        if (delayed) {
            val handler = Handler()
            handler.postDelayed({
                loadPage()
            }, 2000)
        } else {
            loadPage()
        }
    }

    private fun loadPage() {
        paginationAdapter.appendItems(SampleAdapter.buildSamples())
        paginationAdapter.isLoading = false
        refreshLayout.isRefreshing = false
    }

}
