package com.github.stephenvinouze.advancedrecyclerview.javasample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerview.javasample.R;
import com.github.stephenvinouze.advancedrecyclerview.javasample.adapters.SampleAdapter;
import com.github.stephenvinouze.advancedrecyclerview.pagination.PaginationKt;

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
public class PaginationRecyclerFragment extends AbstractRecyclerFragment {

    private SwipeRefreshLayout mRefreshLayout;
    private SampleAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pagination_recycler_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(() -> populatePage(1));

        configureRecyclerView(view.findViewById(R.id.recycler_view));

        mAdapter = new SampleAdapter(getActivity());

        populatePage(1);

        recyclerView.setAdapter(mAdapter);

        PaginationKt.onPaginate(recyclerView, (page) -> {
            populatePage(page);
            return null;
        });
    }

    private void populatePage(int page) {
        PaginationKt.setItems(mAdapter, SampleAdapter.buildSamples(), page);

        mRefreshLayout.setRefreshing(false);
    }

}
