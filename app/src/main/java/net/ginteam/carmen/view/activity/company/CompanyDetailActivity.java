package net.ginteam.carmen.view.activity.company;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.company.CompanyDetailContract;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.presenter.company.CompanyDetailPresenter;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.company.CompaniesProvider;
import net.ginteam.carmen.view.activity.ToolbarActivity;
import net.ginteam.carmen.view.custom.rating.RatingView;

import java.util.List;

public class CompanyDetailActivity extends ToolbarActivity implements CompanyDetailContract.View {

    public static final String COMPANY_ARGUMENT = "company";

    private int mCompanyId;

    private CompanyDetailContract.Presenter mPresenter;

    private ProgressBar mProgressBar;
    private NestedScrollView mNestedScrollView;
    private TextView mTextViewCompanyName;
    private TextView mTextViewCategory;
    private RatingView mRatingViewCompanyRating;
    private TextView mTextViewReviewCount;
    private TextView mTextViewDistance;
    private Button mButtonShowOnMap;
    private TextView mTextViewWorkTime;
    private TextView mTextViewAddress;
    private Button mButtonCash;
    private Button mButtonCashLess;
    private Button mButtonAllReviews;
    private ImageView mImageViewMap;
    private Button mButtonOpenNavigate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_company_detail);

        receiveArguments();
        initialize();

        mPresenter = new CompanyDetailPresenter(this);
        mPresenter.fetchCompanyDetail(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void receiveArguments() {
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            mCompanyId = arguments.getInt(COMPANY_ARGUMENT, 0);
            Toast.makeText(this, "Receive ID: " + mCompanyId, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showLoading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        mNestedScrollView.setVisibility(isLoading ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showCompanyDetail(CompanyModel companyModel) {
        showLoading(false);
        mTextViewCompanyName.setText(companyModel.getName());
        mTextViewCategory.setText(companyModel.getCategory().getData().get(0).getName());
//        mTextViewWorkTime.setText(companyModel.getDetail().getData().getWorkTime().get(0));
        mRatingViewCompanyRating.setRating(companyModel.getRating());
    }

    private void initialize() {
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        mButtonAllReviews = (Button) findViewById(R.id.button_all_reviews);
        mButtonCash = (Button) findViewById(R.id.button_cash);
        mButtonCashLess = (Button) findViewById(R.id.button_cashless);
        mButtonOpenNavigate = (Button) findViewById(R.id.button_open_navigate);
        mButtonShowOnMap = (Button) findViewById(R.id.button_show_on_map);
        mImageViewMap = (ImageView) findViewById(R.id.image_view_map);
        mRatingViewCompanyRating = (RatingView) findViewById(R.id.rating_view_company);
        mTextViewAddress = (TextView) findViewById(R.id.text_view_address);
        mTextViewCategory = (TextView) findViewById(R.id.text_view_category);
        mTextViewCompanyName = (TextView) findViewById(R.id.text_view_company_name);
        mTextViewDistance = (TextView) findViewById(R.id.text_view_distance);
        mTextViewWorkTime = (TextView) findViewById(R.id.text_view_work_time);
        mTextViewReviewCount = (TextView) findViewById(R.id.text_view_review_count);
    }
}
