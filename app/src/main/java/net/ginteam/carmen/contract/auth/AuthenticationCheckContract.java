package net.ginteam.carmen.contract.auth;

import net.ginteam.carmen.contract.RequestLocationContract;

/**
 * Created by Eugene on 12/30/16.
 */

public interface AuthenticationCheckContract {

    interface View extends RequestLocationContract.View {

        void showAuthenticationView();

        void showMainView();

    }

    interface Presenter extends RequestLocationContract.Presenter <View> {

        void checkAuthentication();

        void fetchCurrentUser();

    }

}
