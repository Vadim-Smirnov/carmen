package net.ginteam.carmen.data.provider.company.favorites;

import net.ginteam.carmen.data.provider.company.CompaniesSourceProvider;
import net.ginteam.carmen.data.remote.api.request.FavoritesApi;
import net.ginteam.carmen.manager.ApiManager;
import net.ginteam.carmen.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public class ApiFavoritesProvider implements FavoritesSourceProvider {

    private FavoritesApi mFavoritesApi = ApiManager.getInstance().getService(FavoritesApi.class);

    @Override
    public void fetchFavorites(String lat, String lng, CompaniesSourceProvider.CompaniesCallback callback) {
        mFavoritesApi.fetchFavorites(lat, lng).enqueue(callback);
    }

    @Override
    public void addToFavorites(int id, final AddToFavoriteCallback callback) {
        mFavoritesApi.addToFavorites(id).enqueue(new Callback<ResponseModel<String>>() {
            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                callback.success();
            }

            @Override
            public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                callback.error();
            }
        });
    }

    @Override
    public void removeFromFavorites(int id, final RemoveFromFavoriteCallback callback) {
        mFavoritesApi.removeFromFavorites(id).enqueue(new Callback<ResponseModel<String>>() {
            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                callback.success();
            }

            @Override
            public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                callback.error();
            }
        });
    }

}
