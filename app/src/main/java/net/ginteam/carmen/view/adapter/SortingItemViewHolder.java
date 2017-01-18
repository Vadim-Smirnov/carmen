package net.ginteam.carmen.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import net.ginteam.carmen.R;

/**
 * Created by vadik on 19.01.17.
 */

public class SortingItemViewHolder extends RecyclerView.ViewHolder{

    private View mView;
    private CheckBox mCheckBoxSorting;

    public SortingItemViewHolder(View view) {
        super(view);
        mView = view;
        mCheckBoxSorting = (CheckBox) mView.findViewById(R.id.checkbox_sorting);

    }

    public CheckBox getCheckBoxSorting() {
        return mCheckBoxSorting;
    }

}
