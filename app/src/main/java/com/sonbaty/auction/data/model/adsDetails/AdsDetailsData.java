
package com.sonbaty.auction.data.model.adsDetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdsDetailsData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("sub_category")
    @Expose
    private String subCategory;
    @SerializedName("car_type")
    @Expose
    private String carType;
    @SerializedName("car_type_id")
    @Expose
    private Integer carTypeId;
    @SerializedName("aqaur_type_id")
    @Expose
    private String aqaurTypeId;
    @SerializedName("aqaur_type")
    @Expose
    private String aqaurType;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("car_brand_id")
    @Expose
    private Integer carBrandId;
    @SerializedName("car_brand")
    @Expose
    private String carBrand;
    @SerializedName("modal")
    @Expose
    private String modal;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("myData")
    @Expose
    private MyData myData;
    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;
    @SerializedName("related")
    @Expose
    private List<Related> related = null;
    @SerializedName("room")
    @Expose
    private String room;
    @SerializedName("count_comments")
    @Expose
    private Integer countComments;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("count_favourites")
    @Expose
    private Integer countFavourites;
    @SerializedName("favourite")
    @Expose
    private String favourite;
    @SerializedName("date")
    @Expose
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Integer getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(Integer carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getAqaurTypeId() {
        return aqaurTypeId;
    }

    public void setAqaurTypeId(String aqaurTypeId) {
        this.aqaurTypeId = aqaurTypeId;
    }

    public String getAqaurType() {
        return aqaurType;
    }

    public void setAqaurType(String aqaurType) {
        this.aqaurType = aqaurType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getCarBrandId() {
        return carBrandId;
    }

    public void setCarBrandId(Integer carBrandId) {
        this.carBrandId = carBrandId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getModal() {
        return modal;
    }

    public void setModal(String modal) {
        this.modal = modal;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MyData getMyData() {
        return myData;
    }

    public void setMyData(MyData myData) {
        this.myData = myData;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Related> getRelated() {
        return related;
    }

    public void setRelated(List<Related> related) {
        this.related = related;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getCountComments() {
        return countComments;
    }

    public void setCountComments(Integer countComments) {
        this.countComments = countComments;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCountFavourites() {
        return countFavourites;
    }

    public void setCountFavourites(Integer countFavourites) {
        this.countFavourites = countFavourites;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
