package com.github.stephenvinouze.advancedrecyclerviewsample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerview.callbacks.PaginationCallback;
import com.github.stephenvinouze.advancedrecyclerview.extensions.PaginationKt;
import com.github.stephenvinouze.advancedrecyclerviewsample.R;
import com.github.stephenvinouze.advancedrecyclerviewsample.adapters.SampleAdapter;

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
public class PaginationRecyclerFragment extends AbstractRecyclerFragment {

    private SwipeRefreshLayout mRefreshLayout;
    private SampleAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.pagination_recycler_layout, container, false);

        mRefreshLayout = (SwipeRefreshLayout)contentView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populatePage(1);
            }
        });

        configureRecyclerView((RecyclerView)contentView.findViewById(R.id.recycler_view));

        mAdapter = new SampleAdapter(getActivity());

        PaginationKt.handlePagination(mRecyclerView, new PaginationCallback() {
            @Override
            public void fetchNextPage(int nextPage) {
                populatePage(nextPage);
            }
        });

        populatePage(1);

        mRecyclerView.setAdapter(mAdapter);

        return contentView;
    }

    private void populatePage(int page) {
        PaginationKt.setItems(mAdapter, SampleAdapter.buildSamples(), page);

        mRefreshLayout.setRefreshing(false);
    }

}
