package net.ginteam.carmen.view.adapter.category;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.category.CategoryModel;

/**
 * Created by Eugene on 12/25/16.
 */

public class CategoryItemViewHolder extends RecyclerView.ViewHolder {

    private View mView;

    private TextView mTextViewName;
    private TextView mTextViewItemsCount;
    private ImageView mImageViewIcon;

    public CategoryItemViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        mTextViewName = (TextView) itemView.findViewById(R.id.text_view_category_name);
        mTextViewItemsCount = (TextView) itemView.findViewById(R.id.text_view_category_items_count);
        mImageViewIcon = (ImageView) itemView.findViewById(R.id.image_view_category_icon);
    }

    public TextView getTextViewName() {
        return mTextViewName;
    }

    public TextView getTextViewItemsCount() {
        return mTextViewItemsCount;
    }

    public ImageView getImageViewIcon() {
        return mImageViewIcon;
    }

    public void setOnCategoryClickListener(final CategoryModel forCategory, final OnCategoryItemClickListener listener) {
        if (listener == null) {
            return;
        }

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCategoryItemClick(forCategory);
            }
        });
    }

    public interface OnCategoryItemClickListener {

        void onCategoryItemClick(CategoryModel category);

    }

}
