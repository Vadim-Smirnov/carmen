package net.ginteam.carmen.presenter.company;

import net.ginteam.carmen.contract.company.CompaniesContract;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.company.CompaniesProvider;
import net.ginteam.carmen.provider.company.FavoritesProvider;
import net.ginteam.carmen.view.adapter.company.CompanyItemViewHolder;

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
        mView.showLoading(true);

        CompaniesProvider
                .getInstance()
                .fetchRecentlyWatched(new ModelCallback<List<CompanyModel>>() {
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
    public void fetchPopularCompanies() {

    }

    @Override
    public void fetchFavoriteCompanies() {
        mView.showLoading(true);

        FavoritesProvider
                .getInstance()
                .fetchFavorite(new ModelCallback<List<CompanyModel>>() {
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
    public void addToFavorites(CompanyModel company) {
        FavoritesProvider
                .getInstance()
                .addToFavorites(company, new ModelCallback<String>() {
                    @Override
                    public void onSuccess(String resultModel) {
                        mView.addToFavorites();
                    }

                    @Override
                    public void onFailure(String message) {
                        mView.showError(message);
                    }
                });
    }

    @Override
    public void removeFromFavorites(CompanyModel company) {
        FavoritesProvider
                .getInstance()
                .removeFromFavorites(company, new ModelCallback<String>() {
                    @Override
                    public void onSuccess(String resultModel) {
                        mView.removeFromFavorites();
                    }

                    @Override
                    public void onFailure(String message) {
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
