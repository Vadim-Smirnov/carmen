package net.ginteam.carmen.manager;

import net.ginteam.carmen.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eugene on 12/23/16.
 */

public class ApiManager {

    private static ApiManager sInstance;

    private Retrofit mRetrofit;

    private ApiManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiManager getInstance() {
        if (sInstance == null) {
            sInstance = new ApiManager();
        }
        return sInstance;
    }

    public <T> T getService(Class <T> serviceClass) {
        return mRetrofit.create(serviceClass);
    }

}
