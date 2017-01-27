package net.ginteam.carmen.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.FetchContract;

public abstract class BaseFetchingFragment extends BaseFragment implements FetchContract.View {

    protected View mRootView;

    protected LinearLayout mProgressBar;

    public BaseFetchingFragment() {}

    protected View inflateBaseFragment(int layoutRes, LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        mRootView = inflater.inflate(layoutRes, container, false);

        mProgressBar = (LinearLayout) mRootView.findViewById(R.id.progress_bar);

        return mRootView;
    }

    @Override
    public void showLoading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String message) {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

}
