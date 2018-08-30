package com.github.stephenvinouze.advancedrecyclerview.javasample.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerview.javasample.R;
import com.github.stephenvinouze.advancedrecyclerview.javasample.models.Sample;
import com.github.stephenvinouze.advancedrecyclerview.javasample.views.SampleItemView;
import com.github.stephenvinouze.advancedrecyclerview.pagination.adapters.RecyclerPaginationAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * Created by stephenvinouze on 15/11/2017.
 */

public class SamplePaginationAdapter extends RecyclerPaginationAdapter<Sample> {

    @NotNull
    @Override
    protected View onCreateItemView(@NotNull ViewGroup parent, int viewType) {
        return new SampleItemView(parent.getContext());
    }

    @NotNull
    @Override
    protected View onCreateLoaderView(@NotNull ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.view_progress, parent, false);
    }

    @Override
    protected void onBindItemView(@NotNull View view, int position) {
        SampleItemView sampleItemView = (SampleItemView) view;
        sampleItemView.bind(getItems().get(position), isItemViewToggled(position));
    }

}
