package net.ginteam.carmen.presenter.auth;

import android.util.Log;

import net.ginteam.carmen.contract.auth.SignUpContract;
import net.ginteam.carmen.model.auth.UserModel;
import net.ginteam.carmen.provider.ModelCallback;
import net.ginteam.carmen.provider.auth.AuthProvider;

/**
 * Created by Eugene on 1/4/17.
 */

public class SignUpPresenter implements SignUpContract.Presenter {

    private SignUpContract.View mView;

    @Override
    public void signUp(String name, String email, String password) {
        mView.showLoading(true);

        AuthProvider
                .getInstance()
                .userRegister(name, email, password, new ModelCallback<UserModel>() {
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
    public void attachView(SignUpContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

}
