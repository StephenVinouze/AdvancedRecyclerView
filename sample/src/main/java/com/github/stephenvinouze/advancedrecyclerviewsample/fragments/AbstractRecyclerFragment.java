package com.github.stephenvinouze.advancedrecyclerviewsample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerviewsample.R;
import com.github.stephenvinouze.advancedrecyclerviewsample.adapters.SampleAdapter;

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
public abstract class AbstractRecyclerFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected SampleAdapter mSampleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.recycler_layout, container, false);

        mRecyclerView = (RecyclerView)contentView.findViewById(R.id.recycler_view);
        mSampleAdapter = new SampleAdapter(getActivity());

        return contentView;
    }

}
