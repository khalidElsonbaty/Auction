package com.sonbaty.auction.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sembozdemir.viewpagerarrowindicator.library.ViewPagerArrowIndicator;
import com.sonbaty.auction.R;
import com.sonbaty.auction.adapter.AdsRecyclerAdapter;
import com.sonbaty.auction.adapter.CommentsRecyclerAdapter;
import com.sonbaty.auction.adapter.RelatedSliderAdapter;
import com.sonbaty.auction.adapter.SliderImagesAdapter;
import com.sonbaty.auction.data.api.ApiClient;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.model.adsDetails.AdsDetails;
import com.sonbaty.auction.data.model.adsDetails.AdsDetailsData;
import com.sonbaty.auction.data.model.adsDetails.Comment;
import com.sonbaty.auction.data.model.adsDetails.Related;
import com.sonbaty.auction.data.model.adsDetails.User;
import com.sonbaty.auction.data.model.adsImages.AdsImages;
import com.sonbaty.auction.data.model.adsImages.AdsImagesData;
import com.sonbaty.auction.helper.ZoomOutPageTransformer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class DetailsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = "DetailsActivity";
    @BindView(R.id.DetailsTextView_Title_Appbar)
    TextView DetailsTextViewTitleAppbar;
    @BindView(R.id.DetailsTextView_Subtitle_Appbar)
    TextView DetailsTextViewSubtitleAppbar;
    @BindView(R.id.DetailsTitle_Appbar)
    LinearLayout DetailsTitleAppbar;
    @BindView(R.id.DetailsToolbar)
    Toolbar DetailsToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.DetailsAppBarLayout)
    AppBarLayout DetailsAppBarLayout;
    @BindView(R.id.DetailsCiv_product_Image)
    CircleImageView DetailsCivProductImage;
    @BindView(R.id.DetailsTextView_Name)
    TextView DetailsTextViewName;
    @BindView(R.id.DetailsTextView_place)
    TextView DetailsTextViewPlace;
    @BindView(R.id.DetailsTextView_Message)
    TextView DetailsTextViewMessage;
    @BindView(R.id.Details_TextView_Ads_Number)
    TextView DetailsTextViewAdsNumber;
    @BindView(R.id.Details_TextView_Ads_Data)
    TextView DetailsTextViewAdsData;
    @BindView(R.id.Details_TextView_Ads_Cateory)
    TextView DetailsTextViewAdsCateory;
    @BindView(R.id.Details_TextView_Ads_Price)
    TextView DetailsTextViewAdsPrice;
    @BindView(R.id.Details_TextView_Ads_Country)
    TextView DetailsTextViewAdsCountry;
    @BindView(R.id.Details_TextView_Ads_Region)
    TextView DetailsTextViewAdsRegion;
    @BindView(R.id.Details_TextView_Ads_Details)
    TextView DetailsTextViewAdsDetails;
    @BindView(R.id.Details_TextView_Ads_Comment_Num)
    TextView DetailsTextViewAdsCommentNum;
    @BindView(R.id.Details_TextView_Ads_Fav_Num)
    TextView DetailsTextViewAdsFavNum;
    @BindView(R.id.Details_TextView_contact)
    TextView DetailsTextViewContact;
    @BindView(R.id.Details_RecyclerView_Comments)
    RecyclerView DetailsRecyclerViewComments;
    @BindView(R.id.Details_TextView_UserName)
    TextView DetailsTextViewUserName;
    @BindView(R.id.Details_EditText_Write_Comment)
    EditText DetailsEditTextWriteComment;
    @BindView(R.id.Details_Button_Send)
    Button DetailsButtonSend;
    @BindView(R.id.Details_TextView_Related_Title)
    TextView DetailsTextViewRelatedTitle;
    @BindView(R.id.Details_ViewPager_Related_Recycler)
    ViewPager DetailsViewPagerRelatedRecycler;
    @BindView(R.id.DetailsImageView_Like)
    ImageView DetailsImageViewLike;
    @BindView(R.id.DetailsImageView_Share)
    ImageView DetailsImageViewShare;
    @BindView(R.id.DetailsImageView_Report)
    ImageView DetailsImageViewReport;
    @BindView(R.id.Details_FrameLayout_behaviors)
    FrameLayout DetailsFrameLayoutBehaviors;
    @BindView(R.id.Details_Civ_owner_Image)
    CircleImageView DetailsCivOwnerImage;
    private ApiServices apiServices;
    private List<AdsImagesData> imagesData;
    private SliderImagesAdapter sliderImagesAdapter;
    private List<AdsDetailsData> detailsData;
    private AdsDetailsData data;
    private User user;
    private List<Comment> comments;
    private CommentsRecyclerAdapter commentsAdapter;
    private LinearLayoutManager layoutManager;
    private List<Related> relatedList;
    private RelatedSliderAdapter relatedSliderAdapter;
    private ViewPager DetailsViewPagerImagesRecycler;
    private ViewPagerArrowIndicator viewPagerArrowIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        intiObjects();
        intiToolbar();
        intiSliderImage();
        intiDetailsData();
    }

    private void intiObjects() {
        apiServices = ApiClient.getGeneral().create(ApiServices.class);
        imagesData = new ArrayList<>();
        sliderImagesAdapter = new SliderImagesAdapter(this, imagesData);
        detailsData = new ArrayList<>();
        comments = new ArrayList<>();
        commentsAdapter = new CommentsRecyclerAdapter(this, comments);
        relatedList = new ArrayList<>();
        relatedSliderAdapter = new RelatedSliderAdapter(this, relatedList);
    }

    private void intiToolbar() {
        setSupportActionBar(DetailsToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
        DetailsAppBarLayout.addOnOffsetChangedListener(this);
    }

    private void intiSliderImage() {
        DetailsViewPagerImagesRecycler = (ViewPager) findViewById(R.id.Details_ViewPager_images_Recycler);
        viewPagerArrowIndicator = (ViewPagerArrowIndicator) findViewById(R.id.viewPagerArrowIndicator);
        apiServices.getAdsImagesData(329).enqueue(new Callback<AdsImages>() {
            @Override
            public void onResponse(@NonNull Call<AdsImages> call, @NonNull Response<AdsImages> response) {
                if (response.isSuccessful() && response.body() != null) {
                    imagesData.addAll(response.body().getData());
                    DetailsViewPagerImagesRecycler.setAdapter(sliderImagesAdapter);
                    viewPagerArrowIndicator.bind(DetailsViewPagerImagesRecycler);
                    viewPagerArrowIndicator.setArrowIndicatorRes(R.drawable.left,
                            R.drawable.right);
                } else {
                    Toast.makeText(DetailsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<AdsImages> call, @NonNull Throwable t) {
                Toast.makeText(DetailsActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void intiDetailsData() {
        Toast.makeText(this, "enter method", Toast.LENGTH_SHORT).show();
        apiServices.getAdDetailsData(329, 88).enqueue(new Callback<AdsDetails>() {
            @Override
            public void onResponse(@NonNull Call<AdsDetails> call, @NonNull Response<AdsDetails> response) {
                Log.i(TAG, "onResponse: " + response.raw());
                if (response.isSuccessful() && response.body() != null) {
                    Log.i(TAG, "onResponse Successful : " + response.raw());
                    detailsData.add(response.body().getData());
                    data = response.body().getData();
                    user = data.getUser();
                    Picasso.get().load(user.getAvatar()).placeholder(R.drawable.user).into(DetailsCivOwnerImage);
                    DetailsTextViewName.setText(user.getName());
                    DetailsTextViewPlace.setText(user.getLocation());
                    DetailsTextViewAdsNumber.setText(data.getId().toString());
                    DetailsTextViewAdsData.setText(data.getDate());
                    DetailsTextViewAdsCateory.setText(data.getCategory());
                    DetailsTextViewAdsPrice.setText(data.getPrice());
                    DetailsTextViewAdsCountry.setText(data.getCountry());
                    DetailsTextViewAdsRegion.setText(data.getLocation());
                    DetailsTextViewAdsDetails.setText(data.getDetails());
                    DetailsTextViewAdsCommentNum.setText(data.getCountComments().toString());
                    DetailsTextViewAdsFavNum.setText(data.getCountFavourites().toString());
                    DetailsTextViewContact.setText(user.getPhone());
                    comments.addAll(response.body().getData().getComments());
                    DetailsRecyclerViewComments.setAdapter(commentsAdapter);
                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    DetailsRecyclerViewComments.setLayoutManager(layoutManager);
                    DetailsRecyclerViewComments.setNestedScrollingEnabled(true);
                    commentsAdapter.notifyDataSetChanged();
                    relatedList.addAll(response.body().getData().getRelated());
                    DetailsViewPagerRelatedRecycler.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
                    DetailsViewPagerRelatedRecycler.setPageTransformer(true, new ZoomOutPageTransformer());
                    DetailsViewPagerRelatedRecycler.setAdapter(relatedSliderAdapter);
                } else {
                    Toast.makeText(DetailsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdsDetails> call, @NonNull Throwable t) {
                Log.i(TAG, "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(DetailsActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            DetailsFrameLayoutBehaviors.setVisibility(View.VISIBLE);

        } else {
            DetailsFrameLayoutBehaviors.setVisibility(View.GONE);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
