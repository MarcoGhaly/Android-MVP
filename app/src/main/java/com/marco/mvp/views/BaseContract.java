package com.marco.mvp.views;

import android.app.Activity;
import android.content.Context;

public interface BaseContract {

    interface View<T extends Presenter> {

        T getPresenter();

        Context getContext();

        Activity getActivity();

        int getViewResource();

        void initViews(android.view.View rootView);

        void showLogoutConfirmationDialog();

        void openLoginActivity();

        void finish();

    }

    interface Presenter {

        void viewCreated();

        void viewsCreated();

        void viewsDestroyed();

        void viewDestroyed();

        void logoutButtonClicked();

        void logoutConfirmed();

    }

}
