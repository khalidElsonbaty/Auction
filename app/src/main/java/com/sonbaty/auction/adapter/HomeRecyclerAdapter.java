package com.sonbaty.auction.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sonbaty.auction.R;
import com.sonbaty.auction.data.model.ads.AdsData;
import com.sonbaty.auction.data.model.ads.Data;
import com.sonbaty.auction.helper.ZoomOutPageTransformer;
import com.sonbaty.auction.ui.activity.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder> {
    Context context;
    List<Data> ads = new ArrayList<>();
    String key;

    public HomeRecyclerAdapter(Context context, List<Data> ads, String key) {
        this.context = context;
        this.ads = ads;
        this.key = key;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_view, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.HomeTextViewCategoryTitle.setText(ads.get(i).getName());
        if (key.equalsIgnoreCase("Home")) {
            List<AdsData> adsData = this.ads.get(i).getAds();
            final SliderPagerAdapter sliderPagerAdapter = new SliderPagerAdapter((Activity) context, adsData);
            myViewHolder.HomeViewPagerRecycler.setPageTransformer(true, new ZoomOutPageTransformer());
            myViewHolder.HomeViewPagerRecycler.setClipToPadding(false);
            myViewHolder.HomeViewPagerRecycler.setPadding(40, 0, 40, 0);
            myViewHolder.HomeViewPagerRecycler.setPageMargin(-20);
            myViewHolder.HomeViewPagerRecycler.setAdapter(sliderPagerAdapter);
            sliderPagerAdapter.setOnItemClickListener(new SliderPagerAdapter.ClickListener() {
                @Override
                public void onClick(View v, int position) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    context.startActivity(intent);
                }
            });
        } else {
            myViewHolder.HomeRelativeLayoutViewPagerContainer.setVisibility(View.GONE);
            myViewHolder.HomeTextViewCategoryTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, ads.get(i).getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return ads.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.Home_TextView_Category_Title)
        TextView HomeTextViewCategoryTitle;
        @BindView(R.id.Home_ViewPager_Recycler)
        ViewPager HomeViewPagerRecycler;
        @BindView(R.id.Home_RelativeLayout_ViewPager_Container)
        RelativeLayout HomeRelativeLayoutViewPagerContainer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
