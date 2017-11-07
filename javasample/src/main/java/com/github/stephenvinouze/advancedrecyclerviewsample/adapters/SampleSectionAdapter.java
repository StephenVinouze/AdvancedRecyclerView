package com.github.stephenvinouze.advancedrecyclerviewsample.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerview_section.adapters.RecyclerSectionAdapter;
import com.github.stephenvinouze.advancedrecyclerviewsample.models.Sample;
import com.github.stephenvinouze.advancedrecyclerviewsample.views.SampleItemView;
import com.github.stephenvinouze.advancedrecyclerviewsample.views.SampleSectionItemView;

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
public class SampleSectionAdapter extends RecyclerSectionAdapter<Integer, Sample> {

    public SampleSectionAdapter(Context context) {
        super(context, (Sample::getRate));
    }

    @NonNull
    @Override
    protected View onCreateItemView(@NonNull ViewGroup parent, int viewType) {
        return new SampleItemView(getContext());
    }

    @Override
    protected void onBindItemView(@NonNull View v, int position) {
        SampleItemView sampleItemView = (SampleItemView) v;
        sampleItemView.bind(getItems().get(position), isItemViewToggled(position));
    }

    @NonNull
    @Override
    public View onCreateSectionItemView(@NonNull ViewGroup parent, int viewType) {
        return new SampleSectionItemView(getContext());
    }

    @Override
    public void onBindSectionItemView(@NonNull View sectionView, int sectionPosition) {
        Integer section = sectionAt(sectionPosition);
        if (section != null) {
            SampleSectionItemView sampleSectionItemView = (SampleSectionItemView) sectionView;
            sampleSectionItemView.bind(section);
        }
    }

}
