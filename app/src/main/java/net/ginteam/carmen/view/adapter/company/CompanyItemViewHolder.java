package net.ginteam.carmen.view.adapter.company;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.view.custom.rating.CarmenRatingView;

/**
 * Created by Eugene on 12/27/16.
 */

public class CompanyItemViewHolder extends RecyclerView.ViewHolder {

    private View mView;

    private ImageView mImageViewPhoto;
    private TextView mTextViewName;
    private TextView mTextViewAddress;
    private CarmenRatingView mRatingViewRating;
    private ImageButton mImageButtonAddToFavorite;
    private TextView mTextViewDistance;
    private ImageView mImageViewLocation;

    public CompanyItemViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        mImageViewPhoto = (ImageView) itemView.findViewById(R.id.image_view_company_photo);
        mImageViewLocation = (ImageView) itemView.findViewById(R.id.image_view_company_location);
        mTextViewName = (TextView) itemView.findViewById(R.id.text_view_company_name);
        mTextViewAddress = (TextView) itemView.findViewById(R.id.text_view_company_address);
        mRatingViewRating = (CarmenRatingView) itemView.findViewById(R.id.rating_view_company);
        mImageButtonAddToFavorite = (ImageButton) itemView.findViewById(R.id.image_button_company_favorite);
        mTextViewDistance = (TextView) itemView.findViewById(R.id.text_view_company_distance);
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

    public CarmenRatingView getRatingViewRating() {
        return mRatingViewRating;
    }

    public ImageButton getImageButtonAddToFavorite() {
        return mImageButtonAddToFavorite;
    }

    public TextView getTextViewDistance() {
        return mTextViewDistance;
    }



    public ImageView getImageViewLocation() {
        return mImageViewLocation;
    }

    public void setOnAddToFavoritesClickListener(final CompanyModel company, final OnAddToFavoritesClickListener listener) {
        mImageButtonAddToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAddToFavoritesClick(company);
            }
        });
    }

    public void setOnCompanyItemClickListener(final CompanyModel forCompany, final int position, final OnCompanyItemClickListener listener) {
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCompanyItemClick(forCompany, position);
            }
        });
    }

    public interface OnCompanyItemClickListener {

        void onCompanyItemClick(CompanyModel company, int position);

    }

    public interface OnAddToFavoritesClickListener {

        void onAddToFavoritesClick(CompanyModel company);

    }

}
