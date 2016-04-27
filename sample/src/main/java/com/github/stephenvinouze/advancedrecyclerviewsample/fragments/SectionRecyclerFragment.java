package com.github.stephenvinouze.advancedrecyclerviewsample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerviewsample.adapters.SampleAdapter;
import com.github.stephenvinouze.advancedrecyclerviewsample.adapters.SampleSectionAdapter;
import com.github.stephenvinouze.advancedrecyclerviewsample.models.Sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
public class SectionRecyclerFragment extends AbstractRecyclerFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);

        ArrayList<Sample> samples = SampleAdapter.buildSamples();
        Collections.sort(samples, new Comparator<Sample>() {
            @Override
            public int compare(Sample lhs, Sample rhs) {
                return lhs.getRate() - rhs.getRate();
            }
        });

        final SampleSectionAdapter sectionAdapter = new SampleSectionAdapter(getActivity());
        sectionAdapter.setItems(samples);

        mRecyclerView.setAdapter(sectionAdapter);

        return contentView;
    }

}
