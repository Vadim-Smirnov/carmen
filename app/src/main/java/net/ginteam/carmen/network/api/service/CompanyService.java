package net.ginteam.carmen.network.api.service;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.network.api.ApiLinks;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Eugene on 12/27/16.
 */

public interface CompanyService {

    @GET(ApiLinks.CATALOG.COMPANIES_BY_CATEGORY)
    Observable <ResponseModel <List <CompanyModel>>> fetchCompanies(@Path(ApiLinks.CATALOG.ID) int categoryId);

    @GET(ApiLinks.CATALOG.COMPANY_BY_ID)
    Observable<ResponseModel<CompanyModel>> fetchCompanyDetail(
            @Path(ApiLinks.CATALOG.ID) int companyId,
            @Query(ApiLinks.CATALOG.WITH) String relations
    );

}
