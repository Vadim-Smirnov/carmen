package net.ginteam.carmen.contract.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import net.ginteam.carmen.contract.BaseContract;
import net.ginteam.carmen.contract.FetchContract;

/**
 * Created by Eugene on 1/4/17.
 */

public interface SignInContract {

    interface View extends FetchContract.View {

        AppCompatActivity getActivity();

        void showMain();

    }

    interface Presenter extends BaseContract.Presenter<View> {

        void signIn(String email, String password);

        void requestGoogleAuthentication();

        void requestFacebookAuthentication();

        void onActivityResult(int requestCode, int resultCode, Intent data);

    }

}
