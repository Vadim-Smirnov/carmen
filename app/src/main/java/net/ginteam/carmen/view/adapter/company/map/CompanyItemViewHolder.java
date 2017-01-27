package net.ginteam.carmen.view.adapter.company.map;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.view.custom.rating.RatingView;

/**
 * Created by Eugene on 1/25/17.
 */

public class CompanyItemViewHolder extends RecyclerView.ViewHolder {

    private View mView;

    private View mViewSelectionIndicator;
    private ImageView mImageViewPhoto;
    private ImageButton mImageButtonAddToFavorite;
    private TextView mTextViewName;
    private RatingView mRatingViewRating;
    private TextView mTextViewPrice;

    public CompanyItemViewHolder(View itemView) {
        super(itemView);

        mView = itemView;

        mViewSelectionIndicator = itemView.findViewById(R.id.view_selection_indicator);
        mImageViewPhoto = (ImageView) itemView.findViewById(R.id.image_view_company_photo);
        mImageButtonAddToFavorite = (ImageButton) itemView.findViewById(R.id.image_button_company_favorite);
        mTextViewName = (TextView) itemView.findViewById(R.id.text_view_company_name);
        mRatingViewRating = (RatingView) itemView.findViewById(R.id.rating_view_company);
        mTextViewPrice = (TextView) itemView.findViewById(R.id.text_view_company_price);
    }

    public View getViewSelectionIndicator() {
        return mViewSelectionIndicator;
    }

    public ImageView getImageViewPhoto() {
        return mImageViewPhoto;
    }

    public ImageButton getImageButtonAddToFavorite() {
        return mImageButtonAddToFavorite;
    }

    public TextView getTextViewName() {
        return mTextViewName;
    }

    public RatingView getRatingViewRating() {
        return mRatingViewRating;
    }

    public TextView getTextViewPrice() {
        return mTextViewPrice;
    }

    public void setOnAddToFavoritesClickListener(final CompanyModel company, final net.ginteam.carmen.view.adapter.company.CompanyItemViewHolder.OnAddToFavoritesClickListener listener) {
        mImageButtonAddToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddToFavoritesClick(company);
            }
        });
    }

    public void setOnCompanyItemClickListener(final CompanyModel forCompany, final OnCompanyItemClickListener listener) {
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCompanyItemClick(forCompany);
            }
        });
    }

    public interface OnCompanyItemClickListener {

        void onCompanyItemClick(CompanyModel company);

    }

}
