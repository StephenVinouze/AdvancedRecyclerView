package com.github.stephenvinouze.advancedrecyclerviewsample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerview_gesture.extensions.GestureKt;
import com.github.stephenvinouze.advancedrecyclerviewsample.adapters.SampleAdapter;

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
public class GestureRecyclerFragment extends AbstractRecyclerFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);

        final SampleAdapter adapter = new SampleAdapter(getActivity());
        adapter.setItems(SampleAdapter.buildSamples());

        mRecyclerView.setAdapter(adapter);

        GestureKt.handleGesture(mRecyclerView, ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, null);

        return contentView;
    }

}
