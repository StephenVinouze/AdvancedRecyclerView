package com.github.stephenvinouze.advancedrecyclerview.gesture

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.github.stephenvinouze.advancedrecyclerview.core.adapters.RecyclerAdapter
import com.github.stephenvinouze.advancedrecyclerview.section.adapters.RecyclerSectionAdapter

/**
 * Created by stephenvinouze on 26/04/16.
 */

/**
 * Configure your gestures if any for both move and swipe gestures.
 * You can also implement the OnRecyclerTouchCallback to be notified of the move/swipe events as well as enabling/disabling these gestures for some specific items
 * @param dragDirections The move directions. Can be either ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT, ItemTouchHelper.UP or ItemTouchHelper.DOWN
 * @param swipeDirections The swipe directions. Can be either ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT, ItemTouchHelper.UP or ItemTouchHelper.DOWN
 * @param onMove The gesture callback when moving item from two positions
 * @param onSwipe The gesture callback when swiping item at given position and direction
 * @param canMoveAt The gesture callback that enables move at given position (default true)
 * @param canSwipeAt The gesture callback that enables swipe at given position (default true)
 */
fun RecyclerView.onGesture(dragDirections: Int,
                           swipeDirections: Int,
                           onMove: ((fromPosition: Int, toPosition: Int) -> Boolean)? = null,
                           onSwipe: ((position: Int, direction: Int) -> Unit)? = null,
                           canMoveAt: (position: Int) -> Boolean = { true },
                           canSwipeAt: (position: Int) -> Boolean = { true }) {
    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(dragDirections, swipeDirections) {

        override fun getDragDirs(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
            val position = viewHolder!!.layoutPosition
            val adapter = adapter as? RecyclerSectionAdapter<*, *>
            var isMovable = canMoveAt(position)
            if (adapter != null) {
                isMovable = !adapter.isSectionAt(position)
            }

            return if (isMovable) super.getDragDirs(recyclerView, viewHolder) else 0
        }

        override fun getSwipeDirs(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
            val position = viewHolder!!.layoutPosition
            val adapter = adapter as? RecyclerSectionAdapter<*, *>
            var isSwipable = canSwipeAt(position)
            if (adapter != null) {
                isSwipable = !adapter.isSectionAt(position)
            }

            return if (isSwipable) super.getSwipeDirs(recyclerView, viewHolder) else 0
        }

        override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
            onMove?.let {
                val fromPosition = viewHolder!!.layoutPosition
                val toPosition = target!!.layoutPosition
                val adapter = adapter as? RecyclerAdapter<*>
                if (adapter != null) {

                    // Prevent move items outside its section if any
                    val sectionAdapter = adapter as? RecyclerSectionAdapter<*, *>
                    if (sectionAdapter != null) {
                        if (sectionAdapter.isSectionAt(toPosition)) {
                            sectionAdapter.notifyDataSetChanged()
                            return false
                        }
                    }

                    adapter.moveItem(fromPosition, toPosition)
                    return it(fromPosition, toPosition)
                }
            }

            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
            onSwipe?.let {
                val position = viewHolder!!.layoutPosition
                val adapter = adapter as? RecyclerAdapter<*>
                if (adapter != null) {
                    adapter.removeItem(position)
                    it(position, direction)
                }
            }
        }

    }).attachToRecyclerView(this)
}
