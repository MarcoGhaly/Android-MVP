package com.marco.mvp.model.source;

import com.marco.mvp.model.beans.User;

public interface AccountDataSource {

    interface LoginCallback {

        void onSuccess(User user);

        void onFailure();

        void onConnectionFailure();

    }

    interface SendNewPasswordCallback {

        void onSuccess();

        void onFailure();

        void onConnectionFailure();

    }


    void authenticate(String username, String password, LoginCallback loginCallback);

    void sendNewPasswordRequest(String username, SendNewPasswordCallback sendNewPasswordCallback);

    void cancelTasks();

}
