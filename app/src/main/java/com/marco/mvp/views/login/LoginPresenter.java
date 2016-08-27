package com.marco.mvp.views.login;

import com.marco.mvp.R;
import com.marco.mvp.model.beans.User;
import com.marco.mvp.model.source.AccountDataSource;
import com.marco.mvp.model.source.AccountRepository;
import com.marco.mvp.util.Constants;
import com.marco.mvp.util.PreferencesHandler;
import com.marco.mvp.util.Utils;
import com.marco.mvp.views.BasePresenter;

import java.io.IOException;

class LoginPresenter extends BasePresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private AccountDataSource dataSource;

    private OnLoginListener onLoginListener;
    private OnSendNewPasswordListener onSendNewPasswordListener;

    private String username;
    private String password;

    private boolean loading;


    // Constructor
    public LoginPresenter(LoginContract.View view) {
        super(view);

        this.view = view;
        dataSource = new AccountRepository();
    }


    @Override
    public void viewCreated() {
        super.viewCreated();

        try {
            User user = PreferencesHandler.loadUser(view.getContext());
            boolean userRemembered = PreferencesHandler.isUserRemembered(view.getContext());
            if (user != null && userRemembered) {
                view.openHomeActivity();
                view.finish();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewsCreated() {
        super.viewsCreated();

        updateLoginButton();

        if (loading) {
            view.showProgress();
        }
    }

    @Override
    public void viewsDestroyed() {
        if (loading) {
            view.hideProgress();
        }

        super.viewsDestroyed();
    }

    @Override
    public void viewDestroyed() {
        progressCancelled();

        super.viewDestroyed();
    }


    @Override
    public void usernameChanged(String username) {
        this.username = username.trim();
        updateLoginButton();
    }

    @Override
    public void passwordChanged(String password) {
        this.password = password.trim();
        updateLoginButton();
    }


    @Override
    public void loginClicked(boolean rememberUser) {
        Utils.hideKeyboard(view.getActivity());

        if (Utils.isConnectedToNetwork(view.getContext())) {
            view.clearErrors();
            if (validateInputs()) {
                loading = true;
                view.showProgress();

                onLoginListener = new OnLoginListener(rememberUser);
                dataSource.authenticate(username, password, onLoginListener);
            }
        } else {
            view.displayCheckConnectionMessage();
        }
    }

    @Override
    public void progressCancelled() {
        loading = false;

        dataSource.cancelTasks();

        if (onLoginListener != null) {
            onLoginListener.cancel();
        }

        if (onSendNewPasswordListener != null) {
            onSendNewPasswordListener.cancel();
        }
    }


    @Override
    public void forgotPasswordClicked() {
        view.displayForgotPasswordDialog();
    }

    @Override
    public void sendNewPasswordClicked(String username) {
        if (Utils.isConnectedToNetwork(view.getContext())) {
            loading = true;
            view.showProgress();

            onSendNewPasswordListener = new OnSendNewPasswordListener();
            dataSource.sendNewPasswordRequest(username, onSendNewPasswordListener);
        } else {
            view.displayCheckConnectionMessage();
        }
    }


    // Update Login Button
    private void updateLoginButton() {
        view.setLoginButtonEnabled(username != null && !username.isEmpty() && password != null && !password.isEmpty());
    }


    // Validate Inputs
    private boolean validateInputs() {
        boolean valid = true;

        if (username.length() < Constants.USERNAME_MINIMUM_CHARACTERS) {
            valid = false;
            String message = view.getContext().getString(R.string.minimum_characters);
            view.displayUsernameError(String.format(message, Constants.USERNAME_MINIMUM_CHARACTERS));
        }

        if (password.length() < Constants.PASSWORD_MINIMUM_CHARACTERS) {
            valid = false;
            String message = view.getContext().getString(R.string.minimum_characters);
            view.displayPasswordError(String.format(message, Constants.PASSWORD_MINIMUM_CHARACTERS));
        }

        return valid;
    }


    // On Login Listener
    private class OnLoginListener implements AccountDataSource.LoginCallback {

        private boolean cancelled;
        private boolean rememberUser;

        public OnLoginListener(boolean rememberUser) {
            this.rememberUser = rememberUser;
        }

        public void cancel() {
            cancelled = true;
        }


        @Override
        public void onSuccess(User user) {
            if (!cancelled) {
                loading = false;

                try {
                    PreferencesHandler.saveUser(view.getContext(), user);
                    PreferencesHandler.setRememberUser(view.getContext(), rememberUser);

                    view.openHomeActivity();
                    view.displayWelcomeMessage();
                    view.hideProgress();
                    view.finish();
                } catch (IOException e) {
                    e.printStackTrace();

                    view.displayErrorMessage();
                    view.hideProgress();
                }
            }
        }

        @Override
        public void onFailure() {
            if (!cancelled) {
                loading = false;

                view.displayInvalidLoginMessage();
                view.hideProgress();
            }
        }

        @Override
        public void onConnectionFailure() {
            if (!cancelled) {
                loading = false;

                view.displayConnectionErrorMessage();
                view.hideProgress();
            }
        }
    }


    // On Send New Password Listener
    private class OnSendNewPasswordListener implements AccountDataSource.SendNewPasswordCallback {

        private boolean cancelled;

        public void cancel() {
            cancelled = true;
        }


        @Override
        public void onSuccess() {
            if (!cancelled) {
                loading = false;

                view.displayEmailSentMessage();
                view.hideForgotPasswordDialog();
                view.hideProgress();
            }
        }

        @Override
        public void onFailure() {
            if (!cancelled) {
                loading = false;

                view.displayUsernameDoesNotExistMessage();
                view.hideProgress();
            }
        }

        @Override
        public void onConnectionFailure() {
            if (!cancelled) {
                loading = false;

                view.displayConnectionErrorMessage();
                view.hideProgress();
            }
        }

    }

}
