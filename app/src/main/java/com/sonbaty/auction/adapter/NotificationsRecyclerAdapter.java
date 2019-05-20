package com.sonbaty.auction.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonbaty.auction.R;
import com.sonbaty.auction.data.model.notifications.NotificationsData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsRecyclerAdapter extends RecyclerView.Adapter<NotificationsRecyclerAdapter.MyViewHolder> {
    Context context;
    List<NotificationsData> notificationsData = new ArrayList<>();

    public NotificationsRecyclerAdapter(Context context, List<NotificationsData> notificationsData) {
        this.context = context;
        this.notificationsData = notificationsData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification_view, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.MyAccountEditTextName.setText(notificationsData.get(i).getMessage());
    }

    @Override
    public int getItemCount() {
        return notificationsData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.MyAccount_EditText_Name)
        TextView MyAccountEditTextName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
