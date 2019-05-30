package com.github.stephenvinouze.advancedrecyclerview.sample.fragments

import android.os.Bundle
import android.view.View
import com.github.stephenvinouze.advancedrecyclerview.core.enums.ChoiceMode
import com.github.stephenvinouze.advancedrecyclerview.sample.adapters.SampleSectionAdapter
import com.github.stephenvinouze.advancedrecyclerview.sample.models.Sample
import kotlinx.android.synthetic.main.recycler_layout.*

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
class SectionRecyclerFragment : AbstractRecyclerFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionAdapter = SampleSectionAdapter().apply {
            choiceMode = ChoiceMode.NONE
            items = Sample.mockItems().toMutableList()
        }

        recyclerView.adapter = sectionAdapter
    }
}
