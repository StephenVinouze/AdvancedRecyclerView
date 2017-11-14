package com.github.stephenvinouze.advancedrecyclerview.sample.fragments

import android.os.Bundle
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.Toast
import com.github.stephenvinouze.advancedrecyclerview.core.adapters.RecyclerAdapter
import com.github.stephenvinouze.advancedrecyclerview.gesture.onGesture
import com.github.stephenvinouze.advancedrecyclerview.sample.adapters.SampleSectionAdapter
import com.github.stephenvinouze.advancedrecyclerview.sample.models.Sample
import kotlinx.android.synthetic.main.recycler_layout.*

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
class GestureSectionRecyclerFragment : AbstractRecyclerFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionAdapter = SampleSectionAdapter(context!!).apply {
            items = Sample.mockItems().sortedBy { it.rate }.toMutableList()
            choiceMode = RecyclerAdapter.ChoiceMode.MULTIPLE
            onClick = { _, position ->
                val sample = items[position]
                Toast.makeText(context, "Item clicked : ${sample.id} ($selectedItemViewCount selected)", Toast.LENGTH_SHORT).show()
            }
        }

        recyclerView.adapter = sectionAdapter
        recyclerView.onGesture(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
                onMove = { fromPosition, toPosition ->
                    Toast.makeText(context, "Item moved from position $fromPosition to position $toPosition", Toast.LENGTH_SHORT).show()
                    false
                },
                onSwipe = { position, _ ->
                    Toast.makeText(context, "Item swiped at position $position", Toast.LENGTH_SHORT).show()
                }
        )
    }

}
