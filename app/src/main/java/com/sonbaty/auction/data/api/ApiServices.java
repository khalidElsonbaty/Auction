package com.sonbaty.auction.data.api;

import com.sonbaty.auction.data.model.about.About;
import com.sonbaty.auction.data.model.ads.Ads;
import com.sonbaty.auction.data.model.adsDetails.AdsDetails;
import com.sonbaty.auction.data.model.adsImages.AdsImages;
import com.sonbaty.auction.data.model.categories.Categories;
import com.sonbaty.auction.data.model.contactUs.ContactUs;
import com.sonbaty.auction.data.model.conversation.Conversation;
import com.sonbaty.auction.data.model.deleteMessage.DeleteMessage;
import com.sonbaty.auction.data.model.favourites.Favourites;
import com.sonbaty.auction.data.model.forgetPassword.ForgetPassword;
import com.sonbaty.auction.data.model.notifications.Notifications;
import com.sonbaty.auction.data.model.phoneKeys.PhoneKeys;
import com.sonbaty.auction.data.model.serviceConditions.ServiceConditions;
import com.sonbaty.auction.data.model.signIn.SignIn;
import com.sonbaty.auction.data.model.signUp.SignUp;
import com.sonbaty.auction.data.model.subCategories.SubCategories;
import com.sonbaty.auction.data.model.updatePassword.UpdatePassword;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("phone-keys")
    Call<PhoneKeys> getPhoneKeys();

    @POST("sign-in")
    @FormUrlEncoded
    Call<SignIn> sendUserLoginRequest(@Field("phone") String phone,
                                      @Field("password") String password,
                                      @Field("device_id") String device_id,
                                      @Field("code") String code);

    @POST("sign-up")
    @FormUrlEncoded
    Call<SignUp> sendRegisterRequest(@Field("name") String name,
                                     @Field("phone") String phone,
                                     @Field("email") String email,
                                     @Field("password") String password,
                                     @Field("device_id") String device_id,
                                     @Field("code") String code);

    @POST("forget-password")
    @FormUrlEncoded
    Call<ForgetPassword> sendForgetPasswordRequest(@Field("phone") String phone,
                                                   @Field("code") String code);

    @POST("update-password")
    @FormUrlEncoded
    Call<UpdatePassword> sendResetPasswordRequest(@Field("user_id") String user_id,
                                                  @Field("code") String code,
                                                  @Field("password") String password);

    @POST("contact-us")
    @FormUrlEncoded
    Call<ContactUs> sendContactUsRequest(@Field("name") String name,
                                         @Field("email") String email,
                                         @Field("message") String message);

    @GET("about")
    Call<About> getAboutText();

    @GET("categories")
    Call<Categories> getCategoriesData();

    @POST("favourites")
    @FormUrlEncoded
    Call<Favourites> getFavouritesData(@Field("user_id") String user_id);

    @GET("ads")
    Call<Ads> getAdsData();

    @POST("notifications")
    @FormUrlEncoded
    Call<Notifications> getNotificationsData(@Field("user_id") int user_id);

    @POST("conversation")
    @FormUrlEncoded
    Call<Conversation> getConversationData(@Field("user_id") int user_id);

    @POST("delete-message")
    @FormUrlEncoded
    Call<DeleteMessage> sendDeleteMessage(@Field("user_id") int user_id,
                                          @Field("message_id") int message_id);

    @POST("sub-categories")
    @FormUrlEncoded
    Call<SubCategories> getSubCategoriesData(@Field("category_id") int category_id);

    @GET("service-conditions")
    Call<ServiceConditions> getServiceConditionsData();

    @POST("ads-images")
    @FormUrlEncoded
    Call<AdsImages> getAdsImagesData(@Field("ads_id") int ads_id);

    @POST("ads-details")
    @FormUrlEncoded
    Call<AdsDetails> getAdDetailsData(@Field("ads_id") int ads_id,
                                       @Field("user_id") int user_id);
}
