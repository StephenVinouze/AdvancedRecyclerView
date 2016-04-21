package com.github.stephenvinouze.advancedrecyclerviewsample.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.stephenvinouze.advancedrecyclerviewsample.R;
import com.github.stephenvinouze.advancedrecyclerviewsample.models.Sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
public class SampleSectionItemView extends LinearLayout {

    private List<TextView> mIcons = new ArrayList<>();

    public SampleSectionItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initViews(context);
    }

    public SampleSectionItemView(Context context) {
        super(context);
        initViews(context);
    }

    public void bind(Sample sample) {
        if (sample != null) {
            for (TextView icon : mIcons) {
                if (sample.getRate() > mIcons.indexOf(icon)) {
                    icon.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                }
            }
        }
    }

    private void initViews(Context context) {
        setOrientation(HORIZONTAL);
        setPadding(10, 10, 10, 10);

        for (int i = 0; i < 5; i++) {
            TextView icon = new TextView(getContext());
            icon.setText(context.getString(R.string.icon_rate));
            icon.setTextSize(20);
            icon.setPadding(10, 10, 10, 10);
            icon.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf"));

            addView(icon);
            mIcons.add(icon);
        }
    }
}
