package com.github.stephenvinouze.advancedrecyclerview.extensions

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.github.stephenvinouze.advancedrecyclerview.adapters.RecyclerAdapter
import com.github.stephenvinouze.advancedrecyclerview.adapters.RecyclerSectionAdapter
import com.github.stephenvinouze.advancedrecyclerview.callbacks.GestureCallback

/**
 * Created by stephenvinouze on 26/04/16.
 */

/**
 * Configure your gestures if any for both move and swipe gestures.
 * You can also implement the OnRecyclerTouchCallback to be notified of the move/swipe events as well as enabling/disabling these gestures for some specific items
 * @param dragDirections The move directions. Can be either ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT, ItemTouchHelper.UP or ItemTouchHelper.DOWN
 * @param swipeDirections The swipe directions. Can be either ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT, ItemTouchHelper.UP or ItemTouchHelper.DOWN
 * @param callback The gesture callback
 */
fun RecyclerView.handleGesture(dragGestures: Int, swipeGestures: Int, callback: GestureCallback? = null) {
    ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(dragGestures, swipeGestures) {

        override fun getDragDirs(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
            val position = viewHolder!!.layoutPosition
            val adapter = adapter as? RecyclerSectionAdapter<*, *>
            var isMovable = callback?.canMoveAt(position) ?: true
            if (adapter != null) {
                isMovable = !adapter.isSectionAt(position)
            }

            return if (isMovable) super.getDragDirs(recyclerView, viewHolder) else 0
        }

        override fun getSwipeDirs(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
            val position = viewHolder!!.layoutPosition
            val adapter = adapter as? RecyclerSectionAdapter<*, *>
            var isSwipable = callback?.canSwipeAt(position) ?: true
            if (adapter != null) {
                isSwipable = !adapter.isSectionAt(position)
            }

            return if (isSwipable) super.getSwipeDirs(recyclerView, viewHolder) else 0
        }

        override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
            var fromPosition = viewHolder!!.layoutPosition
            var toPosition = target!!.layoutPosition
            val adapter = adapter as? RecyclerAdapter<*>
            if (adapter != null) {

                val sectionAdapter = adapter as? RecyclerSectionAdapter<*, *>
                if (sectionAdapter != null) {
                    if (sectionAdapter.isSectionAt(toPosition)) {
                        sectionAdapter.notifyDataSetChanged()
                        return false
                    }

                    fromPosition = sectionAdapter.absolutePosition(fromPosition)
                    toPosition = sectionAdapter.absolutePosition(toPosition)
                }

                adapter.moveItem(fromPosition, toPosition)

                return callback?.onMove(fromPosition, toPosition) ?: true
            }

            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
            var position = viewHolder!!.layoutPosition
            val adapter = adapter as? RecyclerAdapter<*>
            if (adapter != null) {

                val sectionAdapter = adapter as? RecyclerSectionAdapter<*, *>
                if (sectionAdapter != null) {
                    position = sectionAdapter.absolutePosition(position)
                }

                adapter.removeItem(position)

                callback?.onSwiped(position, direction)
            }
        }

    }).attachToRecyclerView(this)
}
