package net.ginteam.carmen.network.api.subscriber;

import net.ginteam.carmen.model.Pagination;
import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.provider.ModelCallbackWithMeta;

import rx.Subscriber;

/**
 * Created by Eugene on 1/15/17.
 */

public abstract class ModelSubscriberWithMeta <T>
        extends Subscriber<ResponseModel <T>> implements ModelCallbackWithMeta <T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        onFailure(e.getLocalizedMessage());
    }

    @Override
    public void onNext(ResponseModel<T> responseModel) {
        if (responseModel.isSuccess()) {
            onSuccess(responseModel.getData(), responseModel.getMetaData().getPagination());
            return;
        }
        onFailure(responseModel.getMessage());
    }

    abstract public void onSuccess(T resultModel, Pagination pagination);

    abstract public void onFailure(String message);

}
