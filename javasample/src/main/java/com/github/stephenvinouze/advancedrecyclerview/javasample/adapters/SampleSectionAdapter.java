package com.github.stephenvinouze.advancedrecyclerview.javasample.adapters;

import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerview.javasample.models.Sample;
import com.github.stephenvinouze.advancedrecyclerview.javasample.views.SampleItemView;
import com.github.stephenvinouze.advancedrecyclerview.javasample.views.SampleSectionItemView;
import com.github.stephenvinouze.advancedrecyclerview.section.adapters.RecyclerSectionAdapter;

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
public class SampleSectionAdapter extends RecyclerSectionAdapter<Integer, Sample> {

    public SampleSectionAdapter() {
        super(Sample::getRate);
    }

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

    @NonNull
    @Override
    public View onCreateSectionItemView(@NonNull ViewGroup parent, int viewType) {
        return new SampleSectionItemView(parent.getContext());
    }

    @Override
    public void onBindSectionItemView(@NonNull View sectionView, int sectionPosition) {
        Integer section = getViewModel().sectionAt(sectionPosition);
        if (section != null) {
            SampleSectionItemView sampleSectionItemView = (SampleSectionItemView) sectionView;
            sampleSectionItemView.bind(section);
        }
    }

}
