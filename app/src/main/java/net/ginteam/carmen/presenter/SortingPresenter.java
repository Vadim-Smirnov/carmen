package net.ginteam.carmen.presenter;

import net.ginteam.carmen.contract.SortingContract;
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
    public void attachView(SortingContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
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
}
