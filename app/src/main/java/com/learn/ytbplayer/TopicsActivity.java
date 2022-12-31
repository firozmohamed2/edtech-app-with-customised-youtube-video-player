package com.learn.ytbplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TopicsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TopicRecyclerAdapter topicRecyclerAdapter;
    private ArrayList<topic> topics;
    private List<String> topicArrayList= new ArrayList<>();;


    FirebaseFirestore db;
    private static final String TAG = "Firestore";
    String value;
    TextView chapterTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);


        db = FirebaseFirestore.getInstance();
        recyclerView= findViewById(R.id.recycler_view);
        topics=new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("key");
            //The key argument here must match that used in the other activity
        }



        DocumentReference docRef = db.collection("topic_collection").document(value);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                        Toast.makeText(TopicsActivity.this, "DocumentSnapshot data: " + document.getData(),
//                                Toast.LENGTH_SHORT).show();
                        topicArrayList = (List<String>) document.get("topics");

                        for(String x:topicArrayList){
                            topics.add(new topic(x));
                        }

                        LinearLayoutManager llm = new LinearLayoutManager(TopicsActivity.this);
                        llm.setOrientation(LinearLayoutManager.VERTICAL);



                        topicRecyclerAdapter= new TopicRecyclerAdapter(TopicsActivity.this,topics);
                        recyclerView.setLayoutManager(llm);

                        recyclerView.setAdapter(topicRecyclerAdapter);




                    } else {
                        Log.d(TAG, "No such document");
                        Toast.makeText(TopicsActivity.this, "Data not added , start previous chapters",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });







        chapterTv= findViewById(R.id.chapter_tv);
        chapterTv.setText(value);



    }
}