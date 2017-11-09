package com.github.stephenvinouze.advancedrecyclerview.javasample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.stephenvinouze.advancedrecyclerview.core.adapters.RecyclerAdapter;
import com.github.stephenvinouze.advancedrecyclerview.core.callbacks.ClickCallback;
import com.github.stephenvinouze.advancedrecyclerview.gesture.GestureKt;
import com.github.stephenvinouze.advancedrecyclerview.javasample.adapters.SampleAdapter;
import com.github.stephenvinouze.advancedrecyclerview.javasample.adapters.SampleSectionAdapter;
import com.github.stephenvinouze.advancedrecyclerview.javasample.models.Sample;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
public class GestureSectionRecyclerFragment extends AbstractRecyclerFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final SampleSectionAdapter sectionAdapter = new SampleSectionAdapter(getActivity());
        sectionAdapter.setChoiceMode(RecyclerAdapter.ChoiceMode.MULTIPLE);
        sectionAdapter.setClickCallback(new ClickCallback() {
            @Override
            public void onItemClick(@NonNull View view, int position) {
                Sample sample = sectionAdapter.getItems().get(position);
                Toast.makeText(getActivity(), "Item clicked : " + sample.getId() + " (" + sectionAdapter.getSelectedItemViewCount() + " selected)", Toast.LENGTH_SHORT).show();
            }
        });

/*        GestureKt.handleGesture(mRecyclerView, ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, new GestureCallback() {
            @Override
            public boolean onMove(int fromPosition, int toPosition) {
                Toast.makeText(getActivity(), "Item selected : " + sectionAdapter.getSelectedItemViews(), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(int position, int direction) {
                Toast.makeText(getActivity(), "Item selected : " + sectionAdapter.getSelectedItemViews(), Toast.LENGTH_SHORT).show();
            }
        });*/
        GestureKt.handleGesture(mRecyclerView,
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                (fromPosition, toPosition) -> {
                    Toast.makeText(getContext(), "Item moved from position " + fromPosition + " to position " + toPosition, Toast.LENGTH_SHORT).show();
                    return false;
                },
                (position, directions) -> {
                    Toast.makeText(getContext(), "Item swiped at position " + position, Toast.LENGTH_SHORT).show();
                    return null;
                },
                integer -> true,
                integer -> true
        );

        ArrayList<Sample> samples = SampleAdapter.buildSamples();
        Collections.sort(samples, (lhs, rhs) -> lhs.getRate() - rhs.getRate());

        sectionAdapter.setItems(samples);

        mRecyclerView.setAdapter(sectionAdapter);
    }

}
