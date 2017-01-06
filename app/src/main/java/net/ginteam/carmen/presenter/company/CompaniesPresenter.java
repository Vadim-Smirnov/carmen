package net.ginteam.carmen.presenter.company;

import net.ginteam.carmen.contract.company.CompaniesContract;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.company.CompaniesProvider;

import java.util.List;

/**
 * Created by Eugene on 12/27/16.
 */

public class CompaniesPresenter implements CompaniesContract.Presenter {

    private CompaniesContract.View mView;

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
    public void fetchCompaniesForCategory(int categoryId) {
        mView.showLoading(true);

        CompaniesProvider
                .getInstance()
                .fetchForCategory(categoryId, new ModelCallback<List<CompanyModel>>() {
                    @Override
                    public void onSuccess(List<CompanyModel> resultModel) {
                        mView.showLoading(false);
                        mView.showCompanies(resultModel);
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
    }

    @Override
    public void detachView() {
        mView = null;
    }

}
