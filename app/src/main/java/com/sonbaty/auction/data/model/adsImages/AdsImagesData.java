
package com.sonbaty.auction.data.model.adsImages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdsImagesData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("img")
    @Expose
    private String img;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
