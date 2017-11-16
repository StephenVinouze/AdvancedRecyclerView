package com.github.stephenvinouze.advancedrecyclerview.javasample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.stephenvinouze.advancedrecyclerview.core.enums.ChoiceMode;
import com.github.stephenvinouze.advancedrecyclerview.gesture.extensions.GestureCallback;
import com.github.stephenvinouze.advancedrecyclerview.gesture.extensions.GestureKt;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final SampleSectionAdapter sectionAdapter = new SampleSectionAdapter(getContext());
        sectionAdapter.setChoiceMode(ChoiceMode.MULTIPLE);
        sectionAdapter.setOnClick((view1, position) -> {
            Sample sample = sectionAdapter.getItems().get(position);
            Toast.makeText(getActivity(), "Item clicked : " + sample.getId() + " (" + sectionAdapter.getSelectedItemViewCount() + " selected)", Toast.LENGTH_SHORT).show();
            return null;
        });

        ArrayList<Sample> samples = Sample.mockItems();
        Collections.sort(samples, (lhs, rhs) -> lhs.getRate() - rhs.getRate());

        sectionAdapter.setItems(samples);

        recyclerView.setAdapter(sectionAdapter);
        GestureKt.enableGestures(
                recyclerView,
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                new GestureCallback() {
                    @Override
                    public boolean onMove(int fromPosition, int toPosition) {
                        Toast.makeText(getContext(), "Item moved from position " + fromPosition + " to position " + toPosition, Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public void onSwiped(int position, int direction) {
                        Toast.makeText(getContext(), "Item swiped at position " + position, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

}
