package com.github.stephenvinouze.advancedrecyclerviewsample.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerview.adapters.RecyclerSectionAdapter;
import com.github.stephenvinouze.advancedrecyclerviewsample.models.Sample;
import com.github.stephenvinouze.advancedrecyclerviewsample.views.SampleItemView;
import com.github.stephenvinouze.advancedrecyclerviewsample.views.SampleSectionItemView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
public class SampleSectionAdapter extends RecyclerSectionAdapter<Sample> {

    private Map<Integer, List<Sample>> samplesMap = new LinkedHashMap<>();

    public SampleSectionAdapter(Context context) {
        super(context);
    }

    @Override
    public void setItems(@NotNull List<Sample> samples) {
        super.setItems(samples);

        Collections.sort(samples, new Comparator<Sample>() {
            @Override
            public int compare(Sample lhs, Sample rhs) {
                return lhs.getRate() - rhs.getRate();
            }
        });

        int currentRate = 0;
        List<Sample> rankedSamples = new ArrayList<>();
        for (Sample sample : samples) {
            if (sample.getRate() != currentRate || samples.indexOf(sample) == samples.size() - 1) {
                if (!rankedSamples.isEmpty()) {
                    samplesMap.put(currentRate, new ArrayList<>(rankedSamples));
                }

                currentRate = sample.getRate();
                rankedSamples.clear();
            }
            else {
                rankedSamples.add(sample);
            }
        }
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

    @Override
    public int numberOfSections() {
        return samplesMap.size();
    }

    @Override
    public int numberOfItemsInSection(int section) {
        Object keyAtIndex = samplesMap.keySet().toArray()[section];
        return samplesMap.get(keyAtIndex).size();
    }

    @NonNull
    @Override
    public View onCreateSectionItemView(@NonNull ViewGroup parent, int viewType) {
        return new SampleSectionItemView(getContext());
    }

    @Override
    public void onBindSectionItemView(@NonNull View v, int section) {
        int rate = (Integer)samplesMap.keySet().toArray()[section];
        SampleSectionItemView sampleSectionItemView = (SampleSectionItemView)v;
        sampleSectionItemView.bind(rate);
    }

}
