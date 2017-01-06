package net.ginteam.carmen.provider.company;

import net.ginteam.carmen.manager.ApiManager;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.network.api.service.CompanyService;
import net.ginteam.carmen.network.api.subscriber.ModelSubscriber;
import net.ginteam.carmen.provider.ModelCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Eugene on 12/27/16.
 */

public class CompaniesProvider {

    private static CompaniesProvider sInstance;

    private Map <Integer, List <CompanyModel>> mCachedCompanies;

    private CompaniesProvider() {
        mCachedCompanies = new HashMap<>();
    }

    public static CompaniesProvider getInstance() {
        if (sInstance == null) {
            sInstance = new CompaniesProvider();
        }
        return sInstance;
    }

    public void fetchForCategory(int categoryId, ModelCallback<List<CompanyModel>> completion) {
        if (mCachedCompanies.containsKey(categoryId)) {
            completion.onSuccess(mCachedCompanies.get(categoryId));
            return;
        }
        fetchFromServer(categoryId, completion);
    }

    public void fetchRecentlyWatched(ModelCallback<List<CompanyModel>> completion) {}

    public void fetchPopular(ModelCallback<List<CompanyModel>> completion) {}

    private void fetchFromServer(final int forCategoryId, final ModelCallback<List<CompanyModel>> completion) {
        CompanyService companyService = ApiManager.getInstance().getService(CompanyService.class);
        companyService
                .fetchCompanies(forCategoryId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<List<CompanyModel>>() {
                    @Override
                    public void onSuccess(List<CompanyModel> resultModel) {
                        mCachedCompanies.put(forCategoryId, resultModel);
                        completion.onSuccess(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

}
