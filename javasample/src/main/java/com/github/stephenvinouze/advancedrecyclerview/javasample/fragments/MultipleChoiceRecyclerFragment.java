package com.github.stephenvinouze.advancedrecyclerview.javasample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.github.stephenvinouze.advancedrecyclerview.core.enums.ChoiceMode;
import com.github.stephenvinouze.advancedrecyclerview.javasample.adapters.SampleAdapter;
import com.github.stephenvinouze.advancedrecyclerview.javasample.models.Sample;

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
public class MultipleChoiceRecyclerFragment extends AbstractRecyclerFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SampleAdapter adapter = new SampleAdapter(getContext());
        adapter.setItems(Sample.mockItems());
        adapter.setChoiceMode(ChoiceMode.MULTIPLE);
        adapter.setOnClick((view1, position) -> {
            Sample sample = adapter.getItems().get(position);
            Toast.makeText(getActivity(), "Item clicked : " + sample.getId() + " (" + adapter.getSelectedItemViewCount() + " selected)", Toast.LENGTH_SHORT).show();
            return null;
        });

        recyclerView.setAdapter(adapter);
    }

}
