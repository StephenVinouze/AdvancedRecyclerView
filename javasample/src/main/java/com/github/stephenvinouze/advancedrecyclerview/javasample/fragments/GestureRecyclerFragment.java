package com.github.stephenvinouze.advancedrecyclerview.javasample.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;

import com.github.stephenvinouze.advancedrecyclerview.gesture.extensions.GestureKt;
import com.github.stephenvinouze.advancedrecyclerview.javasample.adapters.SampleAdapter;
import com.github.stephenvinouze.advancedrecyclerview.javasample.models.Sample;

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
public class GestureRecyclerFragment extends AbstractRecyclerFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SampleAdapter adapter = new SampleAdapter();
        adapter.setItems(Sample.mockItems());

        recyclerView.setAdapter(adapter);
        GestureKt.enableGestures(
                recyclerView,
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                null
        );
    }

}
