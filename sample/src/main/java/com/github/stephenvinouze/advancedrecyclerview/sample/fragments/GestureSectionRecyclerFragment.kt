package com.github.stephenvinouze.advancedrecyclerview.sample.fragments

import android.os.Bundle
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.Toast
import com.github.stephenvinouze.advancedrecyclerview.core.adapters.RecyclerAdapter
import com.github.stephenvinouze.advancedrecyclerview.core.callbacks.ClickCallback
import com.github.stephenvinouze.advancedrecyclerview.gesture.handleGesture
import com.github.stephenvinouze.advancedrecyclerview.sample.adapters.SampleAdapter
import com.github.stephenvinouze.advancedrecyclerview.sample.adapters.SampleSectionAdapter
import kotlinx.android.synthetic.main.recycler_layout.*
import java.util.*

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
class GestureSectionRecyclerFragment : AbstractRecyclerFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionAdapter = SampleSectionAdapter(context!!)
        sectionAdapter.choiceMode = RecyclerAdapter.ChoiceMode.MULTIPLE
        sectionAdapter.clickCallback = object : ClickCallback() {
            override fun onItemClick(view: View, position: Int) {
                val sample = sectionAdapter.items[position]
                Toast.makeText(context, "Item clicked : ${sample.id} (${sectionAdapter.selectedItemViewCount} selected)", Toast.LENGTH_SHORT).show()
            }
        }

        recyclerView.handleGesture(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
                onMove = { fromPosition, toPosition ->
                    Toast.makeText(context, "Item moved from position $fromPosition to position $toPosition", Toast.LENGTH_SHORT).show()
                    false
                },
                onSwipe = { position, _ ->
                    Toast.makeText(context, "Item swiped at position $position", Toast.LENGTH_SHORT).show()
                }
        )

        val samples = SampleAdapter.buildSamples()
        Collections.sort(samples) { lhs, rhs -> lhs.rate - rhs.rate }

        sectionAdapter.items = samples

        recyclerView.adapter = sectionAdapter
    }

}
