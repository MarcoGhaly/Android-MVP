package com.marco.mvp.views.home;

import com.marco.mvp.model.beans.User;
import com.marco.mvp.views.BaseContract;

interface HomeContract {

    interface View extends BaseContract.View<Presenter> {

        void updateViews(User user);

    }

    interface Presenter extends BaseContract.Presenter {
    }

}
