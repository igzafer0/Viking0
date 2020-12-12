package com.igzafer.viking.Model.ErrorModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorModel {
    @SerializedName("errorMessages")
    @Expose
    String hata;
    @SerializedName("operation")
    @Expose
    String islem;

    public ErrorModel(String hata, String islem) {
        this.hata = hata;
        this.islem = islem;
    }

    public String getHata() {
        return hata;
    }

    public void setHata(String hata) {
        this.hata = hata;
    }

    public String getIslem() {
        return islem;
    }

    public void setIslem(String islem) {
        this.islem = islem;
    }
}
