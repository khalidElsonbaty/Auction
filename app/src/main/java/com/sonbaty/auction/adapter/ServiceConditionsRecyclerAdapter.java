package com.sonbaty.auction.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonbaty.auction.R;
import com.sonbaty.auction.data.model.serviceConditions.ServiceConditionsData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceConditionsRecyclerAdapter extends RecyclerView.Adapter<ServiceConditionsRecyclerAdapter.MyViewHolder> {
    Context context;
    List<ServiceConditionsData> serviceConditionsData = new ArrayList<>();

    public ServiceConditionsRecyclerAdapter(Context context, List<ServiceConditionsData> serviceConditionsData) {
        this.context = context;
        this.serviceConditionsData = serviceConditionsData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service_conditions, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.ServiceConditionsTextViewTitle.setText(serviceConditionsData.get(i).getCondition());
        myViewHolder.ServiceConditionsTextViewDescription.setText(serviceConditionsData.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return serviceConditionsData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ServiceConditions_TextView_Title)
        TextView ServiceConditionsTextViewTitle;
        @BindView(R.id.ServiceConditions_TextView_description)
        TextView ServiceConditionsTextViewDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
