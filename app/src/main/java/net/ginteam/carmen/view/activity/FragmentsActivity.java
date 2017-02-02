package net.ginteam.carmen.view.activity;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import net.ginteam.carmen.R;
import net.ginteam.carmen.view.fragment.BaseFragment;
import net.ginteam.carmen.view.fragment.MainFragment;

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
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack(mCurrentFragment.getClass().getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            return;
        }

        if (!mCurrentFragment.getClass().getName().equals(MainFragment.class.getName())) {
            showMainFragment();
            return;
        }

        finish();
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

    protected void cleanBackStack() {
        int fragmentsCount = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < fragmentsCount; i++) {
            getSupportFragmentManager().popBackStack();
        }
    }

    public abstract void showMainFragment();

}