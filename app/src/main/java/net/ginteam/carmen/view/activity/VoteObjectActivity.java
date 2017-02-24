package net.ginteam.carmen.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.company.VoteObjectContract;
import net.ginteam.carmen.model.Rating;
import net.ginteam.carmen.presenter.company.VoteObjectPresenter;
import net.ginteam.carmen.provider.auth.AuthProvider;
import net.ginteam.carmen.view.activity.company.CompanyDetailActivity;
import net.ginteam.carmen.view.custom.FilterEditText;
import net.ginteam.carmen.view.custom.rating.CarmenRatingView;

public class VoteObjectActivity extends ToolbarActivity implements VoteObjectContract.View {

    private VoteObjectContract.Presenter mPresenter;

    private String mCompanyName;
    private Rating mRating;

    private CarmenRatingView mRatingView;
    private CarmenRatingView mRatingViewPrice;
    private TextView mTextViewRatingPrice;
    private TextView mTextViewRating;
    private FilterEditText mEditTextName;
    private FilterEditText mEditTextTitle;
    private FilterEditText mEditTextReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_object);

        receiveArguments();
        updateDependencies();

        mPresenter = new VoteObjectPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vote_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_send:
                sendReview();
                break;
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void receiveArguments() {
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            mRating = (Rating) arguments.getSerializable(CompanyDetailActivity.RATING_ARGUMENT);

            mCompanyName = arguments.getString(CompanyDetailActivity.COMPANY_NAME_ARGUMENT);
        }
    }

    private void updateDependencies() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button);

        mTextViewRatingPrice = (TextView) findViewById(R.id.text_view_price_rating);
        mRatingViewPrice = (CarmenRatingView) findViewById(R.id.rating_view_company_price);
        mRatingViewPrice.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating < 1) {
                    mRatingViewPrice.setRating(1);
                    return;
                }
                int index = (int) rating - 1;
                mTextViewRatingPrice
                        .setText(getResources().getTextArray(R.array.rating_price_array)[index]);
            }
        });

        mTextViewRating = (TextView) findViewById(R.id.text_view_rating);
        mRatingView = (CarmenRatingView) findViewById(R.id.rating_view_company);
        mRatingView.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating < 1) {
                    mRatingView.setRating(1);
                    return;
                }
                int index = (int) rating - 1;
                mTextViewRating
                        .setText(getResources().getTextArray(R.array.rating_array)[index]);
            }
        });
        mRatingView.setRating(mRating.getTotalRating());
        mRatingViewPrice.setRating(1);

        mEditTextName = (FilterEditText) findViewById(R.id.filter_edit_text_name);
        mEditTextTitle = (FilterEditText) findViewById(R.id.filter_edit_text_title);
        mEditTextReview = (FilterEditText) findViewById(R.id.filter_edit_text_review);

        if (AuthProvider.getInstance().getCurrentCachedUser() != null) {
            mEditTextName.setText(AuthProvider.getInstance().getCurrentCachedUser().getName());
        }

        findViewById(R.id.button_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReview();
            }
        });

        setTitle("Отзывы");
        setSubtitle(mCompanyName);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(String message) {
    }

    @Override
    public void onSuccess() {
        finish();
    }

    private void sendReview() {
        mRating.setTotalRating(mRatingView.getRating());
        mRating.setPriceRel(mRatingViewPrice.getRating());
        if (!mEditTextName.getText().isEmpty()) {
            mRating.setDisplayName(mEditTextName.getText());
        }
        mRating.setTitle(mEditTextTitle.getText());
        mRating.setText(mEditTextReview.getText());
        mPresenter.sendReview(mRating);
    }
}
