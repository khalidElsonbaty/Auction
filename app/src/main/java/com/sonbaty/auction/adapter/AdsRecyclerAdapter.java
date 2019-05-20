package com.sonbaty.auction.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sonbaty.auction.R;
import com.sonbaty.auction.data.model.ads.AdsData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdsRecyclerAdapter extends RecyclerView.Adapter<AdsRecyclerAdapter.MyViewHolder> {
    Context context;
    List<AdsData> adsData = new ArrayList<>();
    private static AdsRecyclerAdapter.ClickListener clickListener;

    public AdsRecyclerAdapter(Context context, List<AdsData> adsData) {
        this.context = context;
        this.adsData = adsData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ads_view, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Picasso.get().load(adsData.get(i).getImg()).placeholder(R.drawable.ic_circle).into(myViewHolder.AdsCivProductImage);
        myViewHolder.AdsTextViewName.setText(adsData.get(i).getTitle());
        myViewHolder.AdsTextViewPlace.setText(adsData.get(i).getLocation());
        myViewHolder.AdsTextViewDetails.setText(adsData.get(i).getDetails());
        myViewHolder.AdsTextViewTime.setText(adsData.get(i).getDate());
        myViewHolder.AdsImageViewDelete.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return adsData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.Ads_Civ_product_Image)
        CircleImageView AdsCivProductImage;
        @BindView(R.id.Ads_TextView_Name)
        TextView AdsTextViewName;
        @BindView(R.id.Ads_TextView_place)
        TextView AdsTextViewPlace;
        @BindView(R.id.Ads_TextView_Details)
        TextView AdsTextViewDetails;
        @BindView(R.id.Ads_TextView_Time)
        TextView AdsTextViewTime;
        @BindView(R.id.Ads_ImageView_delete)
        ImageView AdsImageViewDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }


    public void setOnItemClickListener(AdsRecyclerAdapter.ClickListener clickListener) {
        AdsRecyclerAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }
}
