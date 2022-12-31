package com.learn.ytbplayer;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TopicRecyclerAdapter extends RecyclerView.Adapter<TopicViewHolder> {
    private Context context;
    private ArrayList<topic> topics;
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;


    public TopicRecyclerAdapter(Context context, ArrayList<topic> topics) {
        this.context = context;
        this.topics = topics;
        pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_layout,parent,false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
       topic p=topics.get(position);
       holder.titleTv.setText(p.getTitle());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, p.getTitle(), Toast.LENGTH_SHORT).show();
                String chapter=pref.getString("chapter", null);         // getting String

                Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);
                Bundle b = new Bundle();

                //get text for current item
                //put text into a bundle and add to intent
                intent.putExtra("key", p.getTitle());
                intent.putExtra("chapter", chapter);


                //get position to carry integer
                //  intent.putExtra("position", itemPosition);

                intent.putExtras(b);

                //begin activity
                holder.itemView.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return topics.size();
    }
}
