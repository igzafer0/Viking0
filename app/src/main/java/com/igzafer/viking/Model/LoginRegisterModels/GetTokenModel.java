package com.igzafer.viking.Model.LoginRegisterModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTokenModel {
    @SerializedName("token")
    @Expose
    String token;
    @SerializedName("expiration")
    @Expose
    String expiration;

    public GetTokenModel(String token, String expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
