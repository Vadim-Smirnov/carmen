package net.ginteam.carmen.view.adapter.company.map;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.model.company.MapCompanyModel;
import net.ginteam.carmen.utils.DisplayUtils;
import net.ginteam.carmen.view.fragment.company.CompanyListFragment;

import java.util.List;
import java.util.Locale;

/**
 * Created by Eugene on 1/25/17.
 */

public class CompanyRecyclerListAdapter extends RecyclerView.Adapter<CompanyItemViewHolder> {

    private static final int VISIBLE_ITEMS = 2;

    private Context mContext;
    private List<MapCompanyModel> mCompanies;
    private int mSelectionIndex;

    private CompanyItemViewHolder.OnCompanyItemClickListener mCompanyItemClickListener;
    private net.ginteam.carmen.view.adapter.company.CompanyItemViewHolder.OnAddToFavoritesClickListener mOnAddToFavoritesClickListener;

    public CompanyRecyclerListAdapter(Context context, List<MapCompanyModel> companies) {
        mContext = context;
        mCompanies = companies;
        mSelectionIndex = -1;
    }

    public void setOnCompanyItemClickListener(CompanyItemViewHolder.OnCompanyItemClickListener listener) {
        mCompanyItemClickListener = listener;
    }

    public void setOnAddToFavoritesClickListener(net.ginteam.carmen.view.adapter.company.CompanyItemViewHolder.OnAddToFavoritesClickListener onAddToFavoritesClickListener) {
        mOnAddToFavoritesClickListener = onAddToFavoritesClickListener;
    }

    public int selectItem(MapCompanyModel companyModel) {
        mSelectionIndex = mCompanies.indexOf(companyModel);
        notifyDataSetChanged();

        return mSelectionIndex;
    }

    @Override
    public CompanyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View companyItemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_company_map, parent, false);

        companyItemView.setLayoutParams(
                new RecyclerView.LayoutParams(calculateItemWidth(), ViewGroup.LayoutParams.WRAP_CONTENT)
        );

        return new CompanyItemViewHolder(companyItemView);
    }

    @Override
    public void onBindViewHolder(CompanyItemViewHolder holder, int position) {
        CompanyModel currentCompany = mCompanies.get(position);

        if (position == mSelectionIndex) {
            holder.getViewSelectionIndicator().setBackgroundColor(Color.BLACK);
        } else {
            holder.getViewSelectionIndicator().setBackgroundColor(Color.WHITE);
        }

        holder.getTextViewName().setText(currentCompany.getName());
        holder.getRatingViewRating().setRating(currentCompany.getRating());
        holder.getTextViewPrice().setText(
                String.format(Locale.getDefault(),
                        mContext.getString(R.string.company_price),
                        currentCompany.getPrice())
        );

        holder.getImageViewPhoto().getLayoutParams().height = calculateImageSize();
        holder.getImageViewPhoto().requestLayout();
        holder.itemView.requestLayout();

        holder.getImageButtonAddToFavorite().setImageResource(currentCompany.isFavorite() ?
                R.drawable.ic_company_favorite_enable : R.drawable.ic_company_favorite_disable);

        if (mCompanyItemClickListener == null) {
            Log.e("CompanyListAdapter", "OnCompanyItemClickListener does not set!");
            return;
        }
        holder.setOnCompanyItemClickListener(currentCompany, mCompanyItemClickListener);

        if (mOnAddToFavoritesClickListener == null) {
            Log.e("CompanyListAdapter", "OnAddToFavoritesClickListener does not set!");
            return;
        }
        holder.setOnAddToFavoritesClickListener(currentCompany, mOnAddToFavoritesClickListener);
    }

    @Override
    public int getItemCount() {
        return mCompanies.size();
    }

    private int calculateItemWidth() {
        Point screenSize = DisplayUtils.getScreenSize(mContext);
        int itemSpacing = (int) mContext.getResources().getDimension(R.dimen.company_item_spacing);
        return (screenSize.x / 2) - (VISIBLE_ITEMS * itemSpacing);
    }

    private int calculateImageSize() {
        Point screenSize = DisplayUtils.getScreenSize(mContext);
        return calculateItemWidth() * 64 / 100;
    }

}
