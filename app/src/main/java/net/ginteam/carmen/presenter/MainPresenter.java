package net.ginteam.carmen.presenter;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.MainContract;
import net.ginteam.carmen.provider.auth.AuthProvider;
import net.ginteam.carmen.view.fragment.BaseFetchingFragment;
import net.ginteam.carmen.view.fragment.category.CategoryListFragment;
import net.ginteam.carmen.view.fragment.company.CompanyListFragment;

/**
 * Created by Eugene on 1/23/17.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    private BaseFetchingFragment mCategoriesFragment;
    private BaseFetchingFragment mRecentlyWatchedCompaniesFragment;
    private BaseFetchingFragment mPopularCompaniesFragment;

    @Override
    public void onStart() {
        if (AuthProvider.getInstance().getCurrentCachedUser() != null) {
            mRecentlyWatchedCompaniesFragment = CompanyListFragment.newInstance(
                    CompanyListFragment.COMPANY_LIST_TYPE.HORIZONTAL,
                    CompanyListFragment.FETCH_COMPANY_TYPE.RECENTLY_WATCHED,
                    0
            );
            mView.showFragment(R.id.recently_companies_fragment_container, mRecentlyWatchedCompaniesFragment);
        }
    }

    @Override
    public void attachView(MainContract.View view) {
        mView = view;

        mCategoriesFragment = CategoryListFragment.newInstance(false);
        mPopularCompaniesFragment = CompanyListFragment.newInstance(
                CompanyListFragment.COMPANY_LIST_TYPE.HORIZONTAL,
                CompanyListFragment.FETCH_COMPANY_TYPE.POPULAR,
                0
        );

        mView.showFragment(R.id.categories_fragment_container, mCategoriesFragment);
        mView.showFragment(R.id.popular_companies_fragment_container, mPopularCompaniesFragment);
    }

    @Override
    public void detachView() {

    }

}
