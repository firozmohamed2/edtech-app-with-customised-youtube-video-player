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

public class ChapterRecyclerAdapter extends RecyclerView.Adapter<ChapterViewHolder> {
    private Context context;
    private ArrayList<chapter> chapters;
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;




    public ChapterRecyclerAdapter(Context context, ArrayList<chapter> chapters) {
        this.context = context;
        this.chapters = chapters;
        pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_layout,parent,false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
       chapter p=chapters.get(position);
       holder.titleTv.setText(p.getTitle());
       holder.indexTv.setText(p.getIndexNumber());


       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             //  Toast.makeText(context, p.getTitle(), Toast.LENGTH_SHORT).show();
               editor.putString("chapter", p.getTitle());  // Saving string
               editor.apply();

               Intent intent = new Intent(holder.itemView.getContext(), TopicsActivity.class);
               Bundle b = new Bundle();

               //get text for current item
               //put text into a bundle and add to intent
               intent.putExtra("key", p.getTitle());

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
        return chapters.size();
    }
}
