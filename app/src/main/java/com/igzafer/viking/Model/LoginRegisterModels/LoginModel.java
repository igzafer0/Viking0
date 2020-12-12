package com.igzafer.viking.Model.LoginRegisterModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {
    @SerializedName("emailornickname")
    @Expose
    String kadi;
    @SerializedName("password")
    @Expose
    String sifre;


    public LoginModel(String kadi, String sifre) {
        this.kadi = kadi;
        this.sifre = sifre;
    }

    public String getKadi() {
        return kadi;
    }

    public void setKadi(String kadi) {
        this.kadi = kadi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}
