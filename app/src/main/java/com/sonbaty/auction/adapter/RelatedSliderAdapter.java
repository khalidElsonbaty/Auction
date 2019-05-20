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
import com.sonbaty.auction.data.model.adsDetails.Related;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RelatedSliderAdapter extends PagerAdapter {
    Activity activity;
    List<Related> relatedList;
    private CircleImageView circleImageView;
    private TextView titleName, place, details, date;
    private ImageView delete;
    private static RelatedSliderAdapter.ClickListener clickListener;

    public RelatedSliderAdapter(Activity activity, List<Related> relatedList) {
        this.activity = activity;
        this.relatedList = relatedList;
    }

    public void setOnItemClickListener(RelatedSliderAdapter.ClickListener clickListener) {
        RelatedSliderAdapter.clickListener = clickListener;
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
        Picasso.get().load(relatedList.get(position).getImg()).placeholder(R.drawable.profileuser).into(circleImageView);
        titleName.setText(relatedList.get(position).getTitle());
        place.setText(relatedList.get(position).getLocation());
        details.setText(relatedList.get(position).getDetails());
        date.setText(relatedList.get(position).getDate());
        delete.setVisibility(View.INVISIBLE);

        view.setOnClickListener(v -> clickListener.onClick(v, position));

        container.addView(view, 0);
        return view;
    }

    @Override
    public int getCount() {
        return relatedList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
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
