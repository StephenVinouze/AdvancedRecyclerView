package com.github.stephenvinouze.advancedrecyclerviewsample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerviewsample.R;
import com.github.stephenvinouze.advancedrecyclerviewsample.adapters.SampleAdapter;
import com.github.stephenvinouze.advancedrecyclerviewsample.models.Sample;

import java.util.List;

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
public class PaginationRecyclerFragment extends AbstractRecyclerFragment {

    private SwipeRefreshLayout mRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.pagination_recycler_layout, container, false);

        RecyclerView recyclerView = (RecyclerView)contentView.findViewById(R.id.recycler_view);
        mRefreshLayout = (SwipeRefreshLayout)contentView.findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fillList(1);
            }
        });

        SampleAdapter sampleAdapter = new SampleAdapter(getActivity());

/*        configureFragment(recyclerView, sampleAdapter);
        setPaginationCallback(new PaginationCallback() {
            @Override
            public void fetchNextPage(int nextPage) {
                fillList(nextPage);
            }
        });*/

        fillList(1);

        return contentView;
    }

    private void fillList(int page) {
        List<Sample> samples = SampleAdapter.buildSamples();

        //displayItems(samples, page);

        mRefreshLayout.setRefreshing(false);
    }

/*    @Override
    public String sortSectionMethod() {
        return null;
    }*/
}
