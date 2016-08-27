package com.marco.mvp.views;

import com.marco.mvp.util.PreferencesHandler;

public class BasePresenter implements BaseContract.Presenter {

    private BaseContract.View view;


    // Constructor
    public BasePresenter(BaseContract.View view) {
        this.view = view;
    }


    @Override
    public void viewCreated() {
    }

    @Override
    public void viewsCreated() {
    }

    @Override
    public void viewsDestroyed() {
    }

    @Override
    public void viewDestroyed() {
    }


    @Override
    public void logoutButtonClicked() {
        view.showLogoutConfirmationDialog();
    }

    @Override
    public void logoutConfirmed() {
        PreferencesHandler.removeUser(view.getContext());
        view.openLoginActivity();
    }

}
