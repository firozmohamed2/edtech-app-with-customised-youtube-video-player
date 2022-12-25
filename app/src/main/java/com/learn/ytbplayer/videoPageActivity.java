package com.learn.ytbplayer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class videoPageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "AnonymousAuth";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_page);

        mAuth = FirebaseAuth.getInstance();
        signInAnonymously();



        LinearLayout layout1= findViewById(R.id.layout_1);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(videoPageActivity.this,ChapterActivity.class);
                startActivity(intent);
            }
        });

        TextView tv = findViewById(R.id.gradient_tv);
        tv.setText("ADAPTIVE METHODOLOGY");
        tv.setTextColor(Color.parseColor("#F9D976"));
        Shader textShader = new LinearGradient(0, 90, tv.getPaint().measureText(tv.getText().toString()), tv.getTextSize(),
                new int[]{Color.parseColor("#FF6D00"), Color.parseColor("#FFFA4D4D"),Color.parseColor("#FF00BFA5")},
                new float[]{0, 1,2}, Shader.TileMode.CLAMP);

        tv.getPaint().setShader(textShader);





    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser user) {

    }


    private void signInAnonymously() {
        // [START signin_anonymously]
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            Toast.makeText(videoPageActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(videoPageActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END signin_anonymously]
    }


}