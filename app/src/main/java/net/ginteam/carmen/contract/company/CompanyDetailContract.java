package net.ginteam.carmen.contract.company;

import android.support.annotation.IdRes;
import android.view.Menu;

import net.ginteam.carmen.contract.FetchContract;
import net.ginteam.carmen.model.company.CompanyModel;
import net.ginteam.carmen.view.fragment.BaseFetchingFragment;

/**
 * Created by vadik on 16.01.17.
 */

public interface CompanyDetailContract {

    interface View extends FetchContract.View {

        void showCompanyDetail(CompanyModel companyModel);

        void openNavigator();

        void showMap();

        void showFragment(@IdRes int containerId, BaseFetchingFragment fragment);

        void call();

    }

    interface Presenter extends FetchContract.Presenter<View> {

        void fetchCompanyDetail(int companyId);

        void onClick(android.view.View selectedView);

        void addToFavoriteClick(CompanyModel companyModel, Menu menu);

        void addToFavorite(CompanyModel companyModel);

        void removeFromFavorite(CompanyModel companyModel);

    }

}
