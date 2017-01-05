package net.ginteam.carmen.presenter.category;

import net.ginteam.carmen.contract.category.CategoriesContract;
import net.ginteam.carmen.model.category.CategoryModel;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.category.CategoriesProvider;

import java.util.List;

/**
 * Created by Eugene on 12/23/16.
 */

public class CategoriesPresenter implements CategoriesContract.Presenter {

    private CategoriesContract.View mView;

    @Override
    public void selectCategory(CategoryModel category) {

    }

    @Override
    public void fetchCategories() {
        mView.showLoading(true);

        CategoriesProvider
                .getInstance()
                .fetchCategories(new ModelCallback<List<CategoryModel>>() {
                    @Override
                    public void onSuccess(List<CategoryModel> resultModel) {
                        mView.showLoading(false);
                        mView.showCategories(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        mView.showError(message);
                    }
                });
    }

    @Override
    public void attachView(CategoriesContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

}
