package com.sonbaty.auction.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sonbaty.auction.R;
import com.sonbaty.auction.data.model.adsImages.AdsImagesData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderImagesAdapter extends PagerAdapter {
    Activity activity;
    List<AdsImagesData> adsImages;

    public SliderImagesAdapter(Activity activity, List<AdsImagesData> adsImages) {
        this.activity = activity;
        this.adsImages = adsImages;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_image_slider_view, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.Item_Image_slider_ImageView_Img);
        Picasso.get().load(adsImages.get(position).getImg()).placeholder(R.drawable.ic_circle).into(imageView);

        container.addView(view,0);
        return view;
    }
    @Override
    public int getCount() {
        return adsImages.size();
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
}
