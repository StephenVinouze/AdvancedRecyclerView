package com.github.stephenvinouze.advancedrecyclerview.javasample.adapters;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerview.core.adapters.RecyclerAdapter;
import com.github.stephenvinouze.advancedrecyclerview.javasample.models.Sample;
import com.github.stephenvinouze.advancedrecyclerview.javasample.views.SampleItemView;

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
public class SampleAdapter extends RecyclerAdapter<Sample> {

    @NonNull
    @Override
    protected View onCreateItemView(@NonNull ViewGroup parent, int viewType) {
        return new SampleItemView(parent.getContext());
    }

    @Override
    protected void onBindItemView(@NonNull View view, int position) {
        SampleItemView sampleItemView = (SampleItemView) view;
        sampleItemView.bind(getItems().get(position), isItemViewToggled(position));
    }

}
