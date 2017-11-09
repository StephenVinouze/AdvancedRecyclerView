package com.github.stephenvinouze.advancedrecyclerview.sample.views

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.github.stephenvinouze.advancedrecyclerview.sample.R
import java.util.*

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
class SampleSectionItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private val mIcons = ArrayList<TextView>()

    init {
        orientation = LinearLayout.HORIZONTAL
        setPadding(10, 20, 10, 20)

        (0 until 5).forEach {
            val icon = TextView(getContext())
            icon.text = context.getString(R.string.icon_rate)
            icon.textSize = 20f
            icon.setPadding(15, 10, 15, 10)
            icon.typeface = ResourcesCompat.getFont(context, R.font.fontawesome)

            addView(icon)
            mIcons.add(icon)
        }
    }

    fun bind(rate: Int) {
        mIcons.forEach {
            it.setTextColor(ContextCompat.getColor(context, if (rate > mIcons.indexOf(it)) R.color.colorAccent else R.color.colorPrimaryLight))
        }
    }

}
