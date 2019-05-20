package com.sonbaty.auction.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonbaty.auction.R;
import com.sonbaty.auction.data.model.adsDetails.Comment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsRecyclerAdapter.MyViewHolder> {
    Activity activity;
    List<Comment> comments = new ArrayList<>();

    public CommentsRecyclerAdapter(Activity activity, List<Comment> comments) {
        this.activity = activity;
        this.comments = comments;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_comment_view, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Picasso.get().load(comments.get(i).getAvatar()).placeholder(R.drawable.user).into(myViewHolder.itemCommentCivProductImage);
        myViewHolder.itemCommentTextViewName.setText(comments.get(i).getName());
        myViewHolder.itemCommentTextViewTime.setText(comments.get(i).getDate());
        myViewHolder.itemCommentTextViewContent.setText(comments.get(i).getComment());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_Comment_Civ_product_Image)
        CircleImageView itemCommentCivProductImage;
        @BindView(R.id.item_Comment_TextView_Name)
        TextView itemCommentTextViewName;
        @BindView(R.id.item_Comment_TextView_Time)
        TextView itemCommentTextViewTime;
        @BindView(R.id.item_Comment_TextView_Content)
        TextView itemCommentTextViewContent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
