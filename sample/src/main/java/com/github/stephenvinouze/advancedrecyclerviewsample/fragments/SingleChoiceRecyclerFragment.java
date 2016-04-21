package com.github.stephenvinouze.advancedrecyclerviewsample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.stephenvinouze.advancedrecyclerview.callbacks.ClickCallback;
import com.github.stephenvinouze.advancedrecyclerviewsample.adapters.SampleAdapter;
import com.github.stephenvinouze.advancedrecyclerviewsample.models.Sample;

import java.util.List;

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
public class SingleChoiceRecyclerFragment extends AbstractRecyclerFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);

        mSampleAdapter.setClickCallback(new ClickCallback() {
            @Override
            public void onItemClick(int position) {
                Sample sample = mSampleAdapter.getItemAt(position);
                Toast.makeText(getActivity(), "Item clicked : " + sample.getName() + " (" + mSampleAdapter.getSelectedItemViewCount() + " selected)", Toast.LENGTH_SHORT).show();
            }
        });

        //configureFragment(mRecyclerView, mSampleAdapter);

        List<Sample> samples = SampleAdapter.buildSamples();

        //displayItems(samples);

        return contentView;
    }

/*    @Override
    public String sortSectionMethod() {
        return null;
    }*/
}
