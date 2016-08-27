package com.marco.mvp.views.login;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.marco.mvp.R;
import com.marco.mvp.util.Constants;
import com.marco.mvp.views.BaseFragment;
import com.marco.mvp.views.home.HomeActivity;

public class LoginFragment extends BaseFragment<LoginContract.Presenter> implements LoginContract.View, View.OnClickListener,
        DialogInterface.OnCancelListener {

    private static final String TAG_FORGOT_PASSWORD_DIALOG = "Forgot Password";
    private static final int REQUEST_CODE_FORGOT_PASSWORD = 0;


    private LoginContract.Presenter presenter;

    private TextInputLayout textInputLayout_username;
    private EditText editText_username;
    private TextInputLayout textInputLayout_password;
    private EditText editText_password;

    private CheckBox checkBox_rememberMe;
    private Button button_forgotPassword;
    private Button button_login;

    private ProgressDialog progressDialog;
    private ForgotPasswordDialog forgotPasswordDialog;


    @Override
    public LoginContract.Presenter getPresenter() {
        if (presenter == null) {
            presenter = new LoginPresenter(this);
        }

        return presenter;
    }


    @Override
    public int getViewResource() {
        return R.layout.fragment_login;
    }


    @Override
    public void onDestroyView() {
        presenter.viewsDestroyed();

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        presenter.viewDestroyed();

        super.onDestroy();
    }


    @Override
    public void initViews(View rootView) {
        textInputLayout_username = (TextInputLayout) rootView.findViewById(R.id.textInputLayout_username);
        textInputLayout_password = (TextInputLayout) rootView.findViewById(R.id.textInputLayout_password);
        editText_username = (EditText) rootView.findViewById(R.id.editText_username);
        editText_password = (EditText) rootView.findViewById(R.id.editText_password);
        checkBox_rememberMe = (CheckBox) rootView.findViewById(R.id.checkBox_rememberMe);
        button_forgotPassword = (Button) rootView.findViewById(R.id.button_forgotPassword);
        button_login = (Button) rootView.findViewById(R.id.button_login);

        editText_username.addTextChangedListener(new CustomTextWatcher(editText_username));
        editText_password.addTextChangedListener(new CustomTextWatcher(editText_password));

        button_forgotPassword.setOnClickListener(this);
        button_login.setOnClickListener(this);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setOnCancelListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        forgotPasswordDialog = (ForgotPasswordDialog) fragmentManager.findFragmentByTag(TAG_FORGOT_PASSWORD_DIALOG);

        if (forgotPasswordDialog == null) {
            forgotPasswordDialog = new ForgotPasswordDialog();
        }
    }


    @Override
    public void onClick(View view) {
        if (view == button_forgotPassword) {
            presenter.forgotPasswordClicked();
        } else if (view == button_login) {
            presenter.loginClicked(checkBox_rememberMe.isChecked());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FORGOT_PASSWORD) {
            if (resultCode == Activity.RESULT_OK) {
                String username = data.getStringExtra(Constants.EXTRA_USERNAME);
                presenter.sendNewPasswordClicked(username);
            }
        }
    }


    @Override
    public void onCancel(DialogInterface dialogInterface) {
        presenter.progressCancelled();
    }


    @Override
    public void setLoginButtonEnabled(boolean enabled) {
        button_login.setEnabled(enabled);
    }


    @Override
    public void displayUsernameError(String message) {
        textInputLayout_username.setError(message);
    }

    @Override
    public void displayPasswordError(String message) {
        textInputLayout_password.setError(message);
    }

    @Override
    public void clearErrors() {
        textInputLayout_username.setErrorEnabled(false);
        textInputLayout_password.setErrorEnabled(false);
    }


    @Override
    public void displayCheckConnectionMessage() {
        Toast.makeText(getActivity(), R.string.check_connection, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void displayForgotPasswordDialog() {
        forgotPasswordDialog.setTargetFragment(this, REQUEST_CODE_FORGOT_PASSWORD);
        forgotPasswordDialog.show(getFragmentManager(), TAG_FORGOT_PASSWORD_DIALOG);
    }

    @Override
    public void hideForgotPasswordDialog() {
        forgotPasswordDialog.dismiss();
    }


    @Override
    public void displayEmailSentMessage() {
        Toast.makeText(getActivity(), R.string.email_sent, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayUsernameDoesNotExistMessage() {
        Toast.makeText(getActivity(), R.string.username_does_not_exist, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void openHomeActivity() {
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    @Override
    public void displayWelcomeMessage() {
        Toast.makeText(getActivity(), R.string.welcome_back, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayInvalidLoginMessage() {
        Toast.makeText(getActivity(), R.string.invalid_login, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayConnectionErrorMessage() {
        Toast.makeText(getActivity(), R.string.connection_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayErrorMessage() {
        Toast.makeText(getActivity(), R.string.error_occurred, Toast.LENGTH_SHORT).show();
    }


    // Custom Text Watcher
    private class CustomTextWatcher implements TextWatcher {

        private EditText editText;

        public CustomTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String text = editText.getText().toString().trim();

            if (editText == editText_username) {
                presenter.usernameChanged(text);
            } else if (editText == editText_password) {
                presenter.passwordChanged(text);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }

    }

}
