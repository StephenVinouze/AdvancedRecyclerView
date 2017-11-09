package com.github.stephenvinouze.advancedrecyclerview.sample.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview.pagination.handlePagination
import com.github.stephenvinouze.advancedrecyclerview.pagination.setItems
import com.github.stephenvinouze.advancedrecyclerview.sample.R
import com.github.stephenvinouze.advancedrecyclerview.sample.adapters.SampleAdapter
import kotlinx.android.synthetic.main.pagination_recycler_layout.*

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
class PaginationRecyclerFragment : Fragment() {

    private val adapter: SampleAdapter by lazy {
        SampleAdapter(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.pagination_recycler_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paginationRecyclerView.setHasFixedSize(true)
        paginationRecyclerView.layoutManager = LinearLayoutManager(context)
        paginationRecyclerView.itemAnimator = DefaultItemAnimator()

        refreshLayout.setOnRefreshListener { populatePage(1) }

        populatePage(1)

        paginationRecyclerView.adapter = adapter

        paginationRecyclerView.handlePagination({ page ->
            populatePage(page)
        })
    }

    private fun populatePage(page: Int) {
        adapter.setItems(SampleAdapter.buildSamples(), page)

        refreshLayout.isRefreshing = false
    }

}
