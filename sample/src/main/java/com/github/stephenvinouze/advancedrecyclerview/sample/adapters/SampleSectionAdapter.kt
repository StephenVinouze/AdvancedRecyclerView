package com.github.stephenvinouze.advancedrecyclerview.sample.adapters

import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview.sample.models.Sample
import com.github.stephenvinouze.advancedrecyclerview.sample.views.SampleItemView
import com.github.stephenvinouze.advancedrecyclerview.sample.views.SampleSectionItemView
import com.github.stephenvinouze.advancedrecyclerview.section.adapters.RecyclerSectionAdapter

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
class SampleSectionAdapter : RecyclerSectionAdapter<Int, Sample>({ it.rate }) {

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): View = SampleItemView(parent.context)

    override fun onBindItemView(view: View, position: Int) {
        when (view) {
            is SampleItemView -> view.bind(items[position], isItemViewToggled(position))
        }
    }

    override fun onCreateSectionItemView(parent: ViewGroup, viewType: Int): View =
            SampleSectionItemView(parent.context)

    override fun onBindSectionItemView(sectionView: View, sectionPosition: Int) {
        sectionAt(sectionPosition)?.let {
            when (sectionView) {
                is SampleSectionItemView -> sectionView.bind(it)
            }
        }
    }
}
