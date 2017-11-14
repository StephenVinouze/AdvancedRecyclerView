package com.github.stephenvinouze.advancedrecyclerview.sample.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.github.stephenvinouze.advancedrecyclerview.core.adapters.RecyclerAdapter
import com.github.stephenvinouze.advancedrecyclerview.sample.models.Sample
import com.github.stephenvinouze.advancedrecyclerview.sample.views.SampleItemView
import java.util.*

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
open class SampleAdapter(context: Context) : RecyclerAdapter<Sample>(context) {

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): View = SampleItemView(context)

    override fun onBindItemView(view: View, position: Int) {
        when (view) {
            is SampleItemView -> view.bind(items[position], isItemViewToggled(position))
        }
    }

    companion object {

        fun buildSamples(itemCount: Int = 0): ArrayList<Sample> {
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
