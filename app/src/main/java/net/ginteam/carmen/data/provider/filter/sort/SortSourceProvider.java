package net.ginteam.carmen.data.provider.filter.sort;

import net.ginteam.carmen.data.provider.DataSourceProvider;
import net.ginteam.carmen.data.remote.api.task.Callback;
import net.ginteam.carmen.model.SortingModel;

import java.util.List;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public interface SortSourceProvider extends DataSourceProvider {

    void fetchSortForCategory(int categoryId, final SortCallback callback);

    abstract class SortCallback extends Callback <List<SortingModel>> {}

}
