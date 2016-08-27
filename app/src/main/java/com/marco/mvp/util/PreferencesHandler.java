package com.marco.mvp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.marco.mvp.model.beans.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PreferencesHandler {

    private static final String FILE_NAME_USER = "User";

    private static final String PREFERENCES_ACCOUNT = "Account";
    private static final String KEY_REMEMBER_USER = "Remember User";


    // Save User
    public static void saveUser(Context context, User user) throws IOException {
        FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME_USER, Context.MODE_PRIVATE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(user);
        objectOutputStream.close();
    }

    // Load User
    public static User loadUser(Context context) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = context.openFileInput(FILE_NAME_USER);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        User user = (User) objectInputStream.readObject();
        return user;
    }

    // Remove User
    public static boolean removeUser(Context context) {
        return context.deleteFile(FILE_NAME_USER);
    }


    // Set Remember User
    public static void setRememberUser(Context context, boolean rememberUser) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_ACCOUNT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_REMEMBER_USER, rememberUser);
        editor.apply();
    }

    // Is User Remembered
    public static boolean isUserRemembered(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_ACCOUNT, Context.MODE_PRIVATE);
        boolean rememberUser = sharedPreferences.getBoolean(KEY_REMEMBER_USER, false);
        return rememberUser;
    }

}
