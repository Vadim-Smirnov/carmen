package net.ginteam.carmen.view.activity.company;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.company.CompanyDetailContract;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.presenter.company.CompanyDetailPresenter;
import net.ginteam.carmen.utils.ActivityUtils;
import net.ginteam.carmen.view.activity.ToolbarActivity;
import net.ginteam.carmen.view.activity.VoteObjectActivity;
import net.ginteam.carmen.view.activity.map.MapActivity;
import net.ginteam.carmen.view.adapter.company.GalleryRecyclerAdapter;
import net.ginteam.carmen.view.custom.rating.CarmenRatingView;
import net.ginteam.carmen.view.custom.rating.RatingView;
import net.ginteam.carmen.view.fragment.BaseFetchingFragment;

public class CompanyDetailActivity extends ToolbarActivity implements CompanyDetailContract.View,
        View.OnClickListener, RatingView.OnRatingChangeListener {

    public static final String COMPANY_ARGUMENT = "company";

    private int mCompanyId;
    private CompanyModel mCompanyModel;

    private CompanyDetailContract.Presenter mPresenter;

    private Menu mMenu;
    private GalleryRecyclerAdapter mGalleryRecyclerAdapter;

    private LinearLayout mProgressBar;
    private TextView mTextViewCompanyName;
    private TextView mTextViewCategory;
    private CarmenRatingView mRatingViewCompanyRating;
    private CarmenRatingView mRatingViewVoteObject;
    private TextView mTextViewReviewCount;
    private TextView mTextViewDistance;
    private TextView mTextViewWorkTime;
    private TextView mTextViewAddress;
    private ImageView mImageViewLocation;
    private Button mButtonCash;
    private Button mButtonCashLess;
    private ImageView mImageViewMap;
    private FloatingActionButton mActionButtonCall;
    private RecyclerView mRecyclerViewGallery;
    private LinearLayout mLinearLayoutIndicator;

    private float mCoordinateX;
    private int mCurrentPreviewPosition;
    private int mImagesCount;
    private ImageView[] mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_company_detail);

        receiveArguments();
        updateDependencies();

        mPresenter = new CompanyDetailPresenter();
        mPresenter.attachView(this);
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

        mGalleryRecyclerAdapter = new GalleryRecyclerAdapter(getContext(), mCompanyModel.getImageUrl());
        mRecyclerViewGallery.setAdapter(mGalleryRecyclerAdapter);
        mRecyclerViewGallery.setOnTouchListener(mOnChangeTemplatePreview);
        showIndicator();

        mMenu.getItem(0).setIcon(ContextCompat.getDrawable(getContext(),
                companyModel.isFavorite() ? R.drawable.ic_company_favorite_enable :
                        R.drawable.ic_company_favorite_disable));
        mTextViewCompanyName.setText(companyModel.getName());
//        mTextViewCategory.setText(companyModel.getCategory().get(0).getName());
        mTextViewAddress.setText(companyModel.getAddress());
        if (!companyModel.getDetail().getWorkTime().isEmpty()) {
            mTextViewWorkTime.append(companyModel.getDetail().getWorkTime().get(1));
        } else {
            mTextViewWorkTime.setText("");
        }
        mTextViewDistance.setText(companyModel.getDistance() == 0 ? "" :
                String.format("%.1f km", companyModel.getDistance() / 1000));
        mImageViewLocation.setVisibility(mTextViewDistance.getText().toString().isEmpty() ?
                View.INVISIBLE : View.VISIBLE);
        mTextViewReviewCount
                .setText(getResources().getQuantityString(
                        R.plurals.review_count_string,
                        companyModel.getRatings().size(),
                        companyModel.getRatings().size()));
        mRatingViewCompanyRating.setRating(companyModel.getRating());
        mActionButtonCall.setVisibility(companyModel.getDetail().getPhones().isEmpty() ?
                View.GONE : View.VISIBLE);
        showMapImage();
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
    public void call() {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        if (mCompanyModel.getDetail().getPhones().size() > 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Выберите номер:");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item);
            for (String currentNumber : mCompanyModel.getDetail().getPhones()) {
                arrayAdapter.add(currentNumber);
            }
            builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intent.setData(Uri.parse("tel:" + arrayAdapter.getItem(which)));
                    startActivity(intent);
                }
            });

            builder.show();
        } else {
            intent.setData(Uri.parse("tel:" + mCompanyModel.getDetail().getPhones().get(0)));
            startActivity(intent);
        }
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

        mRatingViewVoteObject = (CarmenRatingView) findViewById(R.id.rating_view_vote_object);
