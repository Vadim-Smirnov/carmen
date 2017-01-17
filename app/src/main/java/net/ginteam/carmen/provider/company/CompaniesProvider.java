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

    private CompanyService mCompanyService;
    private Map<Integer, CompanyModel> mCachedCompaniesDetail;

    private CompaniesProvider() {
        mCachedCompaniesDetail = new HashMap<>();
        mCompanyService = ApiManager.getInstance().getService(CompanyService.class);
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

    public void fetchCompanyDetail(int companyId, ModelCallback<CompanyModel> completion) {
        if (mCachedCompaniesDetail.containsKey(companyId)) {
            completion.onSuccess(mCachedCompaniesDetail.get(companyId));
            return;
        }
        fetchDetailFromServer(companyId, completion);
    }

    public void fetchRecentlyWatched(ModelCallback<List<CompanyModel>> completion) {}

    public void fetchPopular(ModelCallback<List<CompanyModel>> completion) {}

    public void fetchFavorite(ModelCallback<List<CompanyModel>> completion) {}

    private void fetchDetailFromServer(int companyId, final ModelCallback<CompanyModel> completion) {
        String relations = String.format("%s,%s,%s", ApiLinks.CATALOG.COMFORTS,
                ApiLinks.CATALOG.DETAIL, ApiLinks.CATALOG.CATEGORIES);
        mCompanyService
                .fetchCompanyDetail(companyId, relations)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<CompanyModel>() {
                    @Override
                    public void onSuccess(CompanyModel resultModel) {
                        completion.onSuccess(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

}
