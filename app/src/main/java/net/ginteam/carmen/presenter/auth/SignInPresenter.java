package net.ginteam.carmen.presenter.auth;

import android.content.Intent;

import net.ginteam.carmen.contract.auth.SignInContract;
import net.ginteam.carmen.model.auth.UserModel;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.auth.AuthProvider;

/**
 * Created by Eugene on 1/4/17.
 */

public class SignInPresenter implements SignInContract.Presenter {

    private SignInContract.View mView;

    @Override
    public void signIn(String email, String password) {
        mView.showLoading(true);

        AuthProvider
                .getInstance()
                .userLogin(email, password, new ModelCallback<UserModel>() {
                    @Override
                    public void onSuccess(UserModel resultModel) {
                        mView.showLoading(false);
                        mView.showMain();
                    }

                    @Override
                    public void onFailure(String message) {
                        mView.showLoading(false);
                        mView.showError(message);
                    }
                });
    }

    @Override
    public void requestGoogleAuthentication() {

    }

    @Override
    public void requestFacebookAuthentication() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void attachView(SignInContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

}
