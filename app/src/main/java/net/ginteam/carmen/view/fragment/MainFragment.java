package net.ginteam.carmen.view.fragment;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.MainContract;
import net.ginteam.carmen.presenter.MainPresenter;

public class MainFragment extends BaseFragment implements MainContract.View {

    private MainContract.Presenter mPresenter;

    private View mRootView;

    public MainFragment() {}

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_main, container, false);

        mPresenter = new MainPresenter();
        mPresenter.attachView(this);

        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();

        if (isNotNestedFragment()) {
            setToolbarTitle(getString(R.string.main_item_title), "");
        }
    }

    @Override
    public void showFragment(@LayoutRes int containerId, BaseFetchingFragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

}
