package net.ginteam.carmen.presenter.company;

import net.ginteam.carmen.contract.company.CompanyDetailContract;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.company.CompaniesProvider;

/**
 * Created by vadik on 16.01.17.
 */

public class CompanyDetailPresenter implements CompanyDetailContract.Presenter {

    private CompanyDetailContract.View mView;

    public CompanyDetailPresenter(CompanyDetailContract.View view) {
        attachView(view);
    }

    @Override
    public void attachView(CompanyDetailContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void fetchCompanyDetail(int companyId) {
        mView.showLoading(true);

        CompaniesProvider
                .getInstance()
                .fetchCompanyDetail(companyId, new ModelCallback<CompanyModel>() {
                    @Override
                    public void onSuccess(CompanyModel resultModel) {
                        mView.showLoading(false);
                        mView.showCompanyDetail(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        mView.showLoading(false);
                        mView.showError(message);
                    }
                });
    }
}
