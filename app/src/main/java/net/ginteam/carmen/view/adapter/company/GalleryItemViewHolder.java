package net.ginteam.carmen.view.adapter.company;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import net.ginteam.carmen.R;

/**
 * Created by vadik on 06.02.17.
 */

public class GalleryItemViewHolder extends RecyclerView.ViewHolder {

    private View mView;

    private ImageView mImageViewGallery;

    public GalleryItemViewHolder(View itemView) {
        super(itemView);
        mView = itemView;

        mImageViewGallery = (ImageView) mView.findViewById(R.id.image_view_gallery);
    }

    public ImageView getImageViewGallery() {
        return mImageViewGallery;
    }

}
