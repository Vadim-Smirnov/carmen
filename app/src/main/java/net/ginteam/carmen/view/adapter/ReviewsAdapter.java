package net.ginteam.carmen.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.Rating;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by vadik on 25.01.17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter <ReviewItemViewHolder> {

    private Context mContext;

    private List<Rating> mReviews;

    public ReviewsAdapter(Context context, List<Rating> reviews) {
        mContext = context;
        mReviews = reviews;
    }

    @Override
    public ReviewItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View reviewItemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_review, parent, false);
        return new ReviewItemViewHolder(reviewItemView);
    }

    @Override
    public void onBindViewHolder(ReviewItemViewHolder holder, int position) {
        Rating currentReview = mReviews.get(position);

        holder.getRatingView().setRating(currentReview.getTotalRating());
        holder.getTextViewReviewTitle().setText(currentReview.getTitle());
        holder.getTextViewReviewText().setText(currentReview.getText());
        holder.getTextViewUserName().setText(currentReview.getDisplayName());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(currentReview.getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("dd.MM.yyyy");
        holder.getTextViewDate().setText(format.format(date));
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }
}
