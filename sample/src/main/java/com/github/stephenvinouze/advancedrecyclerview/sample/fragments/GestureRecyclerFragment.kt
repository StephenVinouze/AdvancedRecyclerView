package com.github.stephenvinouze.advancedrecyclerview.sample.fragments

import android.os.Bundle
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.github.stephenvinouze.advancedrecyclerview.gesture.onGesture
import com.github.stephenvinouze.advancedrecyclerview.sample.adapters.SampleAdapter
import kotlinx.android.synthetic.main.recycler_layout.*

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
class GestureRecyclerFragment : AbstractRecyclerFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SampleAdapter(context!!).apply {
            items = SampleAdapter.buildSamples()
        }

        recyclerView.adapter = adapter
        recyclerView.onGesture(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
    }

}
