package com.learn.ytbplayer;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TopicViewHolder extends RecyclerView.ViewHolder {

    TextView titleTv;


    public TopicViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTv=itemView.findViewById(R.id.topic_name);
    }
}
