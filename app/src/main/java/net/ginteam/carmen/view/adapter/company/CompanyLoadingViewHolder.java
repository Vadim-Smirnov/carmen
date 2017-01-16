package net.ginteam.carmen.view.adapter.company;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import net.ginteam.carmen.R;

/**
 * Created by Eugene on 1/16/17.
 */

public class CompanyLoadingViewHolder extends RecyclerView.ViewHolder {

    private ProgressBar mProgressBar;

    public CompanyLoadingViewHolder(View itemView) {
        super(itemView);
        mProgressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar_item);
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

}
