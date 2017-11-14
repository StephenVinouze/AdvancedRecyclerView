package com.github.stephenvinouze.advancedrecyclerview.sample.models

import java.util.*

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
data class Sample(val id: Int, val rate: Int, val name: String) {

    companion object {

        fun mockItems(itemCount: Int = 0): ArrayList<Sample> {
            return (1 until 20).mapTo(ArrayList()) {
                val id = it + itemCount
                Sample(id = id,
                        rate = (Math.random() * 5).toInt(),
                        name = "Sample name for index " + id
                )
            }
        }

    }

}
