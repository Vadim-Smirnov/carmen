package net.ginteam.carmen.view.activity.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.auth.AuthenticationCheckContract;
import net.ginteam.carmen.presenter.auth.AuthenticationCheckPresenter;
import net.ginteam.carmen.utils.ActivityUtils;
import net.ginteam.carmen.view.activity.MainActivity;

public class AuthCheckActivity extends AppCompatActivity implements AuthenticationCheckContract.View {

    private AuthenticationCheckContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_check);

        mPresenter = new AuthenticationCheckPresenter();
        mPresenter.attachView(this);
        mPresenter.checkAuthentication();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
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
        Toast.makeText(getContext(), "CITY LIST ACTIVITY SHOW", Toast.LENGTH_LONG).show();
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

}
