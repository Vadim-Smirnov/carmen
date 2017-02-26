//package net.ginteam.carmen.view.activity;
//
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.view.MenuItem;
//
//import net.ginteam.carmen.R;
//import net.ginteam.carmen.model.Rating;
//import net.ginteam.carmen.view.adapter.ReviewsAdapter;
//import net.ginteam.carmen.view.fragment.company.ReviewsFragment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static net.ginteam.carmen.CarmenApplication.getContext;
//
//public class AllReviewsActivity extends ToolbarActivity {
//
//    private List<Rating> mRatings;
//
//    private RecyclerView mRecyclerViewReviews;
//    private ReviewsAdapter mReviewsAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_all_reviews);
//
//        receiveArguments();
//        updateDependencies();
//        showReviews();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                super.onBackPressed();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void receiveArguments() {
//        Bundle arguments = getIntent().getExtras();
//        if (arguments != null) {
//            mRatings = (ArrayList<Rating>) arguments.getSerializable(ReviewsFragment.RATING_ARG);
//        }
//    }
//
//    private void updateDependencies() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button);
//
//        setTitle(getResources().getString(R.string.all_review_title));
//        setSubtitle(String.format("(%s %s)", getResources().getString(R.string.all_review_subtitle),
//                mRatings.size()));
//
//        mRecyclerViewReviews = (RecyclerView) findViewById(R.id.recycler_view_reviews);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        mRecyclerViewReviews.setLayoutManager(layoutManager);
//    }
//
//    private void showReviews() {
//        if (mRatings != null) {
//            mReviewsAdapter = new ReviewsAdapter(getContext(), mRatings, true);
//            mRecyclerViewReviews.setAdapter(mReviewsAdapter);
//        }
//    }
//
//}
