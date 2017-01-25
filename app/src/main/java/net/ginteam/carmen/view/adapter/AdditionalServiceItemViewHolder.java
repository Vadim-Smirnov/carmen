package net.ginteam.carmen.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import net.ginteam.carmen.R;

/**
 * Created by vadik on 24.01.17.
 */

public class AdditionalServiceItemViewHolder extends RecyclerView.ViewHolder {

    private View mView;

    private ImageView mImageViewAdditionalService;

    public AdditionalServiceItemViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        mImageViewAdditionalService = (ImageView) mView.findViewById(R.id.image_view_additional_service);
    }

    public ImageView getImageViewAdditionalService() {
        return mImageViewAdditionalService;
    }
}
