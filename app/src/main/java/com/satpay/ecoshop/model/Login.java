package com.satpay.ecoshop.model;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
