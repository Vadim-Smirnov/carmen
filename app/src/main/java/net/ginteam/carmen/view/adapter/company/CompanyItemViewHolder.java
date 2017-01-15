package net.ginteam.carmen.view.adapter.company;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.view.custom.rating.RatingView;

/**
 * Created by Eugene on 12/27/16.
 */

public class CompanyItemViewHolder extends RecyclerView.ViewHolder {

    private View mView;

    private ImageView mImageViewPhoto;
    private TextView mTextViewName;
    private TextView mTextViewAddress;
    private RatingView mRatingViewRating;
    private ImageButton mImageButtonAddToFavorite;
    private TextView mTextViewDistance;
    private TextView mTextViewPrice;

    public CompanyItemViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        mImageViewPhoto = (ImageView) itemView.findViewById(R.id.image_view_company_photo);
        mTextViewName = (TextView) itemView.findViewById(R.id.text_view_company_name);
        mTextViewAddress = (TextView) itemView.findViewById(R.id.text_view_company_address);
        mRatingViewRating = (RatingView) itemView.findViewById(R.id.rating_view_company);
        mImageButtonAddToFavorite = (ImageButton) itemView.findViewById(R.id.image_button_company_favorite);
        mTextViewDistance = (TextView) itemView.findViewById(R.id.text_view_company_distance);
        mTextViewPrice = (TextView) itemView.findViewById(R.id.text_view_company_price);
    }

    public ImageView getImageViewPhoto() {
        return mImageViewPhoto;
    }

    public TextView getTextViewName() {
        return mTextViewName;
    }

    public TextView getTextViewAddress() {
        return mTextViewAddress;
    }

    public RatingView getRatingViewRating() {
        return mRatingViewRating;
    }

    public ImageButton getImageButtonAddToFavorite() {
        return mImageButtonAddToFavorite;
    }

    public TextView getTextViewDistance() {
        return mTextViewDistance;
    }

    public TextView getTextViewPrice() {
        return mTextViewPrice;
    }

    public void setOnAddToFavoritesClickListener(final CompanyModel company, final OnAddToFavoritesClickListener listener) {
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

    public interface OnAddToFavoritesClickListener {

        void onAddToFavoritesClick(CompanyModel company);

    }

}
