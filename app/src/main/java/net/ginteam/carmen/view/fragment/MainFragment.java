package net.ginteam.carmen.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.provider.auth.AuthProvider;
import net.ginteam.carmen.view.fragment.category.CategoryListFragment;
import net.ginteam.carmen.view.fragment.company.CompanyListFragment;

public class MainFragment extends Fragment {

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

        prepareFragment(R.id.categories_fragment_container, CategoryListFragment.newInstance(false));
        if (AuthProvider.getInstance().getCurrentCachedUser() != null) {
            prepareFragment(R.id.recently_companies_fragment_container,
                    CompanyListFragment.newInstance(
                            CompanyListFragment.COMPANY_LIST_TYPE.HORIZONTAL,
                            CompanyListFragment.FETCH_COMPANY_TYPE.RECENTLY_WATCHED,
                            0
                    )
            );
        }
        prepareFragment(R.id.popular_companies_fragment_container,
                CompanyListFragment.newInstance(
                        CompanyListFragment.COMPANY_LIST_TYPE.HORIZONTAL,
                        CompanyListFragment.FETCH_COMPANY_TYPE.POPULAR,
                        0
                )
        );

        if (mMainFragmentShowedListener != null) {
            mMainFragmentShowedListener.onMainFragmentShowed();
        }

        return mRootView;
    }

    private void prepareFragment(int containerId, Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    public interface OnMainFragmentShowedListener {

        void onMainFragmentShowed();

    }

}
