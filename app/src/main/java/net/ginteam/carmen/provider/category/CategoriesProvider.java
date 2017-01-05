package net.ginteam.carmen.provider.category;

import net.ginteam.carmen.manager.ApiManager;
import net.ginteam.carmen.model.category.CategoryModel;
import net.ginteam.carmen.network.api.service.CategoryService;
import net.ginteam.carmen.network.api.subscriber.ModelSubscriber;
import net.ginteam.carmen.provider.ModelCallback;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Eugene on 12/23/16.
 */

public class CategoriesProvider {

    private static CategoriesProvider sInstance;

    private List <CategoryModel> mCachedCategories;

    private CategoriesProvider() {}

    public static CategoriesProvider getInstance() {
        if (sInstance == null) {
            sInstance = new CategoriesProvider();
        }
        return sInstance;
    }

    public void fetchCategories(ModelCallback<List<CategoryModel>> completion) {
        if (mCachedCategories != null) {
            completion.onSuccess(mCachedCategories);
            return;
        }
        fetchFromServer(completion);
    }

    private void fetchFromServer(final ModelCallback<List<CategoryModel>> completion) {
        CategoryService categoryService = ApiManager.getInstance().getService(CategoryService.class);
        categoryService
                .fetchCategories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<List<CategoryModel>>() {
                    @Override
                    public void onSuccess(List<CategoryModel> resultModel) {
                        mCachedCategories = resultModel;
                        completion.onSuccess(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

}
