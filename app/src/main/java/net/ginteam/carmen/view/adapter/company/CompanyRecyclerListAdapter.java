package net.ginteam.carmen.view.adapter.company;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.utils.DisplayUtils;
import net.ginteam.carmen.view.fragment.company.CompanyListFragment;

import java.util.List;

import static net.ginteam.carmen.view.adapter.company.CompanyRecyclerListAdapter.ITEM_VIEW_TYPE.COMPANY;
import static net.ginteam.carmen.view.adapter.company.CompanyRecyclerListAdapter.ITEM_VIEW_TYPE.LOADING;

/**
 * Created by Eugene on 12/27/16.
 */

public class CompanyRecyclerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    enum ITEM_VIEW_TYPE {
        COMPANY,
        LOADING
    }

    private static final int VISIBLE_ITEMS = 3;

    private Context mContext;
    private CompanyListFragment.COMPANY_LIST_TYPE mType;
    private List<CompanyModel> mCompanies;

    private boolean mIsLoadingIndicatorShow;

    private CompanyItemViewHolder.OnCompanyItemClickListener mCompanyItemClickListener;
    private CompanyItemViewHolder.OnAddToFavoritesClickListener mOnAddToFavoritesClickListener;

    public CompanyRecyclerListAdapter(Context context, List<CompanyModel> companies, CompanyListFragment.COMPANY_LIST_TYPE type) {
        mContext = context;
        mType = type;
        mCompanies = companies;
        mIsLoadingIndicatorShow = false;
    }

    public void setOnCompanyItemClickListener(CompanyItemViewHolder.OnCompanyItemClickListener listener) {
        mCompanyItemClickListener = listener;
    }

    public void setOnAddToFavoritesClickListener(CompanyItemViewHolder.OnAddToFavoritesClickListener onAddToFavoritesClickListener) {
        mOnAddToFavoritesClickListener = onAddToFavoritesClickListener;
    }

    public void addCompanies(List<CompanyModel> companies) {
        int insertPosition = mCompanies.size();
        mCompanies.addAll(companies);
        notifyItemRangeInserted(insertPosition, companies.size());
    }

    public void removeItem(CompanyModel companyModel) {
        int removeIndex = mCompanies.indexOf(companyModel);
        mCompanies.remove(removeIndex);
        notifyItemRemoved(removeIndex);
    }

    public void showLoading() {
        mIsLoadingIndicatorShow = true;
        mCompanies.add(new CompanyModel());
        notifyItemInserted(mCompanies.size() - 1);
    }

    public void hideLoading() {
        mIsLoadingIndicatorShow = false;

        int position = mCompanies.size() - 1;
        CompanyModel item = mCompanies.get(position);

        if (item != null) {
            mCompanies.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == COMPANY.ordinal()) {
            View companyItemView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(getListItemIdForType(mType), parent, false);

            companyItemView.setLayoutParams(
                    new RecyclerView.LayoutParams(calculateItemWidthForType(mType), ViewGroup.LayoutParams.WRAP_CONTENT)
            );

            return new CompanyItemViewHolder(companyItemView);
        } else {
            View loadingItemView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_item_progress, parent, false);
            return new CompanyLoadingViewHolder(loadingItemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) != COMPANY.ordinal()) {
            return;
        }

        CompanyModel currentCompany = mCompanies.get(position);
        final CompanyItemViewHolder companyViewHolder = (CompanyItemViewHolder) holder;

        companyViewHolder.getTextViewName().setText(currentCompany.getName());
        companyViewHolder.getTextViewAddress().setText(currentCompany.getAddress());
        companyViewHolder.getRatingViewRating().setRating(currentCompany.getRating());
        companyViewHolder.getTextViewDistance()
                .setText(currentCompany.getDistance() == 0 ? "" :
                        String.format("%.1f km", currentCompany.getDistance() / 1000));

        companyViewHolder.getImageViewPhoto().getLayoutParams().height =
                calculateImageSizeForType(mType);
        companyViewHolder.getImageViewPhoto().getLayoutParams().width =
                calculateImageSizeForType(mType);
        companyViewHolder.getImageViewPhoto().requestLayout();

        if (!currentCompany.getImageUrl().isEmpty()) {
            if (!currentCompany.getImageUrl().get(0).isEmpty())
            Picasso.with(mContext).load(currentCompany.getImageUrl().get(0)).placeholder( R.drawable.placeholder_animation).into(companyViewHolder.getImageViewPhoto());
        }

        companyViewHolder.getImageViewLocation().setVisibility(
                companyViewHolder.getTextViewDistance().getText().toString().isEmpty() ?
                        View.INVISIBLE : View.VISIBLE);

        companyViewHolder.getImageButtonAddToFavorite().setImageResource(currentCompany.isFavorite() ?
                R.drawable.ic_company_favorite_enable : R.drawable.ic_company_favorite_disable);


        companyViewHolder.getImageButtonAddToFavorite().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Like", Toast.LENGTH_SHORT).show();
            }
        });

        if (mCompanyItemClickListener == null) {
            Log.e("CompanyListAdapter", "OnCompanyItemClickListener does not set!");
            return;
        }
        companyViewHolder.setOnCompanyItemClickListener(currentCompany, position, mCompanyItemClickListener);

        if (mOnAddToFavoritesClickListener == null) {
            Log.e("CompanyListAdapter", "OnAddToFavoritesClickListener does not set!");
            return;
        }
        companyViewHolder.setOnAddToFavoritesClickListener(currentCompany, mOnAddToFavoritesClickListener);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mCompanies.size() - 1 && mIsLoadingIndicatorShow) ? LOADING.ordinal() : COMPANY.ordinal();
    }

    @Override
    public int getItemCount() {
        return mCompanies.size();
    }

    private int getListItemIdForType(CompanyListFragment.COMPANY_LIST_TYPE type) {
        switch (type) {
            case HORIZONTAL:
                return R.layout.list_item_company_horizontal;
            default:
                return R.layout.list_item_company_vertical;
        }
    }

    private int calculateItemWidthForType(CompanyListFragment.COMPANY_LIST_TYPE type) {
        int itemWidth = RecyclerView.LayoutParams.MATCH_PARENT;
        if (type == CompanyListFragment.COMPANY_LIST_TYPE.HORIZONTAL) {
            Point screenSize = DisplayUtils.getScreenSize(mContext);
            int itemSpacing = (int) mContext.getResources().getDimension(R.dimen.company_item_spacing);
            itemWidth = (screenSize.x / 2) - (VISIBLE_ITEMS * itemSpacing);
        }
        return itemWidth;
    }

    private int calculateImageSizeForType(CompanyListFragment.COMPANY_LIST_TYPE type) {
        int itemWidth = RecyclerView.LayoutParams.MATCH_PARENT;
        Point screenSize = DisplayUtils.getScreenSize(mContext);
        if (type == CompanyListFragment.COMPANY_LIST_TYPE.HORIZONTAL) {
            int itemSpacing = (int) mContext.getResources().getDimension(R.dimen.company_item_spacing);
            itemWidth = (screenSize.x / 2) - (VISIBLE_ITEMS * itemSpacing);
        } else {
            itemWidth = 43 * screenSize.x / 100;
        }
        return itemWidth;
    }

}
