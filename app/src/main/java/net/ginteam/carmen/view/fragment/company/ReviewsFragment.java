package net.ginteam.carmen.view.fragment.company;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.Rating;
import net.ginteam.carmen.view.adapter.ReviewsAdapter;
import net.ginteam.carmen.view.fragment.BaseFetchingFragment;

import java.util.ArrayList;
import java.util.List;

public class ReviewsFragment extends BaseFetchingFragment {

    private static final String RATING_ARG = "rating";

    private List<Rating> mRatingList;

    private RecyclerView mRecyclerViewReviews;
    private ReviewsAdapter mReviewsAdapter;

    public ReviewsFragment() {
    }

    public static ReviewsFragment newInstance(List<Rating> ratingList) {
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
            mRatingList = (ArrayList<Rating>) getArguments().getSerializable(RATING_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflateBaseFragment(R.layout.fragment_reviews, inflater, container, savedInstanceState);

        updateDependencies();
        mReviewsAdapter = new ReviewsAdapter(getContext(), mRatingList);
        mRecyclerViewReviews.setAdapter(mReviewsAdapter);

        return mRootView;
    }

    private void updateDependencies() {
        mRecyclerViewReviews = (RecyclerView) mRootView.findViewById(R.id.recycler_view_reviews);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewReviews.setLayoutManager(layoutManager);
    }
}
