package net.ginteam.carmen.data.provider.user;

import net.ginteam.carmen.data.remote.api.request.UserApi;
import net.ginteam.carmen.manager.ApiManager;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public class ApiUserProvider implements UserSourceProvider {

    private UserApi mUserApi = ApiManager.getInstance().getService(UserApi.class);

    @Override
    public void login(String email, String password, AuthenticationCallback callback) {
        mUserApi.login(email, password).enqueue(callback);
    }

    @Override
    public void register(String name, String email, String password, AuthenticationCallback callback) {
        mUserApi.register(name, email, password).enqueue(callback);
    }

    @Override
    public void getCurrentUser(UserCallback callback) {
        mUserApi.getCurrentUser().enqueue(callback);
    }
}
