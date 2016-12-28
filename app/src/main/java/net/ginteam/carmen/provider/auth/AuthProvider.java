package net.ginteam.carmen.provider.auth;

import net.ginteam.carmen.manager.ApiManager;
import net.ginteam.carmen.manager.PreferencesManager;
import net.ginteam.carmen.model.auth.AuthModel;
import net.ginteam.carmen.model.auth.UserModel;
import net.ginteam.carmen.network.api.service.AuthService;
import net.ginteam.carmen.network.api.subscriber.ModelSubscriber;
import net.ginteam.carmen.provider.ModelCallback;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Eugene on 12/28/16.
 */

public class AuthProvider {

    private static AuthProvider sInstance;

    private AuthService mAuthService;
    private PreferencesManager mPreferencesManager;
    private UserModel mCachedUser;

    private AuthProvider() {
        mAuthService = ApiManager.getInstance().getService(AuthService.class);
        mPreferencesManager = PreferencesManager.getInstance();
    }

    public static AuthProvider getInstance() {
        if (sInstance == null) {
            sInstance = new AuthProvider();
        }
        return sInstance;
    }

    public UserModel getCurrentCachedUser() {
        return mCachedUser;
    }

    public void userLogin(final String email, final String password, final ModelCallback<UserModel> completion) {
        mAuthService
                .login(email, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<AuthModel>() {
                    @Override
                    public void onSuccess(AuthModel resultModel) {
                        mCachedUser = resultModel.getUser();
                        mPreferencesManager.setUserToken(resultModel.getToken());
                        completion.onSuccess(mCachedUser);
                    }

                    @Override
                    public void onFailure(String message) {
                        mCachedUser = null;
                        completion.onFailure(message);
                    }
                });
    }

    public void userRegister(String name, final String email, final String password, final ModelCallback<UserModel> completion) {
        mAuthService
                .register(name, email, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<AuthModel>() {
                    @Override
                    public void onSuccess(AuthModel resultModel) {
                        mCachedUser = resultModel.getUser();
                        mPreferencesManager.setUserToken(resultModel.getToken());
                        completion.onSuccess(mCachedUser);
                    }

                    @Override
                    public void onFailure(String message) {
                        mCachedUser = null;
                        completion.onFailure(message);
                    }
                });
    }

    public void fetchCurrentUser(final ModelCallback<UserModel> completion) {
        mAuthService
                .getCurrentUser()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ModelSubscriber<UserModel>() {
                    @Override
                    public void onSuccess(UserModel resultModel) {
                        mCachedUser = resultModel;
                        completion.onSuccess(mCachedUser);
                    }

                    @Override
                    public void onFailure(String message) {
                        completion.onFailure(message);
                    }
                });
    }

}
