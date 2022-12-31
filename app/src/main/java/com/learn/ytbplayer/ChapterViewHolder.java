package com.learn.ytbplayer;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChapterViewHolder extends RecyclerView.ViewHolder {

    TextView titleTv,indexTv;




    public ChapterViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTv=itemView.findViewById(R.id.topic_name);
        indexTv=itemView.findViewById(R.id.index_number);
    }
}
