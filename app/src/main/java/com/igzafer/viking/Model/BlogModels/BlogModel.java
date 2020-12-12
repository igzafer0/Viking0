package com.igzafer.viking.Model.BlogModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlogModel {
   @SerializedName("blogId")
   @Expose
   int id;
   @SerializedName("blogTitlePhotoUrl")
   @Expose
   String photo;
   @SerializedName("blogTitle")
   @Expose
   String baslik;

    public BlogModel(int id, String photo, String baslik) {
        this.id = id;
        this.photo = photo;
        this.baslik = baslik;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }
}
