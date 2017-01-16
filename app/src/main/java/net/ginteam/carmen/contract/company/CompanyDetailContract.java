package net.ginteam.carmen.contract.company;

import net.ginteam.carmen.contract.FetchContract;
import net.ginteam.carmen.model.company.CompanyModel;

/**
 * Created by vadik on 16.01.17.
 */

public interface CompanyDetailContract {

    interface View extends FetchContract.View {

        void showCompanyDetail(CompanyModel companyModel);

    }

    interface Presenter extends FetchContract.Presenter<View> {

        void fetchCompanyDetail(int companyId);

    }

}
