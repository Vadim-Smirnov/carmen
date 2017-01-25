package net.ginteam.carmen.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.MainContract;
import net.ginteam.carmen.presenter.MainPresenter;

public class MainFragment extends Fragment implements MainContract.View {

    private MainContract.Presenter mPresenter;

    private View mRootView;

    private OnMainFragmentShowedListener mMainFragmentShowedListener;

    public MainFragment() {}

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mMainFragmentShowedListener = (OnMainFragmentShowedListener) context;
        } catch (ClassCastException exception) {
            Log.e("MainFragment", "Parent context does not confirm to OnMainFragmentShowedListener!");
        }
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

        if (mMainFragmentShowedListener != null) {
            mMainFragmentShowedListener.onMainFragmentShowed();
        }
    }

    @Override
    public void showFragment(@LayoutRes int containerId, BaseFetchingFragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    public interface OnMainFragmentShowedListener {

        void onMainFragmentShowed();

    }

}
