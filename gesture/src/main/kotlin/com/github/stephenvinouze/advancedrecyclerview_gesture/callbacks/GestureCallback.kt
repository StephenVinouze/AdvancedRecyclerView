package com.github.stephenvinouze.advancedrecyclerview_gesture.callbacks

/**
 * Created by Stephen Vinouze on 10/11/2015.
 */
abstract class GestureCallback {

    open fun canMoveAt(position: Int): Boolean = true
    open fun canSwipeAt(position: Int): Boolean = true

    abstract fun onMove(fromPosition: Int, toPosition: Int): Boolean
    abstract fun onSwiped(position: Int, direction: Int)

}
