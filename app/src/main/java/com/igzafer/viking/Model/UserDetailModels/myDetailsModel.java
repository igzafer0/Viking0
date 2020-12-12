package com.igzafer.viking.Model.UserDetailModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class myDetailsModel {

    @SerializedName("nickname")
    @Expose
    String nick;
    @SerializedName("firstName")
    @Expose
    String ad;
    @SerializedName("lastName")
    @Expose
    String soyad;
    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("avatarUrl")
    @Expose
    String avatar;
    @SerializedName("description")
    @Expose
    String biyografi;
    @SerializedName("id")
    @Expose
    int user_id;

    public myDetailsModel(String nick, String ad, String soyad, String email, String avatar, String biyografi, int user_id) {
        this.nick = nick;
        this.ad = ad;
        this.soyad = soyad;
        this.email = email;
        this.avatar = avatar;
        this.biyografi = biyografi;
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBiyografi() {
        return biyografi;
    }

    public void setBiyografi(String biyografi) {
        this.biyografi = biyografi;
    }
}
