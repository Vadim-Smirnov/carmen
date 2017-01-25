package net.ginteam.carmen.provider.company;

import android.util.Log;

import net.ginteam.carmen.manager.ApiManager;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.network.api.service.CompanyService;
import net.ginteam.carmen.network.api.subscriber.ModelSubscriber;
import net.ginteam.carmen.provider.ModelCallback;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vadik on 12.01.17.
 */

public class FavoritesProvider {

    private static FavoritesProvider sInstance;

    private List<CompanyModel> mCachedFavorites;

    private CompanyService mCompanyService;

    private FavoritesProvider() {
        mCachedFavorites = new ArrayList<>();
        mCompanyService = ApiManager.getInstance().getService(CompanyService.class);
    }

    public static FavoritesProvider getInstance() {
        if (sInstance == null) {
            sInstance = new FavoritesProvider();
        }
        return sInstance;
    }

    public void fetchFavorite(ModelCallback<List<CompanyModel>> completion) {
//        if (!mCachedFavorites.isEmpty()) {
//            completion.onSuccess(mCachedFavorites);
//            return;
//        }
        fetchFromServer(completion);
    }

    public void addToFavorites(final CompanyModel selectedCompany, final ModelCallback<String> completion) {
        mCompanyService
                .addToFavorites(selectedCompany.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<String>() {
                    @Override
                    public void onSuccess(String resultModel) {
                        mCachedFavorites.add(selectedCompany);
                        completion.onSuccess(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

    public void removeFromFavorites(final CompanyModel selectedCompany, final ModelCallback<String> completion) {
        mCompanyService
                .removeFromFavorites(selectedCompany.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<String>() {
                    @Override
                    public void onSuccess(String resultModel) {
                        mCachedFavorites.remove(selectedCompany);
                        completion.onSuccess(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

    private void fetchFromServer(final ModelCallback<List<CompanyModel>> completion) {
        mCompanyService
                .fetchFavorites()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<List<CompanyModel>>() {
                    @Override
                    public void onSuccess(List<CompanyModel> resultModel) {
                        mCachedFavorites.addAll(resultModel);
                        completion.onSuccess(resultModel);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

}
