package net.ginteam.carmen.data.remote.api.request;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.auth.AuthModel;
import net.ginteam.carmen.model.auth.UserModel;
import net.ginteam.carmen.data.remote.api.ApiLinks;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by eugene_shcherbinock on 2/7/17.
 */

public interface UserApi {

    @POST(ApiLinks.AUTH.LOGIN)
    Call<ResponseModel<AuthModel>> login(
            @Query(ApiLinks.AUTH.EMAIL) String email,
            @Query(ApiLinks.AUTH.PASSWORD) String password
    );

    @POST(ApiLinks.AUTH.REGISTER)
    Call<ResponseModel<AuthModel>> register(
            @Query(ApiLinks.AUTH.NAME) String name,
            @Query(ApiLinks.AUTH.EMAIL) String email,
            @Query(ApiLinks.AUTH.PASSWORD) String password
    );

    @GET(ApiLinks.AUTH.GET_CURRENT_USER)
    Call<ResponseModel<UserModel>> getCurrentUser();

}
