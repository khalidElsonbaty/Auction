
package com.sonbaty.auction.data.model.adsDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdsDetails {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("data")
    @Expose
    private AdsDetailsData data;

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

    public AdsDetailsData getData() {
        return data;
    }

    public void setData(AdsDetailsData data) {
        this.data = data;
    }

}
