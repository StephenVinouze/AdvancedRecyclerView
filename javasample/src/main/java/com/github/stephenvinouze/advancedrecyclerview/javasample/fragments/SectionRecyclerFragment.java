package com.github.stephenvinouze.advancedrecyclerview.javasample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.stephenvinouze.advancedrecyclerview.core.enums.ChoiceMode;
import com.github.stephenvinouze.advancedrecyclerview.javasample.adapters.SampleSectionAdapter;
import com.github.stephenvinouze.advancedrecyclerview.javasample.models.Sample;

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
public class SectionRecyclerFragment extends AbstractRecyclerFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SampleSectionAdapter sectionAdapter = new SampleSectionAdapter(getContext());
        sectionAdapter.setChoiceMode(ChoiceMode.NONE);
        sectionAdapter.setItems(Sample.mockItems());

        recyclerView.setAdapter(sectionAdapter);
    }

}
