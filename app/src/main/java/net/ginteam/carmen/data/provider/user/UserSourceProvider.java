package net.ginteam.carmen.data.provider.user;

import net.ginteam.carmen.data.provider.DataSourceProvider;
import net.ginteam.carmen.data.remote.api.task.Callback;
import net.ginteam.carmen.model.auth.AuthModel;
import net.ginteam.carmen.model.auth.UserModel;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public interface UserSourceProvider extends DataSourceProvider {

    void login(String email, String password, final AuthenticationCallback callback);

    void register(String name, String email, String password, final AuthenticationCallback callback);

    void getCurrentUser(final UserCallback callback);

    abstract class AuthenticationCallback extends Callback <AuthModel> {}

    abstract class UserCallback extends Callback <UserModel> {}

}
