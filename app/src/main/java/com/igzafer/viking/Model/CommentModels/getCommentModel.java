package com.igzafer.viking.Model.CommentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getCommentModel {

    @SerializedName("commentId")
    @Expose
    int id;
    @SerializedName("userAvatarUrl")
    @Expose
    String avatar;
    @SerializedName("userNickname")
    @Expose
    String nick;
    @SerializedName("commentDate")
    @Expose
    String time;
    @SerializedName("text")
    @Expose
    String text;

    public getCommentModel(int id, String avatar, String nick, String time, String text) {
        this.id = id;
        this.avatar = avatar;
        this.nick = nick;
        this.time = time;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
