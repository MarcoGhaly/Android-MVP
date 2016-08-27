package com.marco.mvp.views.login;

import com.marco.mvp.views.BaseContract;

interface LoginContract {

    interface View extends BaseContract.View<Presenter> {

        void setLoginButtonEnabled(boolean enabled);

        void displayUsernameError(String message);

        void displayPasswordError(String message);

        void clearErrors();

        void displayCheckConnectionMessage();

        void showProgress();

        void hideProgress();

        void displayForgotPasswordDialog();

        void hideForgotPasswordDialog();

        void displayEmailSentMessage();

        void displayUsernameDoesNotExistMessage();

        void openHomeActivity();

        void displayWelcomeMessage();

        void displayInvalidLoginMessage();

        void displayConnectionErrorMessage();

        void displayErrorMessage();

    }

    interface Presenter extends BaseContract.Presenter {

        void usernameChanged(String username);

        void passwordChanged(String password);

        void loginClicked(boolean rememberUser);

        void progressCancelled();

        void forgotPasswordClicked();

        void sendNewPasswordClicked(String username);

    }

}
