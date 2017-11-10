package com.github.stephenvinouze.advancedrecyclerview.javasample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.stephenvinouze.advancedrecyclerview.gesture.GestureKt;
import com.github.stephenvinouze.advancedrecyclerview.javasample.adapters.SampleAdapter;

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
public class GestureRecyclerFragment extends AbstractRecyclerFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SampleAdapter adapter = new SampleAdapter(getActivity());
        adapter.setItems(SampleAdapter.buildSamples());

        recyclerView.setAdapter(adapter);

        GestureKt.onGesture(recyclerView,
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
                integer -> true);
    }

}
