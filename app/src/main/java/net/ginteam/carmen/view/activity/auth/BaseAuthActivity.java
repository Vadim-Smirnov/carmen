package net.ginteam.carmen.view.activity.auth;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import net.ginteam.carmen.R;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseAuthActivity extends AppCompatActivity implements Validator.ValidationListener {

    private Toolbar mToolbar;

    protected ProgressBar mProgressBar;

    @Order(1)
    @Email(messageResId = R.string.wrong_email_string)
    protected EditText mEditTextEmail;

    @Order(2)
    @Password(min = 6, messageResId = R.string.wrong_password_string)
    protected EditText mEditTextPassword;

    protected Validator mFieldsValidator;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initializeToolbar();
        updateDependencies();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.auth_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public AppCompatActivity getActivity() {
        return this;
    }

    public Context getContext() {
        return getActivity();
    }

    public void showLoading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public abstract void onValidationSucceeded();

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ValidationError firstError = errors.get(0);
        showError(firstError.getCollatedErrorMessage(this));
        firstError.getView().requestFocus();
    }

    protected void updateDependencies() {
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mEditTextEmail = (EditText) findViewById(R.id.edit_text_email);
        mEditTextPassword = (EditText) findViewById(R.id.edit_text_password);

        mFieldsValidator = new Validator(this);
        mFieldsValidator.setValidationListener(this);
    }

    private void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.auth_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

}
