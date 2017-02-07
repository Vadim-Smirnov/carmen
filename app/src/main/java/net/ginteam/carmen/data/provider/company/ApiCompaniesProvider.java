package net.ginteam.carmen.data.provider.company;

import com.google.android.gms.maps.model.LatLng;

import net.ginteam.carmen.data.remote.api.request.CompanyApi;
import net.ginteam.carmen.manager.ApiManager;
import net.ginteam.carmen.manager.PreferencesManager;
import net.ginteam.carmen.utils.ValidationUtils;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public class ApiCompaniesProvider implements CompaniesSourceProvider {

    private CompanyApi mCompanyApi = ApiManager.getInstance().getService(CompanyApi.class);
    private LatLng mUserLocation = PreferencesManager.getInstance().getUserLocation();

    @Override
    public void fetchCompanies(int categoryId, String filter, String sortField, String sortType,
                               int page, PaginationCompaniesCallback callback) {
        ValidationUtils.Pair <String, String> location = ValidationUtils.validateUserLocation(mUserLocation);
        String cityBounds = PreferencesManager.getInstance().getCity().getBounds();

        mCompanyApi.fetchCompanies(categoryId, location.first, location.second, cityBounds, filter,
                sortField, sortType, page).enqueue(callback);
    }

    @Override
    public void fetchCompanies(int categoryId, String filter, String bounds, MapCompaniesCallback callback) {
        mCompanyApi.fetchCompanies(categoryId, filter, bounds).enqueue(callback);
    }

    @Override
    public void fetchCompanyDetail(int companyId, String lat, String lng, String relations, CompanyCallback callback) {
        mCompanyApi.fetchCompanyDetail(companyId, lat, lng, relations).enqueue(callback);
    }

    @Override
    public void fetchPopular(int cityId, String lat, String lng, CompaniesCallback callback) {
        mCompanyApi.fetchPopular(cityId, lat, lng).enqueue(callback);
    }

    @Override
    public void fetchRecentlyWatched(String lat, String lng, CompaniesCallback callback) {
        mCompanyApi.fetchRecentlyWatched(lat, lng).enqueue(callback);
    }

}
