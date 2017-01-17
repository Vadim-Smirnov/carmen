package net.ginteam.carmen.presenter.filter;

import net.ginteam.carmen.contract.filter.FilterContract;
import net.ginteam.carmen.manager.FiltersViewStateManager;
import net.ginteam.carmen.model.Pagination;
import net.ginteam.carmen.model.filter.FilterModel;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.filter.FiltersProvider;
import net.ginteam.carmen.view.custom.FilterEditText;

import java.util.List;

/**
 * Created by Eugene on 1/15/17.
 */

public class FiltersPresenter implements FilterContract.Presenter {

    private FilterContract.View mView;

    private FiltersViewStateManager mFiltersViewStateManager;

    @Override
    public void fetchFiltersForCategory(int categoryId) {
        mView.showLoading(true);

        FiltersProvider
                .getInstance()
                .fetchForCategory(categoryId, new ModelCallback<List<FilterModel>>() {
                    @Override
                    public void onSuccess(List<FilterModel> resultModel) {
                        mView.showLoading(false);
                        mView.showFilters(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        mView.showLoading(false);
                        mView.showError(message);
                    }
                });
    }

    @Override
    public void updateResultsWithFilter(int categoryId, String filter) {
        mView.showLoading(true);

        FiltersProvider
                .getInstance()
                .calculateCount(categoryId, filter, new ModelCallback<Pagination>() {
                    @Override
                    public void onSuccess(Pagination resultModel) {
                        mView.showLoading(false);
                        mView.showResultsCount(resultModel.getTotalItemsCount());
                    }

                    @Override
                    public void onFailure(String message) {
                        mView.showLoading(false);
                        mView.showError(message);
                    }
                });
    }

    @Override
    public void saveViewState(int categoryId, List<FilterEditText> filters, String filterQuery) {
        mFiltersViewStateManager.saveFiltersState(categoryId, filters, filterQuery);
    }

    @Override
    public String restoreViewState(int categoryId, List<FilterEditText> filters) {
        mFiltersViewStateManager.restoreFiltersState(categoryId, filters);
        return mFiltersViewStateManager.restoreFiltersQuery();
    }

    @Override
    public void attachView(FilterContract.View view) {
        mView = view;
        mFiltersViewStateManager = FiltersViewStateManager.getInstance();
    }

    @Override
    public void detachView() {
        mView = null;
    }

}
