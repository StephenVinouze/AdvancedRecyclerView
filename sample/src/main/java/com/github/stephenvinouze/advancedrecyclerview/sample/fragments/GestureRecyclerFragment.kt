package com.github.stephenvinouze.advancedrecyclerview.sample.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import com.github.stephenvinouze.advancedrecyclerview.gesture.extensions.enableGestures
import com.github.stephenvinouze.advancedrecyclerview.sample.adapters.SampleAdapter
import com.github.stephenvinouze.advancedrecyclerview.sample.models.Sample
import kotlinx.android.synthetic.main.recycler_layout.*

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
class GestureRecyclerFragment : AbstractRecyclerFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SampleAdapter().apply {
            items = Sample.mockItems().toMutableList()
        }

        recyclerView.adapter = adapter
        recyclerView.enableGestures(
                dragDirections = ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                swipeDirections = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        )
    }
}
