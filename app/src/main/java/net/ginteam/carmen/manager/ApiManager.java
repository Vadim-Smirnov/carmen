package net.ginteam.carmen.manager;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import net.ginteam.carmen.BuildConfig;
import net.ginteam.carmen.network.api.ApiLinks;
import net.ginteam.carmen.utils.ValidationUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eugene on 12/23/16.
 */

public class ApiManager {

    private final int REQUEST_TIMEOUT_SECONDS = 15;

    private static ApiManager sInstance;

    private Retrofit mRetrofit;

    private ApiManager() {
        OkHttpClient httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new AuthorizationTokenInterceptor())
                .connectTimeout(REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    public static ApiManager getInstance() {
        if (sInstance == null) {
            sInstance = new ApiManager();
        }
        return sInstance;
    }

    public <T> T getService(Class<T> serviceClass) {
        return mRetrofit.create(serviceClass);
    }

    private class AuthorizationTokenInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request apiRequest = chain.request();

            Log.d("HttpInterceptor", "Request URL: " + apiRequest.url().toString());

            Request.Builder builder = apiRequest.newBuilder();
            String authToken = PreferencesManager.getInstance().getUserToken();
            if (!authToken.isEmpty()) {
                Log.d("HttpInterceptor", "Auth token: " + authToken);

                builder.addHeader(ApiLinks.AUTH.AUTH_HEADER, "Bearer " + authToken);
            }

            LatLng userLocation = PreferencesManager.getInstance().getUserLocation();
            ValidationUtils.Pair<String, String> pair = ValidationUtils.validateUserLocation(userLocation);
            builder.addHeader(ApiLinks.CATALOG.LAT, pair.first).addHeader(ApiLinks.CATALOG.LNG, pair.second);

            apiRequest = builder.build();
            Log.d("HttpInterceptor", "Request headers: " + apiRequest.headers().toString());

            return chain.proceed(apiRequest);
        }

    }

}
