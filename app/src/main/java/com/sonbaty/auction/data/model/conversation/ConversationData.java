
package com.sonbaty.auction.data.model.conversation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConversationData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("s_id")
    @Expose
    private Integer sId;
    @SerializedName("r_id")
    @Expose
    private Integer rId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSId() {
        return sId;
    }

    public void setSId(Integer sId) {
        this.sId = sId;
    }

    public Integer getRId() {
        return rId;
    }

    public void setRId(Integer rId) {
        this.rId = rId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
