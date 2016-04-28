package com.github.stephenvinouze.advancedrecyclerviewsample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.stephenvinouze.advancedrecyclerview.adapters.RecyclerAdapter;
import com.github.stephenvinouze.advancedrecyclerview.callbacks.ClickCallback;
import com.github.stephenvinouze.advancedrecyclerview.callbacks.GestureCallback;
import com.github.stephenvinouze.advancedrecyclerview.extensions.GestureKt;
import com.github.stephenvinouze.advancedrecyclerviewsample.adapters.SampleAdapter;
import com.github.stephenvinouze.advancedrecyclerviewsample.adapters.SampleSectionAdapter;
import com.github.stephenvinouze.advancedrecyclerviewsample.models.Sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
public class GestureSectionRecyclerFragment extends AbstractRecyclerFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);

        final SampleSectionAdapter sectionAdapter = new SampleSectionAdapter(getActivity());
        sectionAdapter.setChoiceMode(RecyclerAdapter.ChoiceMode.MULTIPLE_CHOICE);
        sectionAdapter.setClickCallback(new ClickCallback() {
            @Override
            public void onItemClick(@NonNull View view, int position) {
                Sample sample = sectionAdapter.getItems().get(position);
                Toast.makeText(getActivity(), "Item clicked : " + sample.getId() + " (" + sectionAdapter.getSelectedItemViewCount() + " selected)", Toast.LENGTH_SHORT).show();
            }
        });

        GestureKt.handleGesture(mRecyclerView, ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, new GestureCallback() {
            @Override
            public boolean onMove(int fromPosition, int toPosition) {
                Toast.makeText(getActivity(), "Item selected : " + sectionAdapter.getSelectedItemViews(), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(int position, int direction) {
                Toast.makeText(getActivity(), "Item selected : " + sectionAdapter.getSelectedItemViews(), Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<Sample> samples = SampleAdapter.buildSamples();
        Collections.sort(samples, new Comparator<Sample>() {
            @Override
            public int compare(Sample lhs, Sample rhs) {
                return lhs.getRate() - rhs.getRate();
            }
        });

        sectionAdapter.setItems(samples);

        mRecyclerView.setAdapter(sectionAdapter);

        return contentView;
    }

}
