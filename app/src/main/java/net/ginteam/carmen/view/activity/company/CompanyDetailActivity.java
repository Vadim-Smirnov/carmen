package net.ginteam.carmen.view.activity.company;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.ginteam.carmen.R;
import net.ginteam.carmen.utils.ActivityUtils;
import net.ginteam.carmen.view.activity.MapActivity;
import net.ginteam.carmen.view.activity.ToolbarActivity;

public class CompanyDetailActivity extends ToolbarActivity {

    public static final String COMPANY_ARGUMENT = "company";

    private int mCompanyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_company_detail);

        receiveArguments();

        Button button = (Button) findViewById(R.id.button_show_on_map);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.showActivity(MapActivity.class, null, true);
                finish();
            }
        });
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
