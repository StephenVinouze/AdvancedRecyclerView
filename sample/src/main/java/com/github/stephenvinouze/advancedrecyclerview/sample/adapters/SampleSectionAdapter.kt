package com.github.stephenvinouze.advancedrecyclerview.sample.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup

import com.github.stephenvinouze.advancedrecyclerview.sample.models.Sample
import com.github.stephenvinouze.advancedrecyclerview.sample.views.SampleItemView
import com.github.stephenvinouze.advancedrecyclerview.sample.views.SampleSectionItemView
import com.github.stephenvinouze.advancedrecyclerview.section.adapters.RecyclerSectionAdapter

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
class SampleSectionAdapter(context: Context) : RecyclerSectionAdapter<Int, Sample>(context, { it.rate }) {

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): View = SampleItemView(context)

    override fun onBindItemView(v: View, position: Int) {
        when (v) {
            is SampleItemView -> v.bind(items[position], isItemViewToggled(position))
        }
    }

    override fun onCreateSectionItemView(parent: ViewGroup, viewType: Int): View =
            SampleSectionItemView(context)

    override fun onBindSectionItemView(sectionView: View, sectionPosition: Int) {
        sectionAt(sectionPosition)?.let {
            when (sectionView) {
                is SampleSectionItemView -> sectionView.bind(it)
            }
        }
    }

}
