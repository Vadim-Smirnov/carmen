package net.ginteam.carmen.view.fragment.company;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.kotlin.model.RatingModel;
import net.ginteam.carmen.kotlin.view.activity.company.CompanyRatingsActivity;
import net.ginteam.carmen.view.adapter.ReviewsAdapter;
import net.ginteam.carmen.view.fragment.BaseFetchingFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReviewsFragment extends BaseFetchingFragment {

    public static final String RATING_ARG = "rating";

    private List<RatingModel> mRatingList;

    private RecyclerView mRecyclerViewReviews;
    private ReviewsAdapter mReviewsAdapter;

    public ReviewsFragment() {
    }

    public static ReviewsFragment newInstance(List<RatingModel> ratingList) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle args = new Bundle();
        args.putSerializable(RATING_ARG, new ArrayList<>(ratingList));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRatingList = (ArrayList<RatingModel>) getArguments().getSerializable(RATING_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflateBaseFragment(R.layout.fragment_reviews, inflater, container, savedInstanceState);

        updateDependencies();

        if (mRatingList != null) {
            mReviewsAdapter = new ReviewsAdapter(getContext(), mRatingList, false);
            mRecyclerViewReviews.setAdapter(mReviewsAdapter);
        }

        return mRootView;
    }

    private void updateDependencies() {
        mRecyclerViewReviews = (RecyclerView) mRootView.findViewById(R.id.recycler_view_reviews);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewReviews.setLayoutManager(layoutManager);
        mRootView.findViewById(R.id.button_all_reviews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllReviews();
            }
        });
    }

    private void showAllReviews() {
        Intent intent = new Intent(getContext(), CompanyRatingsActivity.class);
        intent.putExtra(CompanyRatingsActivity.COMPANY_RATINGS_ARGUMENT, (Serializable) mRatingList);
        startActivity(intent);
    }
}
