package com.github.stephenvinouze.advancedrecyclerview.javasample.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.stephenvinouze.advancedrecyclerview.javasample.R;
import com.github.stephenvinouze.advancedrecyclerview.javasample.models.Sample;

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
public class SampleItemView extends FrameLayout {

    private TextView mIndexTextView;
    private TextView mNameTextView;
    private TextView mTickIconView;

    public SampleItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initViews(context);
    }

    public SampleItemView(Context context) {
        super(context);
        initViews(context);
    }

    public void bind(Sample sample, boolean isToggled) {
        if (sample != null) {
            mIndexTextView.setText(String.format("%d", sample.getId()));
            mNameTextView.setText(sample.getName());
        }

        mTickIconView.setVisibility(isToggled ? VISIBLE : GONE);
    }

    private void initViews(Context context) {
        View view = inflate(getContext(), R.layout.sample_item_view, this);
        mIndexTextView = view.findViewById(R.id.sample_item_index_text_view);
        mNameTextView = view.findViewById(R.id.sample_item_name_text_view);
        mTickIconView = view.findViewById(R.id.sample_item_name_tick_view);
        mTickIconView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf"));
    }
}
