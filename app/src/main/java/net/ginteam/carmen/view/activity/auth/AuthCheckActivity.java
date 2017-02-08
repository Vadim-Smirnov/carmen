package net.ginteam.carmen.view.activity.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.auth.AuthenticationCheckContract;
import net.ginteam.carmen.manager.PreferencesManager;
import net.ginteam.carmen.model.city.CityModel;
import net.ginteam.carmen.presenter.auth.AuthenticationCheckPresenter;
import net.ginteam.carmen.utils.ActivityUtils;
import net.ginteam.carmen.utils.NetworkUtils;
import net.ginteam.carmen.view.activity.MainActivity;
import net.ginteam.carmen.view.fragment.city.CityListFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AuthCheckActivity extends AppCompatActivity implements AuthenticationCheckContract.View,
        CityListFragment.OnCitySelectedListener{

    private AuthenticationCheckContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new AuthenticationCheckPresenter();
        mPresenter.attachView(this);
        mPresenter.checkAuthentication();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void showAuthenticationView() {
        Log.d("AuthCheck", "AUTH ACTIVITY SHOW");
        ActivityUtils.showActivity(SignInActivity.class, null, true);
        finish();
    }

    @Override
    public void showCityListView() {
        Log.d("AuthCheck", "CITY LIST ACTIVITY SHOW");
        DialogFragment newFragment = CityListFragment.newInstance();
        newFragment.show(getSupportFragmentManager(), "dialog");

    }

    @Override
    public void showMainView() {
        ActivityUtils.showActivity(MainActivity.class, null, true);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public AppCompatActivity getActivity() {
        return this;
    }

    @Override
    public void onCitySelected(CityModel city) {
        mPresenter.fetchCurrentUser();
        PreferencesManager.getInstance().setUserLocation("");
    }
}
