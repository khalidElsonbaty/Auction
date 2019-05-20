
package com.sonbaty.auction.data.model.serviceConditions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceConditionsData {

    @SerializedName("condition")
    @Expose
    private String condition;
    @SerializedName("description")
    @Expose
    private String description;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
