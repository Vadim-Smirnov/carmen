package net.ginteam.carmen.network.api.service;

import net.ginteam.carmen.model.ResponseModel;
import net.ginteam.carmen.model.auth.AuthModel;
import net.ginteam.carmen.model.auth.UserModel;
import net.ginteam.carmen.network.api.ApiLinks;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Eugene on 12/28/16.
 */

public interface AuthService {

    @POST(ApiLinks.AUTH.LOGIN)
    Observable <ResponseModel<AuthModel>> login(
            @Query(ApiLinks.AUTH.EMAIL) String email,
            @Query(ApiLinks.AUTH.PASSWORD) String password
    );

    @POST(ApiLinks.AUTH.REGISTER)
    Observable <ResponseModel<AuthModel>> register(
            @Query(ApiLinks.AUTH.NAME) String name,
            @Query(ApiLinks.AUTH.EMAIL) String email,
            @Query(ApiLinks.AUTH.PASSWORD) String password
    );

    @GET(ApiLinks.AUTH.GET_CURRENT_USER)
    Observable <ResponseModel<UserModel>> getCurrentUser();

}
