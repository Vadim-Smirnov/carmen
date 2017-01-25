package net.ginteam.carmen.view.activity.company;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.company.CompanyDetailContract;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.presenter.company.CompanyDetailPresenter;
import net.ginteam.carmen.utils.ActivityUtils;
import net.ginteam.carmen.view.activity.MapActivity;
import net.ginteam.carmen.view.activity.ToolbarActivity;
import net.ginteam.carmen.view.activity.VoteObjectActivity;
import net.ginteam.carmen.view.custom.rating.RatingView;
import net.ginteam.carmen.view.fragment.BaseFetchingFragment;

public class CompanyDetailActivity extends ToolbarActivity implements CompanyDetailContract.View,
        View.OnClickListener, RatingView.OnRatingChangeListener {

    public static final String COMPANY_ARGUMENT = "company";

    private int mCompanyId;
    private CompanyModel mCompanyModel;

    private CompanyDetailContract.Presenter mPresenter;

    private Menu mMenu;

    private LinearLayout mProgressBar;
    private TextView mTextViewCompanyName;
    private TextView mTextViewCategory;
    private RatingView mRatingViewCompanyRating;
    private RatingView mRatingViewVoteObject;
    private TextView mTextViewReviewCount;
    private TextView mTextViewDistance;
    private TextView mTextViewWorkTime;
    private TextView mTextViewAddress;
    private Button mButtonCash;
    private Button mButtonCashLess;
    private ImageView mImageViewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_company_detail);

        receiveArguments();
        updateDependencies();

        mPresenter = new CompanyDetailPresenter(this);
        mPresenter.fetchCompanyDetail(mCompanyId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.company_detail_menu, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                mPresenter.addToFavoriteClick(mCompanyModel, mMenu);
                break;
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
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
        mCompanyModel = companyModel;

        mMenu.getItem(0).setIcon(ContextCompat.getDrawable(getContext(),
                companyModel.isFavorite() ? R.drawable.ic_company_favorite_enable :
                        R.drawable.ic_company_favorite_disable));
        mTextViewCompanyName.setText(companyModel.getName());
        mTextViewCategory.setText(companyModel.getCategory().get(0).getName());
        mTextViewAddress.setText(companyModel.getAddress());
        mTextViewWorkTime.append(companyModel.getDetail().getWorkTime().get(1));
        mTextViewDistance.setText("1.2 km");
        mTextViewReviewCount
                .setText(getResources().getQuantityString(
                        R.plurals.review_count_string,
                        companyModel.getRatings().size(),
                        companyModel.getRatings().size()));
        mRatingViewCompanyRating.setRating(companyModel.getRating());
    }

    @Override
    public void openNavigator() {
        String uri = String.format("geo:%1$s,%2$s?q=%1$s,%2$s",
                mCompanyModel.getPosition().latitude,
                mCompanyModel.getPosition().longitude);
        String packageName = "com.google.android.apps.maps";

        Uri gmmIntentUri = Uri.parse(uri);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage(packageName);
        try {
            startActivity(mapIntent);
        } catch (android.content.ActivityNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
            }
        }
    }

    @Override
    public void showMap() {
        ActivityUtils.showActivity(MapActivity.class, null, false);
    }

    @Override
    public void showFragment(@IdRes int containerId, BaseFetchingFragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    @Override
    public void onClick(View v) {
        mPresenter.onClick(v);
    }

    @Override
    public void onRatingChanged(RatingView ratingView, int rating) {
        ActivityUtils.showActivity(VoteObjectActivity.class, null, false);
    }

    private void receiveArguments() {
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            mCompanyId = arguments.getInt(COMPANY_ARGUMENT, 0);
            Toast.makeText(this, "Receive ID: " + mCompanyId, Toast.LENGTH_LONG).show();
        }
    }

    private void updateDependencies() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mProgressBar = (LinearLayout) findViewById(R.id.progress_bar);

        mButtonCash = (Button) findViewById(R.id.button_cash);
        mButtonCashLess = (Button) findViewById(R.id.button_cashless);

        findViewById(R.id.button_open_navigate).setOnClickListener(this);
        findViewById(R.id.button_show_on_map).setOnClickListener(this);

        mRatingViewVoteObject = (RatingView) findViewById(R.id.rating_view_vote_object);
        mRatingViewVoteObject.setUserInteractionEnabled(true);
        mRatingViewVoteObject.setOnRatingChangeListener(this);

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
