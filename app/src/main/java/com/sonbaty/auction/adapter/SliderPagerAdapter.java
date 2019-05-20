package com.sonbaty.auction.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonbaty.auction.R;
import com.sonbaty.auction.data.model.ads.AdsData;
import com.squareup.picasso.Picasso;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SliderPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    List<AdsData> adsData;
    private CircleImageView circleImageView;
    private TextView titleName, place, details, date;
    private ImageView delete;
    private static SliderPagerAdapter.ClickListener clickListener;

    public SliderPagerAdapter(Activity activity, List<AdsData> adsData) {
        this.activity = activity;
        this.adsData = adsData;
    }

    public void setOnItemClickListener(SliderPagerAdapter.ClickListener clickListener) {
        SliderPagerAdapter.clickListener = clickListener;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_ads_view, container, false);
        circleImageView = (CircleImageView) view.findViewById(R.id.Ads_Civ_product_Image);
        titleName = (TextView) view.findViewById(R.id.Ads_TextView_Name);
        place = (TextView) view.findViewById(R.id.Ads_TextView_place);
        details = (TextView) view.findViewById(R.id.Ads_TextView_Details);
        date = (TextView) view.findViewById(R.id.Ads_TextView_Time);
        delete = (ImageView) view.findViewById(R.id.Ads_ImageView_delete);
        Picasso.get().load(adsData.get(position).getImg()).placeholder(R.drawable.profileuser).into(circleImageView);
        titleName.setText(adsData.get(position).getTitle());
        place.setText(adsData.get(position).getLocation());
        details.setText(adsData.get(position).getDetails());
        date.setText(adsData.get(position).getDate());
        delete.setVisibility(View.INVISIBLE);

        view.setOnClickListener(v -> clickListener.onClick(v, position));

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return adsData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    public interface ClickListener {
        void onClick(View v, int position);
    }
}
