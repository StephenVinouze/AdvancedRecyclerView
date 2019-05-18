package com.github.stephenvinouze.advancedrecyclerview.javasample.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.github.stephenvinouze.advancedrecyclerview.core.enums.ChoiceMode;
import com.github.stephenvinouze.advancedrecyclerview.javasample.adapters.SampleAdapter;
import com.github.stephenvinouze.advancedrecyclerview.javasample.models.Sample;

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
public class SingleChoiceRecyclerFragment extends AbstractRecyclerFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SampleAdapter adapter = new SampleAdapter();
        adapter.setChoiceMode(ChoiceMode.SINGLE);
        adapter.setItems(Sample.mockItems());
        adapter.setOnClick((view1, position) -> {
            Sample sample = adapter.getItems().get(position);
            Toast.makeText(getActivity(), "Item clicked : " + sample.getId() + " (" + adapter.getSelectedItemViewCount() + " selected)", Toast.LENGTH_SHORT).show();
            return null;
        });

        recyclerView.setAdapter(adapter);
    }

}
