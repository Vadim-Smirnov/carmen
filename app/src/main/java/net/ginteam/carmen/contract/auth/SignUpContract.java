package net.ginteam.carmen.contract.auth;

import net.ginteam.carmen.contract.BaseContract;
import net.ginteam.carmen.contract.FetchContract;

/**
 * Created by Eugene on 1/4/17.
 */

public interface SignUpContract {

    interface View extends FetchContract.View {

        void showMain();

    }

    interface Presenter extends BaseContract.Presenter<View> {

        void signUp(String name, String email, String password);

    }

}
