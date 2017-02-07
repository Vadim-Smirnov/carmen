package net.ginteam.carmen.data.provider.filter;

import net.ginteam.carmen.data.provider.DataSourceProvider;
import net.ginteam.carmen.data.provider.company.CompaniesSourceProvider;
import net.ginteam.carmen.data.remote.api.task.Callback;
import net.ginteam.carmen.model.filter.FilterModel;

import java.util.List;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public interface FiltersSourceProvider extends DataSourceProvider {

    void fetchFiltersForCategory(int categoryId, final FiltersCallback callback);

    void getCompaniesCountWithFilter(int categoryId, String filter, final FilterableCountCallback callback);

    abstract class FiltersCallback extends Callback <List<FilterModel>> {}

    abstract class FilterableCountCallback extends CompaniesSourceProvider.PaginationCompaniesCallback {}

}
