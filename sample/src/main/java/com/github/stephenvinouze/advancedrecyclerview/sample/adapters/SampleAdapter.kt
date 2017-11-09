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
class SampleAdapter(context: Context) : RecyclerAdapter<Sample>(context) {

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): View {
        return SampleItemView(context)
    }

    override fun onBindItemView(v: View, position: Int) {
        val sampleItemView = v as SampleItemView
        sampleItemView.bind(items[position], isItemViewToggled(position))
    }

    companion object {

        fun buildSamples(): ArrayList<Sample> {
            return (1..19).mapTo(ArrayList()) {
                Sample(id = it,
                        rate = (Math.random() * 5).toInt(),
                        name = "Sample name for index " + it
                )
            }
        }

    }

}
