package net.ginteam.carmen.provider;

import net.ginteam.carmen.model.Pagination;

/**
 * Created by Eugene on 1/15/17.
 */

public interface ModelCallbackWithMeta <T> {

    void onSuccess(T resultModel, Pagination pagination);

    void onFailure(String message);

}