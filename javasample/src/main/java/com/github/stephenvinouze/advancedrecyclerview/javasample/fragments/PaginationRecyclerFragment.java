package com.github.stephenvinouze.advancedrecyclerview.javasample.fragments;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.stephenvinouze.advancedrecyclerview.javasample.R;
import com.github.stephenvinouze.advancedrecyclerview.javasample.adapters.SamplePaginationAdapter;
import com.github.stephenvinouze.advancedrecyclerview.javasample.models.Sample;
import com.github.stephenvinouze.advancedrecyclerview.pagination.extensions.PaginationCallback;
import com.github.stephenvinouze.advancedrecyclerview.pagination.extensions.PaginationKt;

import java.util.List;

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
public class PaginationRecyclerFragment extends AbstractRecyclerFragment {

    private static final int PAGINATION_THRESHOLD = 5;

    private SwipeRefreshLayout refreshLayout;
    private SamplePaginationAdapter paginationAdapter;
    private final Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pagination_recycler_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        paginationAdapter = new SamplePaginationAdapter();

        configureRecyclerView(view.findViewById(R.id.recycler_view));
        recyclerView.setAdapter(paginationAdapter);

        PaginationKt.enablePagination(
                recyclerView,
                PAGINATION_THRESHOLD,
                new PaginationCallback() {
                    @Override
                    public boolean isLoading() {
                        return paginationAdapter.isLoading();
                    }

                    @Override
                    public boolean hasAllItems() {
                        return false;
                    }

                    @Override
                    public void onLoad() {
                        populatePage(false, true);
                    }
                }
        );

        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(() -> populatePage(true, true));

        populatePage(true, false);
    }

    private void populatePage(boolean reload, boolean delayed) {
        paginationAdapter.setLoading(true);

        if (delayed) {
            handler.postDelayed(() -> loadPage(reload), 2000);
        } else {
            loadPage(reload);
        }
    }

    private void loadPage(boolean reload) {
        List<Sample> items = Sample.mockItems();
        if (reload) {
            paginationAdapter.setItems(items);
        } else {
            PaginationKt.appendItems(paginationAdapter, items);
        }

        paginationAdapter.setLoading(false);
        refreshLayout.setRefreshing(false);
    }
}
