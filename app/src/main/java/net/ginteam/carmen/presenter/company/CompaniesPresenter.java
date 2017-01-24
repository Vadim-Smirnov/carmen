package net.ginteam.carmen.presenter.company;

import net.ginteam.carmen.contract.company.CompaniesContract;
import net.ginteam.carmen.manager.FiltersViewStateManager;
import net.ginteam.carmen.manager.PreferencesManager;
import net.ginteam.carmen.model.Pagination;
import net.ginteam.carmen.model.city.CityModel;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.ModelCallbackWithMeta;
import net.ginteam.carmen.provider.company.CompaniesProvider;
import net.ginteam.carmen.provider.company.FavoritesProvider;

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
        mView.showLoading(true);

        CompaniesProvider
                .getInstance()
                .fetchRecentlyWatched(new ModelCallback<List<CompanyModel>>() {
                    @Override
                    public void onSuccess(List<CompanyModel> resultModel) {
                        mView.showLoading(false);
                        mView.showCompanies(resultModel, null);
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
        mView.showLoading(true);

        CityModel userCity = PreferencesManager.getInstance().getCity();
        CompaniesProvider
                .getInstance()
                .fetchPopular(userCity.getId(), new ModelCallback<List<CompanyModel>>() {
                    @Override
                    public void onSuccess(List<CompanyModel> resultModel) {
                        mView.showLoading(false);
                        mView.showCompanies(resultModel, null);
                    }

                    @Override
                    public void onFailure(String message) {
                        mView.showLoading(false);
                        mView.showError(message);
                    }
                });
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
                        mView.showCompanies(resultModel, null);
                    }

                    @Override
                    public void onFailure(String message) {
                        mView.showLoading(false);
                        mView.showError(message);
                    }
                });
    }

    @Override
    public void fetchCompaniesForCategory(int categoryId, String filter, String sortField,
                                          String sortType, int page) {
        mIsFirstLoading = (page == 1);
        if (mIsFirstLoading) {
            mView.showLoading(true);
        }

        CompaniesProvider
                .getInstance()
                .fetchForCategory(categoryId, filter, sortField, sortType, page, new ModelCallbackWithMeta<List<CompanyModel>>() {
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
        FiltersViewStateManager.getInstance().resetFiltersState();
    }

    @Override
    public void detachView() {
        mView = null;
    }

}
