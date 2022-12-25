package com.learn.ytbplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TopicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        LinearLayout layout1= findViewById(R.id.layout_1);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TopicActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


        TextView tv = findViewById(R.id.gradient_tv);
        tv.setText("ARITHMETIC PROGRESSION");
        tv.setTextColor(Color.parseColor("#27A2ED"));
        Shader textShader = new LinearGradient(0, 90, tv.getPaint().measureText(tv.getText().toString()), tv.getTextSize(),
                new int[]{Color.parseColor("#27A2ED"), Color.parseColor("#FF00BFA5"),Color.parseColor("#FF00BFA5")},
                new float[]{0, 1,2}, Shader.TileMode.CLAMP);

        tv.getPaint().setShader(textShader);
    }
}