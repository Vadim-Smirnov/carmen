package net.ginteam.carmen.view.activity.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.auth.SignInContract;
import net.ginteam.carmen.presenter.auth.SignInPresenter;
import net.ginteam.carmen.utils.ActivityUtils;
import net.ginteam.carmen.view.activity.MainActivity;

public class SignInActivity extends BaseAuthActivity implements SignInContract.View, View.OnClickListener {

    private SignInContract.Presenter mPresenter;

    private Button mButtonSignIn;
    private Button mButtonSignInWithFacebook;
    private Button mButtonSignInWithGoogle;

    private TextView mTextViewSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_sign_in);

        mPresenter = new SignInPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sign_in:
                mFieldsValidator.validate();
                break;
            case R.id.text_view_sign_up:
                ActivityUtils.showActivity(SignUpActivity.class, null, false);
                break;
        }
    }

    @Override
    public void showMain() {
        ActivityUtils.showActivity(MainActivity.class, null, true);
        finish();
    }

    @Override
    public void onValidationSucceeded() {
        mPresenter.signIn(mEditTextEmail.getText().toString(), mEditTextPassword.getText().toString());
    }

    @Override
    protected void updateDependencies() {
        super.updateDependencies();

        mButtonSignIn = (Button) findViewById(R.id.button_sign_in);
        mButtonSignInWithFacebook = (Button) findViewById(R.id.button_sign_in_facebook);
        mButtonSignInWithGoogle = (Button) findViewById(R.id.button_sign_in_google);
        mTextViewSignUp = (TextView) findViewById(R.id.text_view_sign_up);

        mButtonSignIn.setOnClickListener(this);
        mButtonSignInWithFacebook.setOnClickListener(this);
        mButtonSignInWithGoogle.setOnClickListener(this);
        mTextViewSignUp.setOnClickListener(this);
    }

}
