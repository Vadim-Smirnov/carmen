package net.ginteam.carmen.data.provider.company;

import net.ginteam.carmen.data.provider.DataSourceProvider;
import net.ginteam.carmen.data.remote.api.task.Callback;
import net.ginteam.carmen.model.Pagination;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.model.company.MapCompanyModel;

import java.util.List;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public interface CompaniesSourceProvider extends DataSourceProvider {

    void fetchCompanies(int categoryId,
                        String filter,
                        String sortField, String sortType,
                        int page, final PaginationCompaniesCallback callback);

    void fetchCompanies(int categoryId, String filter, String bounds, final MapCompaniesCallback callback);

    void fetchCompanyDetail(int companyId, String lat, String lng, String relations, final CompanyCallback callback);

    void fetchPopular(int cityId, String lat, String lng, final CompaniesCallback callback);

    void fetchRecentlyWatched(String lat, String lng, final CompaniesCallback callback);

    abstract class CompanyCallback extends Callback <CompanyModel> {}

    abstract class MapCompaniesCallback extends Callback <List<MapCompanyModel>> {}

    abstract class CompaniesCallback extends Callback <List<CompanyModel>> {}

    abstract class PaginationCompaniesCallback extends Callback <List<CompanyModel>> {

        @Override
        abstract public void onSuccess(List<CompanyModel> response, Pagination meta);

        @Override
        public void onSuccess(List<CompanyModel> response) {

        }

    }

}
