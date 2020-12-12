package com.igzafer.viking.Model.CommentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class addCommentModel {
    @SerializedName("blogId")
    @Expose
    int blogId;
    @SerializedName("text")
    @Expose
    String text;

    public addCommentModel(int blogId, String text) {
        this.blogId = blogId;
        this.text = text;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
