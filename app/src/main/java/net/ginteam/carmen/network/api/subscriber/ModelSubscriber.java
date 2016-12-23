package net.ginteam.carmen.network.api.subscriber;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.provider.ModelCallback;

import rx.Subscriber;

/**
 * Created by Eugene on 12/23/16.
 */

public abstract class ModelSubscriber <T>
        extends Subscriber <ResponseModel <T>> implements ModelCallback <T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        onFailure(e.getLocalizedMessage());
    }

    @Override
    public void onNext(ResponseModel <T> responseModel) {
        if (responseModel.isSuccess()) {
            onSuccess(responseModel.getData());
            return;
        }
        onFailure(responseModel.getMessage());
    }

    abstract public void onSuccess(T resultModel);

    abstract public void onFailure(String message);

}