//        mRatingViewVoteObject.setOnClickListener(this);

        mRecyclerViewGallery = (RecyclerView) findViewById(R.id.recycler_view_gallery);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewGallery.setLayoutManager(layoutManager);

        mImageViewMap = (ImageView) findViewById(R.id.image_view_map);
        mImageViewLocation = (ImageView) findViewById(R.id.image_view_location);
        mRatingViewCompanyRating = (CarmenRatingView) findViewById(R.id.rating_view_company);
        mTextViewAddress = (TextView) findViewById(R.id.text_view_address);
        mTextViewCategory = (TextView) findViewById(R.id.text_view_category);
        mTextViewCompanyName = (TextView) findViewById(R.id.text_view_company_name);
        mTextViewDistance = (TextView) findViewById(R.id.text_view_distance);
        mTextViewWorkTime = (TextView) findViewById(R.id.text_view_work_time);
        mTextViewReviewCount = (TextView) findViewById(R.id.text_view_review_count);
        mActionButtonCall = (FloatingActionButton) findViewById(R.id.action_button_call);
        mLinearLayoutIndicator = (LinearLayout) findViewById(R.id.gallery_indicator);
        mActionButtonCall.setOnClickListener(this);
    }

    private void showMapImage() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int mapHeight = (int) getResources().getDimension(R.dimen.company_detail_image_map_height);
        String url =
                String.format("http://maps.googleapis.com/maps/api/staticmap?center=%1$s," +
                                "%2$s&markers=color:red|size:mid|%1$s,%2$s&zoom=14&scale=2&" +
                                "size=%3$sx%4$s&format=png&visual_refresh=true",
                        mCompanyModel.getPosition().latitude, mCompanyModel.getPosition().longitude,
                        size.x, mapHeight);

        Picasso.with(getContext()).load(url).into(mImageViewMap);
    }

    private View.OnTouchListener mOnChangeTemplatePreview = new View.OnTouchListener() {
        @Override
        public boolean onTouch(final View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mCoordinateX = event.getX();
                    break;
                case MotionEvent.ACTION_UP:
                    if (mCoordinateX > event.getX()) {
                        if (mCurrentPreviewPosition + 1 < mGalleryRecyclerAdapter.getItemCount()) {
                            mCurrentPreviewPosition++;
                        }
                    } else if (mCoordinateX < event.getX()) {
                        if (mCurrentPreviewPosition - 1 >= 0) {
                            mCurrentPreviewPosition--;
                        }
                    }
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((RecyclerView) v).scrollToPosition(mCurrentPreviewPosition);
                            setIndicator(mCurrentPreviewPosition);
                        }
                    }, 50);

                    break;
            }
            return false;
        }
    };

    private void showIndicator() {
        mImagesCount = mGalleryRecyclerAdapter.getItemCount();

        if (mImagesCount > 1) {
            mIndicator = new ImageView[mImagesCount];

            for (int i = 0; i < mImagesCount; i++) {
                mIndicator[i] = new ImageView(getContext());
                mIndicator[i].setImageDrawable(ContextCompat.getDrawable(getContext(),
                        R.drawable.nonselecteditem_dot));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

                params.setMargins(5, 0, 5, 0);

                mLinearLayoutIndicator.addView(mIndicator[i], params);
            }
            mIndicator[0].setImageDrawable(ContextCompat.getDrawable(getContext(),
                    R.drawable.selecteditem_dot));
        }
    }

    private void setIndicator(int position) {
        if (mImagesCount > 1) {
            for (int i = 0; i < mImagesCount; i++) {
                mIndicator[i].setImageDrawable(ContextCompat.getDrawable(getContext(),
                        R.drawable.nonselecteditem_dot));
            }
            mIndicator[position].setImageDrawable(ContextCompat.getDrawable(getContext(),
                    R.drawable.selecteditem_dot));
        }
    }
}
