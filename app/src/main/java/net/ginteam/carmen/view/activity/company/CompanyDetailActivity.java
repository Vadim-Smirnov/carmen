package net.ginteam.carmen.view.activity.company;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import net.ginteam.carmen.R;
import net.ginteam.carmen.utils.ActivityUtils;
import net.ginteam.carmen.view.activity.ReviewActivity;
import net.ginteam.carmen.view.activity.ToolbarActivity;
import net.ginteam.carmen.view.custom.rating.RatingView;

public class CompanyDetailActivity extends ToolbarActivity {

    public static final String COMPANY_ARGUMENT = "company";

    private int mCompanyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_company_detail);

        RatingView ratingView = (RatingView) findViewById(R.id.rating_view_vote_object);
        ratingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.showActivity(ReviewActivity.class, null, true);
                finish();
            }
        });
        receiveArguments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.company_list_menu, menu);
        return true;
    }

    private void receiveArguments() {
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            mCompanyId = arguments.getInt(COMPANY_ARGUMENT, 0);
            Toast.makeText(this, "Receive ID: " + mCompanyId, Toast.LENGTH_LONG).show();
        }
    }

}
