package net.ginteam.carmen.view.activity.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import net.ginteam.carmen.R;
import net.ginteam.carmen.contract.auth.SignUpContract;
import net.ginteam.carmen.presenter.auth.SignUpPresenter;
import net.ginteam.carmen.utils.ActivityUtils;
import net.ginteam.carmen.view.activity.MainActivity;

public class SignUpActivity extends BaseAuthActivity implements View.OnClickListener, SignUpContract.View {

    private SignUpContract.Presenter mPresenter;

    @Order(4)
    @Pattern(regex = "^(([\\p{L}]+){1}( ?[\\p{L}]+)?){1}$", messageResId = R.string.wrong_name_string)
    private EditText mEditTextName;

    @Order(3)
    @ConfirmPassword(messageResId = R.string.wrong_password_confirmation_string)
    private EditText mEditTextConfirmPassword;

    private Button mButtonSignUp;
    private TextView mTextViewSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_sign_up);

        mPresenter = new SignUpPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sign_up:
                mFieldsValidator.validate();
                break;
            case R.id.text_view_sign_in:
                onBackPressed();
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
        mPresenter.signUp(
                mEditTextName.getText().toString(),
                mEditTextEmail.getText().toString(),
                mEditTextPassword.getText().toString()
        );
    }

    @Override
    protected void updateDependencies() {
        super.updateDependencies();

        mEditTextName = (EditText) findViewById(R.id.edit_text_name);
        mEditTextConfirmPassword = (EditText) findViewById(R.id.edit_text_confirm_password);
        mButtonSignUp = (Button) findViewById(R.id.button_sign_up);
        mTextViewSignIn = (TextView) findViewById(R.id.text_view_sign_in);

        mButtonSignUp.setOnClickListener(this);
        mTextViewSignIn.setOnClickListener(this);
    }

}
