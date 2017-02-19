package net.ginteam.carmen.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import net.ginteam.carmen.R;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.view.activity.company.CompanyDetailActivity;
import net.ginteam.carmen.view.custom.FilterEditText;
import net.ginteam.carmen.view.custom.rating.CarmenRatingView;

public class VoteObjectActivity extends ToolbarActivity {

    private float mRating;
    private CompanyModel mCurrentCompany;

    private CarmenRatingView mRatingView;
    private FilterEditText mFilterEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_object);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            mRating = arguments.getFloat(CompanyDetailActivity.RATING_ARGUMENT);
            mCurrentCompany = (CompanyModel) arguments.getSerializable(CompanyDetailActivity.COMPANY_ARGUMENT);
        }

        updateDependencies();
    }

    private void updateDependencies() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button);

        mRatingView = (CarmenRatingView) findViewById(R.id.rating_view_company);
        mRatingView.setRating(mRating);

        mFilterEditText = (FilterEditText) findViewById(R.id.CustomEditText);

        setTitle("Отзывы");

    }

}
