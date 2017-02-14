package net.ginteam.carmen.view.adapter.category;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.category.CategoryModel;

import java.util.List;

/**
 * Created by Eugene on 12/25/16.
 */

public class CategoryRecyclerListAdapter extends RecyclerView.Adapter <CategoryItemViewHolder> {

    private Context mContext;
    private boolean mIsDialog;
    private List <CategoryModel> mCategories;

    private CategoryItemViewHolder.OnCategoryItemClickListener mCategoryItemClickListener;

    public CategoryRecyclerListAdapter(Context context, List <CategoryModel> categories, boolean isDialog) {
        mContext = context;
        mIsDialog = isDialog;
        mCategories = categories;
    }

    public void setOnCategoryClickListener(CategoryItemViewHolder.OnCategoryItemClickListener listener) {
        mCategoryItemClickListener = listener;
    }

    @Override
    public CategoryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View categoryItemView = LayoutInflater
                .from(parent.getContext())
                .inflate(mIsDialog ? R.layout.list_item_category_dialog :
                        R.layout.list_item_category, parent, false);
        return new CategoryItemViewHolder(categoryItemView);
    }

    @Override
    public void onBindViewHolder(CategoryItemViewHolder holder, int position) {
        CategoryModel currentCategory = mCategories.get(position);
        holder.getTextViewName().setText(currentCategory.getName());
        holder.getTextViewItemsCount().setText(
                mContext
                        .getResources()
                        .getQuantityString(
                                R.plurals.category_items_count_string,
                                currentCategory.getCompaniesCount(),
                                currentCategory.getCompaniesCount()
                        ));

        if (mCategoryItemClickListener == null) {
            Log.e("CategoryRecyclerAdapter", "OnCategoryClickListener does not set!");
            return;
        }
        holder.setOnCategoryClickListener(currentCategory, mCategoryItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

}
