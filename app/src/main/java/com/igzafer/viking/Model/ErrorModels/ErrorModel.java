package com.igzafer.viking.Model.ErrorModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorModel {

    @SerializedName("errorMessages")
    @Expose
    private String body;

    public ErrorModel(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
