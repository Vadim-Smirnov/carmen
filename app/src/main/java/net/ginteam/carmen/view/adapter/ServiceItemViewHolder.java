package net.ginteam.carmen.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.ginteam.carmen.R;

/**
 * Created by vadik on 26.01.17.
 */

public class ServiceItemViewHolder extends RecyclerView.ViewHolder{

    private View mView;

    private TextView mTextViewServiceName;

    public ServiceItemViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        mTextViewServiceName = (TextView) mView.findViewById(R.id.text_view_service_name);

    }

    public TextView getTextViewServiceName() {
        return mTextViewServiceName;
    }

}
