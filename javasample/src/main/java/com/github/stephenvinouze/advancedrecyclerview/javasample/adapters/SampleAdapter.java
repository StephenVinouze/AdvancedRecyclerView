package com.github.stephenvinouze.advancedrecyclerview.javasample.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerview.core.adapters.RecyclerAdapter;
import com.github.stephenvinouze.advancedrecyclerview.javasample.models.Sample;
import com.github.stephenvinouze.advancedrecyclerview.javasample.views.SampleItemView;

import java.util.ArrayList;

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
public class SampleAdapter extends RecyclerAdapter<Sample> {

    public SampleAdapter(Context context) {
        super(context);
    }

    public static ArrayList<Sample> buildSamples() {
        ArrayList<Sample> samples = new ArrayList<>();

        for (int i = 1; i < 20; i++) {
            Sample sample = new Sample();
            sample.setId(i);
            sample.setRate((int) (Math.random() * 5));
            sample.setName("Sample name for index " + i);
            samples.add(sample);
        }

        return samples;
    }

    @NonNull
    @Override
    protected View onCreateItemView(@NonNull ViewGroup parent, int viewType) {
        return new SampleItemView(getContext());
    }

    @Override
    protected void onBindItemView(@NonNull View v, int position) {
        SampleItemView sampleItemView = (SampleItemView)v;
        sampleItemView.bind(getItems().get(position), isItemViewToggled(position));
    }

}
