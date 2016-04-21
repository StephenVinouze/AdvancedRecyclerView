package com.github.stephenvinouze.advancedrecyclerviewsample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.stephenvinouze.advancedrecyclerview.adapters.RecyclerAdapter;
import com.github.stephenvinouze.advancedrecyclerview.callbacks.ClickCallback;
import com.github.stephenvinouze.advancedrecyclerviewsample.adapters.SampleAdapter;
import com.github.stephenvinouze.advancedrecyclerviewsample.models.Sample;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Stephen Vinouze on 06/11/2015.
 */
public class GestureRecyclerFragment extends AbstractRecyclerFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = super.onCreateView(inflater, container, savedInstanceState);

        mSampleAdapter.setChoiceMode(RecyclerAdapter.ChoiceMode.MULTIPLE_CHOICE);
        mSampleAdapter.setClickCallback(new ClickCallback() {
            @Override
            public void onItemClick(int position) {
                Sample sample = mSampleAdapter.getItemAt(position);
                Toast.makeText(getActivity(), "Item clicked : " + sample.getName() + " (" + mSampleAdapter.getSelectedItemViewCount() + " selected)", Toast.LENGTH_SHORT).show();
            }
        });
/*        configureFragment(mRecyclerView, mSampleAdapter, new SampleSectionAdapter(getActivity()));
        setGestureCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, new GestureCallback() {
            @Override
            public boolean onMove(int fromPosition, int toPosition) {
                Toast.makeText(getActivity(), "Item selected : " + mSampleAdapter.getSelectedItemViews(), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(int position, int direction) {
                Toast.makeText(getActivity(), "Item selected : " + mSampleAdapter.getSelectedItemViews(), Toast.LENGTH_SHORT).show();
            }
        });*/

        List<Sample> samples = SampleAdapter.buildSamples();

        Collections.sort(samples, new Comparator<Sample>() {
            @Override
            public int compare(Sample lhs, Sample rhs) {
                return lhs.getRate() - rhs.getRate();
            }
        });

        //displayItems(samples);

        return contentView;
    }

}
