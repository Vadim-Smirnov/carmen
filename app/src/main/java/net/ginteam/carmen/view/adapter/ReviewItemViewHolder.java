package net.ginteam.carmen.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.ginteam.carmen.R;
import net.ginteam.carmen.view.custom.rating.RatingView;

/**
 * Created by vadik on 25.01.17.
 */

public class ReviewItemViewHolder extends RecyclerView.ViewHolder {

    private View mView;

    private TextView mTextViewReviewTitle;
    private RatingView mRatingView;
    private TextView mTextViewDate;
    private TextView mTextViewUserName;
    private TextView mTextViewReviewText;


    public ReviewItemViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        mRatingView = (RatingView) mView.findViewById(R.id.rating_view_company);
        mTextViewReviewTitle = (TextView) mView.findViewById(R.id.text_view_review_title);
        mTextViewDate = (TextView) mView.findViewById(R.id.text_view_date);
        mTextViewReviewText = (TextView) mView.findViewById(R.id.text_view_review_text);
        mTextViewUserName = (TextView) mView.findViewById(R.id.text_view_user_name);
    }

    public TextView getTextViewReviewTitle() {
        return mTextViewReviewTitle;
    }

    public RatingView getRatingView() {
        return mRatingView;
    }

    public TextView getTextViewDate() {
        return mTextViewDate;
    }

    public TextView getTextViewUserName() {
        return mTextViewUserName;
    }

    public TextView getTextViewReviewText() {
        return mTextViewReviewText;
    }
}
