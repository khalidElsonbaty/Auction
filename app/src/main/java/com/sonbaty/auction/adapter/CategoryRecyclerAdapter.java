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
import com.sonbaty.auction.data.model.categories.CategoriesData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<CategoriesData> categoriesData = new ArrayList<>();
    String key;
    private static ClickListener clickListener;


    public CategoryRecyclerAdapter(Context context, List<CategoriesData> categoriesData, String key) {
        this.context = context;
        this.categoriesData = categoriesData;
        this.key = key;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (key) {
            case "categories":
                view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
                return new CardTypeViewHolder(view);
            case "advertisement":
                view = LayoutInflater.from(context).inflate(R.layout.item_add_advertisement_view, parent, false);
                return new TextTypeViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (key.equalsIgnoreCase("categories")) {
            ((CardTypeViewHolder) holder).CategoryName.setText(categoriesData.get(i).getName());
            Picasso.get().load(categoriesData.get(i).getIcon()).placeholder(R.drawable.ic_circle).into(((CardTypeViewHolder) holder).CategoryThumb);
        } else {
            ((TextTypeViewHolder) holder).AddAdvertisementTextViewOrderName.setText(categoriesData.get(i).getName());
        }
    }

    @Override
    public int getItemCount() {
        return categoriesData.size();
    }

    public class TextTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.Add_Advertisement_TextView_Order_Name)
        TextView AddAdvertisementTextViewOrderName;

        public TextTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }

    public class CardTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.CategoryThumb)
        ImageView CategoryThumb;
        @BindView(R.id.CategoryName)
        TextView CategoryName;

        public CardTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        CategoryRecyclerAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }
}
