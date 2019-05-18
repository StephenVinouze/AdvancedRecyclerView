package com.github.stephenvinouze.advancedrecyclerview.javasample.views;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.stephenvinouze.advancedrecyclerview.javasample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
public class SampleSectionItemView extends LinearLayout {

    private final List<TextView> mIcons = new ArrayList<>();

    public SampleSectionItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initViews(context);
    }

    public SampleSectionItemView(Context context) {
        super(context);
        initViews(context);
    }

    public void bind(int rate) {
        for (TextView icon : mIcons) {
            icon.setTextColor(ContextCompat.getColor(getContext(), rate > mIcons.indexOf(icon) ? R.color.colorAccent : R.color.colorPrimaryLight));
        }
    }

    private void initViews(Context context) {
        setOrientation(HORIZONTAL);
        setPadding(10, 20, 10, 20);

        for (int i = 0; i < 5; i++) {
            TextView icon = new TextView(getContext());
            icon.setText(context.getString(R.string.icon_rate));
            icon.setTextSize(20);
            icon.setPadding(15, 10, 15, 10);
            icon.setTypeface(ResourcesCompat.getFont(getContext(), R.font.fontawesome));

            addView(icon);
            mIcons.add(icon);
        }
    }
}
