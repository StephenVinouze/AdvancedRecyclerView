package com.github.stephenvinouze.advancedrecyclerview.sample.models

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
data class Sample(val id: Int, val rate: Int, val name: String) {

    companion object {
        fun mockItems(): List<Sample> =
            (1 until 20).map {
                Sample(id = it,
                    rate = (Math.random() * 5).toInt(),
                    name = "Sample name for index $it"
                )
            }
    }
}
