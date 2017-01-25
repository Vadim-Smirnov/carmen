package net.ginteam.carmen.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.company.Comfort;

import java.util.List;

/**
 * Created by vadik on 24.01.17.
 */

public class AdditionalServiceListAdapter extends RecyclerView.Adapter <AdditionalServiceItemViewHolder> {

    private Context mContext;
    private List<Comfort> mComforts;

    public AdditionalServiceListAdapter(Context context, List<Comfort> comforts) {
        mContext = context;
        mComforts = comforts;
    }

    @Override
    public AdditionalServiceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View additionalServiceItemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_additional_service, parent, false);
        return new AdditionalServiceItemViewHolder(additionalServiceItemView);
    }

    @Override
    public void onBindViewHolder(AdditionalServiceItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mComforts.size();
    }
}
