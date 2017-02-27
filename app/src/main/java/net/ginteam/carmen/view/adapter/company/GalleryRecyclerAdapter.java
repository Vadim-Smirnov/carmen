package net.ginteam.carmen.view.adapter.company;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import net.ginteam.carmen.R;

import java.util.List;

/**
 * Created by vadik on 06.02.17.
 */

public class GalleryRecyclerAdapter extends RecyclerView.Adapter<GalleryItemViewHolder> {

    private Context mContext;
    private List<String> mImages;

    public GalleryRecyclerAdapter(Context context, List<String> imageUrls) {
        mContext = context;
        mImages = imageUrls;
    }

    @Override
    public GalleryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View galleryItemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_gallery, parent, false);
        return new GalleryItemViewHolder(galleryItemView);
    }

    @Override
    public void onBindViewHolder(GalleryItemViewHolder holder, int position) {
        String imageUrl = mImages.get(position);
        if (!imageUrl.isEmpty()) {
            Picasso.with(mContext).load(imageUrl).into(holder.getImageViewGallery());
        }
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }
}
