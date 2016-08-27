package com.marco.mvp.model.beans;

import java.io.Serializable;

public class User implements Serializable {

    private String username;


    // Constructors

    public User() {
    }

    public User(String username) {
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
