package net.ginteam.carmen.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.SortingModel;
import net.ginteam.carmen.view.adapter.city.CityItemViewHolder;

import java.util.List;

/**
 * Created by vadik on 19.01.17.
 */

public class SortingListAdapter extends RecyclerView.Adapter <SortingItemViewHolder> {

    private Context mContext;

    private List<SortingModel> mSortingList;

    public SortingListAdapter(Context context, List<SortingModel> sortingList) {
        mContext = context;
        mSortingList = sortingList;
    }

    @Override
    public SortingItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View sortingItemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_sorting, parent, false);
        return new SortingItemViewHolder(sortingItemView);
    }

    @Override
    public void onBindViewHolder(SortingItemViewHolder holder, int position) {
        SortingModel currentSorting = mSortingList.get(position);

        holder.getCheckBoxSorting().setText(currentSorting.getName());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mSortingList.size();
    }

}
