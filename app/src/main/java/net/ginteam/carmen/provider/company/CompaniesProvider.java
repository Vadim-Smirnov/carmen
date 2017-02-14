package net.ginteam.carmen.provider.company;

import com.google.android.gms.maps.model.LatLng;

import net.ginteam.carmen.manager.ApiManager;
import net.ginteam.carmen.manager.PreferencesManager;
import net.ginteam.carmen.model.Pagination;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.model.company.MapCompanyModel;
import net.ginteam.carmen.network.api.ApiLinks;
import net.ginteam.carmen.network.api.service.CompanyService;
import net.ginteam.carmen.network.api.subscriber.ModelSubscriber;
import net.ginteam.carmen.network.api.subscriber.ModelSubscriberWithMeta;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.ModelCallbackWithMeta;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Eugene on 12/27/16.
 */

public class CompaniesProvider {

    private static CompaniesProvider sInstance;

    private CompanyService mCompanyService;
    private LatLng mUserLocation;

    private CompaniesProvider() {
        mCompanyService = ApiManager.getInstance().getService(CompanyService.class);
        mUserLocation = PreferencesManager.getInstance().getUserLocation();
    }

    public static CompaniesProvider getInstance() {
        if (sInstance == null) {
            sInstance = new CompaniesProvider();
        }
        return sInstance;
    }

    public void fetchForCategory(final int categoryId, String filter, String sortField, String sortType,
                                 int page, final ModelCallbackWithMeta<List<CompanyModel>> completion) {
        mCompanyService
                .fetchCompanies(categoryId, PreferencesManager.getInstance().getCity().getBounds(), filter, sortField, sortType, page)
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

    public void fetchForBounds(int categoryId, String filters, String bounds, final ModelCallback<List<MapCompanyModel>> completion) {
        mCompanyService
                .fetchCompanies(categoryId, filters, bounds)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<List<MapCompanyModel>>() {
                    @Override
                    public void onSuccess(List<MapCompanyModel> resultModel) {
                        completion.onSuccess(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

    public void fetchCompanyDetail(int companyId, final ModelCallback<CompanyModel> completion) {
        String relations = String.format("%s,%s,%s,%s", ApiLinks.CATALOG.COMFORTS,
                ApiLinks.CATALOG.DETAIL, ApiLinks.CATALOG.CATEGORIES, ApiLinks.CATALOG.RATINGS);

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

    public void fetchRecentlyWatched(final ModelCallback<List<CompanyModel>> completion) {
        mCompanyService
                .fetchRecentlyWatched()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<List<CompanyModel>>() {
                    @Override
                    public void onSuccess(List<CompanyModel> resultModel) {
                        completion.onSuccess(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

    public void fetchPopular(int cityId, final ModelCallback<List<CompanyModel>> completion) {
        mCompanyService
                .fetchPopular(cityId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<List<CompanyModel>>() {
                    @Override
                    public void onSuccess(List<CompanyModel> resultModel) {
                        completion.onSuccess(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

}
