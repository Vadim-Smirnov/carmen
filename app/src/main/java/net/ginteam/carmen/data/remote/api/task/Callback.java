package net.ginteam.carmen.data.remote.api.task;

import net.ginteam.carmen.model.Meta;
import net.ginteam.carmen.model.Pagination;
import net.ginteam.carmen.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public abstract class Callback <T> implements retrofit2.Callback<ResponseModel<T>> {

    @Override
    public void onResponse(Call<ResponseModel<T>> call, Response<ResponseModel<T>> response) {
        if (response.isSuccessful()) {
            if (response.body().getMetaData() != null) {
                onSuccess(response.body().getData(), response.body().getMetaData().getPagination());
                return;
            }
            onSuccess(response.body().getData());
            return;
        }
        onFailure(response.body().getMessage());
    }

    @Override
    public void onFailure(Call<ResponseModel<T>> call, Throwable t) {
        onFailure(t.getLocalizedMessage());
    }

    public abstract void onSuccess(T response);

    public void onSuccess(T response, Pagination meta) {}

    public abstract void onFailure(String message);

}
