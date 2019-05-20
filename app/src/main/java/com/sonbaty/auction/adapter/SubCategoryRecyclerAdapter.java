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
import com.sonbaty.auction.data.model.subCategories.SubCategoriesData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubCategoryRecyclerAdapter extends RecyclerView.Adapter<SubCategoryRecyclerAdapter.MyViewHolder> {
    Context context;
    List<SubCategoriesData> subCategoriesData = new ArrayList<>();
    private static CategoryRecyclerAdapter.ClickListener clickListener;

    public SubCategoryRecyclerAdapter(Context context, List<SubCategoriesData> subCategoriesData) {
        this.context = context;
        this.subCategoriesData = subCategoriesData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Picasso.get().load(subCategoriesData.get(i).getIcon()).placeholder(R.drawable.ic_circle).into(myViewHolder.CategoryThumb);
        myViewHolder.CategoryName.setText(subCategoriesData.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return subCategoriesData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.CategoryThumb)
        ImageView CategoryThumb;
        @BindView(R.id.CategoryName)
        TextView CategoryName;

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

    public void setOnItemClickListener(CategoryRecyclerAdapter.ClickListener clickListener) {
        SubCategoryRecyclerAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }
}
