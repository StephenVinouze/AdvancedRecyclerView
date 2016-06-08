package com.github.stephenvinouze.advancedrecyclerview.callbacks

import android.view.View

/**
 * Created by Stephen Vinouze on 10/11/2015.
 */
abstract class ClickCallback {

    abstract fun onItemClick(view: View, position: Int)

    open fun onItemLongClick(view: View, position: Int): Boolean {
        return false
    }

}
