package com.github.stephenvinouze.advancedrecyclerview.sample.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview.pagination.adapters.RecyclerPaginationAdapter
import com.github.stephenvinouze.advancedrecyclerview.sample.R
import com.github.stephenvinouze.advancedrecyclerview.sample.models.Sample
import com.github.stephenvinouze.advancedrecyclerview.sample.views.SampleItemView

/**
 * Created by stephenvinouze on 14/11/2017.
 */
class SamplePaginationAdapter : RecyclerPaginationAdapter<Sample>() {

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): View =
            SampleItemView(parent.context)

    override fun onBindItemView(view: View, position: Int) {
        when (view) {
            is SampleItemView -> view.bind(items[position], isItemViewToggled(position))
        }
    }

    override fun onCreateLoaderView(parent: ViewGroup, viewType: Int): View =
            LayoutInflater.from(parent.context).inflate(R.layout.view_progress, parent, false)

}