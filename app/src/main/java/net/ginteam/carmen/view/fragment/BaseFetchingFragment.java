package net.ginteam.carmen.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.ginteam.carmen.R;

public abstract class BaseFetchingFragment extends BaseFragment {

    protected View mRootView;

    protected LinearLayout mProgressBar;

    public BaseFetchingFragment() {}

    protected View inflateBaseFragment(int layoutRes, LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        mRootView = inflater.inflate(layoutRes, container, false);

        mProgressBar = (LinearLayout) mRootView.findViewById(R.id.progress_bar);

        return mRootView;
    }

    public void showLoading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    public void showError(String message) {
        mProgressBar.setVisibility(View.GONE);
    }

}
