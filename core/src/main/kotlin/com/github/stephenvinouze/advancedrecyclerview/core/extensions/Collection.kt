package com.github.stephenvinouze.advancedrecyclerview.core.extensions

/**
 * Created by stephenvinouze on 26/04/16.
 */

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}
