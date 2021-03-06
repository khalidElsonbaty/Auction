
package com.sonbaty.auction.data.model.ads;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ads")
    @Expose
    private List<AdsData> ads = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AdsData> getAds() {
        return ads;
    }

    public void setAds(List<AdsData> ads) {
        this.ads = ads;
    }

}
