package net.ginteam.carmen.view.adapter.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.filter.FilterOptionModel;

import java.util.List;

/**
 * Created by Eugene on 1/13/17.
 */

public class FilterOptionsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<FilterOptionModel> mOptionsList;

    public FilterOptionsListAdapter(Context context, List<FilterOptionModel> options) {
        mContext = context;
        mOptionsList = options;
    }

    @Override
    public int getCount() {
        return mOptionsList.size();
    }

    @Override
    public FilterOptionModel getItem(int i) {
        return mOptionsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FilterItemViewHolder viewHolder;
        FilterOptionModel currentFilterOption = mOptionsList.get(i);

        if (view == null) {
            view = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.list_item_filter_option, viewGroup, false);
            viewHolder = new FilterItemViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (FilterItemViewHolder) view.getTag();
        }

        viewHolder.getTextViewOption().setText(currentFilterOption.getValue());

        return view;
    }
}
