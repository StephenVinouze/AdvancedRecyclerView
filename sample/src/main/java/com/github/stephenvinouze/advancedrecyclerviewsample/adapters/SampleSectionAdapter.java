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

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
public class SampleSectionAdapter extends RecyclerSectionAdapter<Sample> {

    public SampleSectionAdapter(Context context) {
        super(context);

/*        Map<Integer, List<Sample>> sampleMap = new LinkedHashMap<>();
        for (Sample sample : getItems()) {
            int rate = sample.getRate();
            List<Sample> samples = new ArrayList<>();
            if (sampleMap.containsKey(rate)) {
                samples.add(sample);
            }

            sampleMap.put(rate, samples);
        }*/
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
    public void onBindSectionItemView(@NonNull View v, int sectionPosition) {
        SampleSectionItemView sampleSectionItemView = (SampleSectionItemView)v;
        sampleSectionItemView.bind(getItems().get(0));
    }

    @Override
    public int numberOfSections() {
        return getItems().size() / 2;
    }

    @Override
    public int numberOfItemsInSection(int section) {
        return 2;
    }

}
