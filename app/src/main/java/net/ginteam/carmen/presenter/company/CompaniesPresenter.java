package net.ginteam.carmen.presenter.company;

import net.ginteam.carmen.contract.company.CompaniesContract;
import net.ginteam.carmen.manager.FiltersViewStateManager;
import net.ginteam.carmen.model.Pagination;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.provider.ModelCallbackWithMeta;
import net.ginteam.carmen.provider.company.CompaniesProvider;

import java.util.List;

/**
 * Created by Eugene on 12/27/16.
 */

public class CompaniesPresenter implements CompaniesContract.Presenter {

    private CompaniesContract.View mView;
    private boolean mIsFirstLoading;

    @Override
    public void selectCompany(CompanyModel company) {

    }

    @Override
    public void fetchRecentlyWatchedCompanies() {

    }

    @Override
    public void fetchPopularCompanies() {

    }

    @Override
    public void fetchFavoriteCompanies() {

    }

    @Override
    public void fetchCompaniesForCategory(int categoryId, String filter, int page) {
        mView.showLoading(true);
        mIsFirstLoading = (page == 1);

        CompaniesProvider
                .getInstance()
                .fetchForCategory(categoryId, filter, page, new ModelCallbackWithMeta<List<CompanyModel>>() {
                    @Override
                    public void onSuccess(List<CompanyModel> resultModel, Pagination pagination) {
                        mView.showLoading(false);
                        if (mIsFirstLoading) {
                            mView.showCompanies(resultModel, pagination);
                            mIsFirstLoading = false;
                            return;
                        }
                        mView.showMoreCompanies(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        mView.showLoading(false);
                        mView.showError(message);
                    }
                });
    }

    @Override
    public void attachView(CompaniesContract.View view) {
        mView = view;
        FiltersViewStateManager.getInstance().resetFiltersState();
    }

    @Override
    public void detachView() {
        mView = null;
    }

}
