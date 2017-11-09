package com.github.stephenvinouze.advancedrecyclerview.sample.fragments

import android.os.Bundle
import android.view.View
import com.github.stephenvinouze.advancedrecyclerview.core.adapters.RecyclerAdapter
import com.github.stephenvinouze.advancedrecyclerview.sample.adapters.SampleAdapter
import com.github.stephenvinouze.advancedrecyclerview.sample.adapters.SampleSectionAdapter
import kotlinx.android.synthetic.main.recycler_layout.*

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
class SectionRecyclerFragment : AbstractRecyclerFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionAdapter = SampleSectionAdapter(context!!)
        sectionAdapter.choiceMode = RecyclerAdapter.ChoiceMode.NONE
        sectionAdapter.items = SampleAdapter.buildSamples()

        recyclerView.adapter = sectionAdapter
    }

}
