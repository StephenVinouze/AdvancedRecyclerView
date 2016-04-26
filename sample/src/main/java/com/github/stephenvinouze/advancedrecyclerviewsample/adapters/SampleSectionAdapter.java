package com.github.stephenvinouze.advancedrecyclerviewsample.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerview.adapters.RecyclerSectionAdapter;
import com.github.stephenvinouze.advancedrecyclerview.extensions.SectionKt;
import com.github.stephenvinouze.advancedrecyclerviewsample.models.Sample;
import com.github.stephenvinouze.advancedrecyclerviewsample.views.SampleItemView;
import com.github.stephenvinouze.advancedrecyclerviewsample.views.SampleSectionItemView;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
public class SampleSectionAdapter extends RecyclerSectionAdapter<Integer, Sample> {

    public SampleSectionAdapter(Context context) {
        super(context);
    }

    @NotNull
    @Override
    protected View onCreateItemView(@NotNull ViewGroup parent, int viewType) {
        return new SampleItemView(getContext());
    }

    @Override
    protected void onBindItemView(@NonNull View v, int position) {
        SampleItemView sampleItemView = (SampleItemView)v;
        sampleItemView.bind(getItems().get(position), isItemViewToggled(position));
    }

    @NonNull
    @Override
    public View onCreateSectionItemView(@NonNull ViewGroup parent, int viewType) {
        return new SampleSectionItemView(getContext());
    }

    @Override
    public void onBindSectionItemView(@NonNull View v, int section) {
        SampleSectionItemView sampleSectionItemView = (SampleSectionItemView)v;
        sampleSectionItemView.bind((int) SectionKt.sectionAt(this, section));
    }

}
