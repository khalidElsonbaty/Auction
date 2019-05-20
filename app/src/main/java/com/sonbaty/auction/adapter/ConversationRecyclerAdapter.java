package com.sonbaty.auction.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sonbaty.auction.R;
import com.sonbaty.auction.data.api.ApiServices;
import com.sonbaty.auction.data.model.conversation.ConversationData;
import com.sonbaty.auction.ui.activity.HomeActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ConversationRecyclerAdapter extends RecyclerView.Adapter<ConversationRecyclerAdapter.MyViewHolder> {
    Context context;
    List<ConversationData> conversationData = new ArrayList<>();
    private ApiServices apiServices;


    public ConversationRecyclerAdapter(Context context, List<ConversationData> conversationData) {
        this.context = context;
        this.conversationData = conversationData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_conversation_view, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        Picasso.get().load(conversationData.get(i).getImage()).placeholder(R.drawable.profileuser).into(myViewHolder.ConversationCivProductImage);
        myViewHolder.ConversationTextViewName.setText(conversationData.get(i).getUsername());
        myViewHolder.ConversationTextViewMessage.setText(conversationData.get(i).getMsg());
        myViewHolder.ConversationTextViewDate.setText(conversationData.get(i).getDate());
        myViewHolder.ConversationImageViewMenu.setOnClickListener(new View.OnClickListener() {
            private boolean firstTap = true;

            @Override
            public void onClick(View v) {
                if (firstTap) {
                    myViewHolder.ConversationImageViewMenu.setImageResource(R.drawable.trash);
                    firstTap = false;
                } else {
                    showDialog();
                }
            }
        });

    }
    private void showDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialoge_trash);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView cancelView = (ImageView) dialog.findViewById(R.id.Trust_ImageView_cancel);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ImageView okView = (ImageView) dialog.findViewById(R.id.Trust_ImageView_ok);
        okView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteMessageRequest();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void onDeleteMessageRequest() {
        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return conversationData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.Conversation_Civ_product_Image)
        CircleImageView ConversationCivProductImage;
        @BindView(R.id.Conversation_TextView_Name)
        TextView ConversationTextViewName;
        @BindView(R.id.Conversation_TextView_message)
        TextView ConversationTextViewMessage;
        @BindView(R.id.Conversation_ImageView_menu)
        ImageView ConversationImageViewMenu;
        @BindView(R.id.Conversation_TextView_Date)
        TextView ConversationTextViewDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
