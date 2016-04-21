package com.github.stephenvinouze.advancedrecyclerview.callbacks

/**
 * Created by Stephen Vinouze on 10/11/2015.
 */
abstract class ClickCallback {

    abstract fun onItemClick(position: Int)

    fun onItemLongClick(position: Int): Boolean {
        return false
    }

}
