package net.ginteam.carmen.presenter;

import android.support.annotation.IdRes;

import net.ginteam.carmen.contract.SortingContract;
import net.ginteam.carmen.manager.SortViewStateManager;
import net.ginteam.carmen.model.SortingModel;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.SortingProvider;

import java.util.List;

/**
 * Created by vadik on 18.01.17.
 */

public class SortingPresenter implements SortingContract.Presenter {

    private SortingContract.View mView;

    public SortingPresenter() {
    }

    @Override
    public void saveViewState(int categoryId, @IdRes int checkedViewId, String sortField, String sortType) {
        SortViewStateManager
                .getInstance()
                .saveViewState(new SortViewStateManager.SortViewState(categoryId, checkedViewId, sortField, sortType));
    }

    @Override
    public SortViewStateManager.SortViewState restoreViewState(int categoryId) {
        return SortViewStateManager
                .getInstance()
                .restoreViewState(categoryId);
    }

    @Override
    public void fetchSortingForCategory(int categoryId) {
        mView.showLoading(true);

        SortingProvider
                .getInstance()
                .fetchForCategory(categoryId, new ModelCallback<List<SortingModel>>() {
                    @Override
                    public void onSuccess(List<SortingModel> resultModel) {
                        mView.showLoading(false);
                        mView.showSorting(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        mView.showLoading(false);
                        mView.showError(message);
                    }
                });
    }

    @Override
    public void attachView(SortingContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

}
