package com.github.stephenvinouze.advancedrecyclerview.sample.fragments

import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.View
import android.widget.Toast
import com.github.stephenvinouze.advancedrecyclerview.core.enums.ChoiceMode
import com.github.stephenvinouze.advancedrecyclerview.gesture.extensions.enableGestures
import com.github.stephenvinouze.advancedrecyclerview.sample.adapters.SampleSectionAdapter
import com.github.stephenvinouze.advancedrecyclerview.sample.models.Sample
import kotlinx.android.synthetic.main.recycler_layout.*

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
class GestureSectionRecyclerFragment : AbstractRecyclerFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionAdapter = SampleSectionAdapter().apply {
            items = Sample.mockItems().sortedBy { it.rate }.toMutableList()
            choiceMode = ChoiceMode.MULTIPLE
            onClick = { _, position ->
                val sample = items[position]
                Toast.makeText(context, "Item clicked : ${sample.id} ($selectedItemViewCount selected)", Toast.LENGTH_SHORT).show()
            }
        }

        recyclerView.adapter = sectionAdapter
        recyclerView.enableGestures(
                dragDirections = ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                swipeDirections = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
                onMove = { fromPosition, toPosition ->
                    Toast.makeText(context, "Item moved from position $fromPosition to position $toPosition", Toast.LENGTH_SHORT).show()
                    true
                },
                onSwipe = { position, _ ->
                    Toast.makeText(context, "Item swiped at position $position", Toast.LENGTH_SHORT).show()
                }
        )
    }

}
