package net.ginteam.carmen.provider.company;

import net.ginteam.carmen.manager.ApiManager;
import net.ginteam.carmen.model.Pagination;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.network.api.service.CompanyService;
import net.ginteam.carmen.network.api.subscriber.ModelSubscriberWithMeta;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.ModelCallbackWithMeta;

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

    public void fetchForCategory(final int categoryId, String filter, int page, final ModelCallbackWithMeta<List<CompanyModel>> completion) {
        CompanyService companyService = ApiManager.getInstance().getService(CompanyService.class);
        companyService
                .fetchCompanies(categoryId, filter, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriberWithMeta<List<CompanyModel>>() {
                    @Override
                    public void onSuccess(List<CompanyModel> resultModel, Pagination pagination) {
                        completion.onSuccess(resultModel, pagination);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

    public void fetchRecentlyWatched(ModelCallback<List<CompanyModel>> completion) {}

    public void fetchPopular(ModelCallback<List<CompanyModel>> completion) {}

    public void fetchFavorite(ModelCallback<List<CompanyModel>> completion) {}

}
