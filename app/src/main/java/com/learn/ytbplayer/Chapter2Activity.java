package com.learn.ytbplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Chapter2Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChapterRecyclerAdapter chapterRecyclerAdapter;
    private ArrayList<chapter> chapter;
    String value;

    TextView subjectTv;
    String[] mathsChapters,chemistryChapters,physicsChapters,biologyChapters;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter2);

        mathsChapters=new String[]{ "Sets", "Relations and Functions", " Trigonometric Functions",
        " Principle of Mathematical Induction", "Complex Numbers and Quadratic Equations", " Linear Inequalities",
        "Permutations and Combinations", "Binomial Theorem"};

        chemistryChapters=new String[]{ "Some Basic Concepts of Chemistry", "Structure of Atom", " Classification of Elements and Periodicity in Properties",
                " Chemical Bonding and Molecular Structure", "States of Matter", " Thermodynamics",
                "Equilibrium", "Oxygen"};

        physicsChapters=new String[]{ "Sets", "Relations and Functions", " Trigonometric Functions",
                " Principle of Mathematical Induction", "Complex Numbers and Quadratic Equations", " Linear Inequalities",
                "Permutations and Combinations", "Binomial Theorem"};

        biologyChapters=new String[]{ "Sets", "Relations and Functions", " Trigonometric Functions",
                " Principle of Mathematical Induction", "Complex Numbers and Quadratic Equations", " Linear Inequalities",
                "Permutations and Combinations", "Binomial Theorem"};




        recyclerView= findViewById(R.id.recycler_view);
        chapter= new ArrayList<chapter>();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             value = extras.getString("key");
            //The key argument here must match that used in the other activity
        }





        if(value.equals("Mathematics")) {

            for(int i=0;i<8;i++){
                chapter.add(new chapter(Integer.toString(i+1), mathsChapters[i]));
            }

        }
        else if(value.equals("Physics") ){
            for(int i=0;i<8;i++){
                chapter.add(new chapter(Integer.toString(i+1), physicsChapters[i]));
            }
        }
        else  if(value.equals("Chemistry") ){
            for(int i=0;i<8;i++){
                chapter.add(new chapter(Integer.toString(i+1), chemistryChapters[i]));
            }
        }
        else{
            for(int i=0;i<8;i++){
                chapter.add(new chapter(Integer.toString(i+1), biologyChapters[i]));
            }
        }

        LinearLayoutManager llm = new LinearLayoutManager(Chapter2Activity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);



        chapterRecyclerAdapter= new ChapterRecyclerAdapter(Chapter2Activity.this,chapter);
        recyclerView.setLayoutManager(llm);

        recyclerView.setAdapter(chapterRecyclerAdapter);



         subjectTv = findViewById(R.id.gradient_tv);
        subjectTv.setText(value);
        subjectTv.setTextColor(Color.parseColor("#27A2ED"));
        Shader textShader = new LinearGradient(0, 90, subjectTv.getPaint().measureText(subjectTv.getText().toString()), subjectTv.getTextSize(),
                new int[]{Color.parseColor("#27A2ED"), Color.parseColor("#FF00BFA5"),Color.parseColor("#FF00BFA5")},
                new float[]{0, 1,2}, Shader.TileMode.CLAMP);

        subjectTv.getPaint().setShader(textShader);

    }
}