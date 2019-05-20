
package com.sonbaty.auction.data.model.notifications;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notifications {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("data")
    @Expose
    private List<NotificationsData> data = null;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<NotificationsData> getData() {
        return data;
    }

    public void setData(List<NotificationsData> data) {
        this.data = data;
    }

}
