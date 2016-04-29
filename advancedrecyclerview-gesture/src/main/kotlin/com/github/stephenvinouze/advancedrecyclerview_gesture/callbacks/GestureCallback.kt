package com.github.stephenvinouze.advancedrecyclerview.callbacks

/**
 * Created by Stephen Vinouze on 10/11/2015.
 */
abstract class GestureCallback {

    fun canMoveAt(position: Int): Boolean {
        return true
    }

    fun canSwipeAt(position: Int): Boolean {
        return true
    }

    abstract fun onMove(fromPosition: Int, toPosition: Int): Boolean
    abstract fun onSwiped(position: Int, direction: Int)

}
