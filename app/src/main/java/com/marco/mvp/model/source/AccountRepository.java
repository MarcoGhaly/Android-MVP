package com.marco.mvp.model.source;

import android.os.Handler;
import android.os.Looper;
import android.text.format.DateUtils;

import com.marco.mvp.model.beans.User;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements AccountDataSource {

    private static final String USERNAME = "Test User";
    private static final String PASSWORD = "123456";

    private static final long TIME_DELAY_MILLISECONDS = 5 * DateUtils.SECOND_IN_MILLIS;


    private List<Task> runningTasks = new ArrayList<>();


    @Override
    public void authenticate(String username, String password, LoginCallback loginCallback) {
        cancelTasks();

        AuthenticationTask authenticationTask = new AuthenticationTask(username, password, loginCallback);
        new Thread(authenticationTask).start();
        runningTasks.add(authenticationTask);
    }


    @Override
    public void sendNewPasswordRequest(String username, SendNewPasswordCallback sendNewPasswordCallback) {
        cancelTasks();

        SendNewPasswordTask sendNewPasswordTask = new SendNewPasswordTask(username, sendNewPasswordCallback);
        new Thread(sendNewPasswordTask).start();
        runningTasks.add(sendNewPasswordTask);
    }


    @Override
    public void cancelTasks() {
        for (Task task : runningTasks) {
            task.cancel();
        }
    }


    // Authentication Task
    private class AuthenticationTask extends Task {

        private String username;
        private String password;
        private LoginCallback loginCallback;

        public AuthenticationTask(String username, String password, LoginCallback loginCallback) {
            this.username = username;
            this.password = password;
            this.loginCallback = loginCallback;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(TIME_DELAY_MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    runningTasks.remove(AuthenticationTask.this);

                    if (!isCancelled()) {
                        if (username.equalsIgnoreCase(USERNAME) && password.equals(PASSWORD)) {
                            loginCallback.onSuccess(new User(USERNAME));
                        } else {
                            loginCallback.onFailure();
                        }
                    }
                }
            });
        }
    }


    // Send New Password Task
    private class SendNewPasswordTask extends Task {

        private String username;
        private SendNewPasswordCallback sendNewPasswordCallback;

        public SendNewPasswordTask(String username, SendNewPasswordCallback sendNewPasswordCallback) {
            this.username = username;
            this.sendNewPasswordCallback = sendNewPasswordCallback;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(TIME_DELAY_MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    runningTasks.remove(SendNewPasswordTask.this);

                    if (!isCancelled()) {
                        if (username.equalsIgnoreCase(USERNAME)) {
                            sendNewPasswordCallback.onSuccess();
                        } else {
                            sendNewPasswordCallback.onFailure();
                        }
                    }
                }
            });
        }
    }

}
