package com.github.stephenvinouze.advancedrecyclerview.sample.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.github.stephenvinouze.advancedrecyclerview.sample.R
import com.github.stephenvinouze.advancedrecyclerview.sample.models.Sample
import kotlinx.android.synthetic.main.view_sample_item.view.*

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
class SampleItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_sample_item, this, true)
    }

    fun bind(sample: Sample, isToggled: Boolean) {
        sampleItemIndexTV.text = String.format("%d", sample.id)
        sampleItemNameTV.text = sample.name
        sampleItemTickIcon.visibility = if (isToggled) View.VISIBLE else View.GONE
    }
}
