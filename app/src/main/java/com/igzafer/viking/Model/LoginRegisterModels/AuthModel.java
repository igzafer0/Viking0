package com.igzafer.viking.Model.LoginRegisterModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthModel {
    @SerializedName("emailornickname")
    @Expose
    String emailornickname;
    @SerializedName("nickName")
    @Expose
    String kadi;
    @SerializedName("password")
    @Expose
    String sifre;
    @SerializedName("email")
    @Expose
    String email;

    public AuthModel(String emailornickname, String sifre) {
        this.emailornickname = emailornickname;
        this.sifre = sifre;
    }

    public AuthModel(String kadi, String sifre, String email) {
        this.emailornickname = email;
        this.kadi = kadi;
        this.sifre = sifre;
        this.email = email;
    }

    public String getEmailornickname() {
        return emailornickname;
    }

    public void setEmailornickname(String emailornickname) {
        this.emailornickname = emailornickname;
    }

    public String getKadi() {
        return kadi;
    }

    public String getSifre() {
        return sifre;
    }

    public String getEmail() {
        return email;
    }

    public void setKadi(String kadi) {
        this.kadi = kadi;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
