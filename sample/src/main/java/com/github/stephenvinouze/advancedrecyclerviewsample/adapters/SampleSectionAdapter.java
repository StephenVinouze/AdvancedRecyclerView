package com.github.stephenvinouze.advancedrecyclerviewsample.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerview.adapters.RecyclerSectionAdapter;
import com.github.stephenvinouze.advancedrecyclerviewsample.models.Sample;
import com.github.stephenvinouze.advancedrecyclerviewsample.views.SampleSectionItemView;

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
public class SampleSectionAdapter extends RecyclerSectionAdapter<Sample> {

    public SampleSectionAdapter(Context context) {
        super(context);
    }

    /**
     * Note that overriding this method allow you to customize the title displayed into the default section layout
     * Hence doing so will both ignore your @see(onCreateSectionItemView()) and @see(onBindSectionItemView()) methods
     * Default implementation returns null and is based on your sorting method to display the sections
     * In this example we choose to implement the custom section view callbacks to let you see how to display sections without the default section layout
     */
    @Override
    public String titleForSection(int sectionPosition) {
        return null;
    }

    @Override
    public View onCreateSectionItemView(ViewGroup parent, int viewType) {
        return new SampleSectionItemView(getContext());
    }

    @Override
    public void onBindSectionItemView(View v, int sectionPosition) {
        SampleSectionItemView sampleSectionItemView = (SampleSectionItemView)v;
        sampleSectionItemView.bind(getFirstItemInSection(sectionPosition));
    }
}
