package com.github.stephenvinouze.advancedrecyclerviewsample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerviewsample.R;
import com.github.stephenvinouze.advancedrecyclerviewsample.adapters.SampleAdapter;
import com.github.stephenvinouze.advancedrecyclerviewsample.adapters.SampleSectionAdapter;

/**
 * Created by Stephen Vinouze on 09/11/2015.
 */
public abstract class AbstractRecyclerFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected SampleAdapter mSampleAdapter;
    protected SampleSectionAdapter mSampleSectionAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.recycler_layout, container, false);

        mRecyclerView = (RecyclerView)contentView.findViewById(R.id.recycler_view);
        mSampleAdapter = new SampleAdapter(getActivity());

        return contentView;
    }

    public void configureFragment(@NonNull RecyclerView recyclerView, @NonNull SampleAdapter adapter) {
        configureFragment(recyclerView, adapter, null);
    }

    public void configureFragment(@NonNull RecyclerView recyclerView,
                                  @NonNull SampleAdapter adapter,
                                  @Nullable SampleSectionAdapter sectionAdapter) {
        mSampleAdapter = adapter;

/*        String sortName = sortSectionMethod();
        if (sortName != null) {

            if (sectionAdapter == null) {
                sectionAdapter = new RecyclerSectionAdapter<>(getActivity());
            }

            mSectionAdapter = sectionAdapter;

            sectionAdapter.setBaseAdapter(adapter);
            sectionAdapter.setSortMethodName(sortName);
            recyclerView.setAdapter(sectionAdapter);
        }
        else {
            mSectionAdapter = null;
            recyclerView.setAdapter(adapter);
        }*/

        mRecyclerView = recyclerView;
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mSampleAdapter);
    }

}
