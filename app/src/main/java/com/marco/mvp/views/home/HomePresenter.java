package com.marco.mvp.views.home;

import com.marco.mvp.model.beans.User;
import com.marco.mvp.util.PreferencesHandler;
import com.marco.mvp.views.BasePresenter;

import java.io.IOException;

class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private User user;


    // Constructor
    public HomePresenter(HomeContract.View view) {
        super(view);
    }


    @Override
    public void viewCreated() {
        super.viewCreated();

        try {
            user = PreferencesHandler.loadUser(view.getContext());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewsCreated() {
        super.viewsCreated();

        view.updateViews(user);
    }

}
