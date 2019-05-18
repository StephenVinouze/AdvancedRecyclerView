package com.github.stephenvinouze.advancedrecyclerview.gesture.extensions

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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
fun RecyclerView.enableGestures(
    dragDirections: Int,
    swipeDirections: Int,
    onMove: ((fromPosition: Int, toPosition: Int) -> Boolean)? = null,
    onSwipe: ((position: Int, direction: Int) -> Unit)? = null,
    canMoveAt: (position: Int) -> Boolean = { true },
    canSwipeAt: (position: Int) -> Boolean = { true }
) {
    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(dragDirections, swipeDirections) {

        override fun getDragDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val position = viewHolder.layoutPosition
            var isMovable = canMoveAt(position)
            (adapter as? RecyclerSectionAdapter<*, *>)?.let {
                isMovable = !it.isSectionAt(position)
            }

            return if (isMovable) super.getDragDirs(recyclerView, viewHolder) else 0
        }

        override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val position = viewHolder.layoutPosition
            var isSwipable = canSwipeAt(position)
            (adapter as? RecyclerSectionAdapter<*, *>)?.let {
                isSwipable = !it.isSectionAt(position)
            }

            return if (isSwipable) super.getSwipeDirs(recyclerView, viewHolder) else 0
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.layoutPosition
            val toPosition = target.layoutPosition
            (adapter as? RecyclerAdapter<*>)?.let { adapter ->
                // Prevent move items outside its section if any
                (adapter as? RecyclerSectionAdapter<*, *>)
                    ?.takeIf { it.isSectionAt(toPosition) }
                    ?.let {
                        it.notifyDataSetChanged()
                        return false
                    }

                adapter.moveItem(fromPosition, toPosition)
                return onMove?.invoke(fromPosition, toPosition) ?: true
            }

            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            (adapter as? RecyclerAdapter<*>)?.let {
                it.removeItem(position)
                onSwipe?.invoke(position, direction)
            }
        }
    }).attachToRecyclerView(this)
}

/**
 * [JAVA VERSION with abstract class as callback instead of lambdas]
 * Configure your gestures if any for both move and swipe gestures.
 * You can also implement the OnRecyclerTouchCallback to be notified of the move/swipe events as well as enabling/disabling these gestures for some specific items
 * @param dragDirections The move directions. Can be either ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT, ItemTouchHelper.UP or ItemTouchHelper.DOWN
 * @param swipeDirections The swipe directions. Can be either ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT, ItemTouchHelper.UP or ItemTouchHelper.DOWN
 * @param callback The gesture callback
 */
fun RecyclerView.enableGestures(
    dragDirections: Int,
    swipeDirections: Int,
    callback: GestureCallback? = null
) {
    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(dragDirections, swipeDirections) {

        override fun getDragDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val position = viewHolder.layoutPosition
            var isMovable = callback?.canMoveAt(position) ?: true
            (adapter as? RecyclerSectionAdapter<*, *>)?.let {
                isMovable = !it.isSectionAt(position)
            }

            return if (isMovable) super.getDragDirs(recyclerView, viewHolder) else 0
        }

        override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val position = viewHolder.layoutPosition
            var isSwipable = callback?.canSwipeAt(position) ?: true
            (adapter as? RecyclerSectionAdapter<*, *>)?.let {
                isSwipable = !it.isSectionAt(position)
            }

            return if (isSwipable) super.getSwipeDirs(recyclerView, viewHolder) else 0
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = viewHolder.layoutPosition
            val toPosition = target.layoutPosition
            (adapter as? RecyclerAdapter<*>)?.let { adapter ->
                // Prevent move items outside its section if any
                (adapter as? RecyclerSectionAdapter<*, *>)
                    ?.takeIf { it.isSectionAt(toPosition) }
                    ?.let {
                        it.notifyDataSetChanged()
                        return false
                    }
                adapter.moveItem(fromPosition, toPosition)
                return callback?.onMove(fromPosition, toPosition) ?: true
            }

            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            (adapter as? RecyclerAdapter<*>)?.let {
                it.removeItem(position)
                callback?.onSwiped(position, direction)
            }
        }
    }).attachToRecyclerView(this)
}

abstract class GestureCallback {
    open fun canMoveAt(position: Int): Boolean = true
    open fun canSwipeAt(position: Int): Boolean = true

    abstract fun onMove(fromPosition: Int, toPosition: Int): Boolean
    abstract fun onSwiped(position: Int, direction: Int)
}
