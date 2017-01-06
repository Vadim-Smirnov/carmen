package net.ginteam.carmen.view.activity;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import net.ginteam.carmen.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Eugene on 12/27/16.
 */

public class ToolbarActivity extends AppCompatActivity {

    protected Toolbar mToolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initializeToolbar();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void setTitle(String title) {
        ((TextView) mToolbar.findViewById(R.id.text_view_toolbar_title)).setText(title);
    }

    protected void setSubtitle(String subtitle) {
        ((TextView) mToolbar.findViewById(R.id.text_view_toolbar_subtitle)).setText(subtitle);
    }

    private void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}
