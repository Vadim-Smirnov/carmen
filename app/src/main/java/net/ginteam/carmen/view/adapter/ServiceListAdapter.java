package net.ginteam.carmen.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.kotlin.model.ServiceModel;

import java.util.List;

/**
 * Created by vadik on 26.01.17.
 */

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceItemViewHolder> {

    private Context mContext;

    private int mVisibleServiceCount;

    private List<ServiceModel> mServices;

    public ServiceListAdapter(Context context, int visibleServiceCount, List<ServiceModel> services) {
        mContext = context;
        mVisibleServiceCount = visibleServiceCount;
        mServices = services;
    }

    @Override
    public ServiceItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View serviceItemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_service, parent, false);
        return new ServiceItemViewHolder(serviceItemView);
    }

    @Override
    public void onBindViewHolder(ServiceItemViewHolder holder, int position) {
        ServiceModel currentService = mServices.get(position);
        holder.getTextViewServiceName().setText(currentService.getName());
    }

    @Override
    public int getItemCount() {
        return mVisibleServiceCount;
    }

    public void setVisibleServiceCount(int visibleServiceCount) {
        mVisibleServiceCount = visibleServiceCount;
    }

}
