package net.ginteam.carmen.view.activity;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import net.ginteam.carmen.R;
import net.ginteam.carmen.view.fragment.BaseFragment;

/**
 * Created by Eugene on 1/27/17.
 */

public abstract class FragmentsActivity extends ToolbarActivity implements BaseFragment.OnSetToolbarTitleListener {

    protected Fragment mCurrentFragment;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setToolbarTitle(String title, String subtitle) {
        setTitle(title);
        setSubtitle(subtitle);
    }

    protected void prepareFragment(Fragment fragment, boolean isNeedBackStack) {
        mCurrentFragment = fragment;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, fragment);
        if (isNeedBackStack) {
            fragmentTransaction.addToBackStack(mCurrentFragment.getClass().getName());
        }
        fragmentTransaction.commit();
    }


}