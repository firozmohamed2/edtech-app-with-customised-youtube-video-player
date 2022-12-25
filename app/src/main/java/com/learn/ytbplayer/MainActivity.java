package com.learn.ytbplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton forwardButton ;
    ImageButton backwardButton ;
    ImageButton playPauseButton ;
    Button optionButtonA ,optionButtonB,optionButtonC, optionButtonD;

    ConstraintLayout optionLayout ;


    int rightAnswer;
    int startTime;
    int endTime;
    int nextRightId;
    int nextWrongId;

    int select;
    int currentLevel;



    String[] startArray;
    String[] endArray;
    String[] rightOptionArray;
    String[] rightArray;
    String[] wrongArray;
    String videoId;


     FirebaseFirestore db;
    private static final String TAG = "Firestore";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();


        forwardButton = findViewById(R.id.forward_btn);
         backwardButton = findViewById(R.id.backward_btn);
         playPauseButton = findViewById(R.id.play_pause_button);


         optionButtonA = findViewById(R.id.option_button_A);
         optionButtonB = findViewById(R.id.option_button_B);
         optionButtonC = findViewById(R.id.option_button_C);
         optionButtonD = findViewById(R.id.option_button_D);

         optionLayout = findViewById(R.id.option_layout);


         rightAnswer=0;
         startTime=0;
         endTime=1000;
         nextRightId=-1;
         nextWrongId=-1;

         select=-1;
         currentLevel=0;



       startArray= new String[]{"0", "100", "150", "170", "190"};
         endArray= new String[]{"10", "110", "160", "175", "195"};
        rightOptionArray= new String[]{"2", "-1", "0", "-1", "-1"};
         rightArray= new String[]{"1", "3", "3", "-1", "-1"};
         wrongArray= new String[]{"2", "4", "4", "-1", "-1"};



        playPauseButton.setTag(R.drawable.pause);











        startTime=Integer.parseInt(startArray[0]);
        endTime=Integer.parseInt(endArray[0]);
        rightAnswer=Integer.parseInt(rightOptionArray[0]);
        nextRightId=Integer.parseInt(rightArray[0]);
        nextWrongId=Integer.parseInt(wrongArray[0]);



        if(rightAnswer==-1){
            optionLayout.setVisibility(View.GONE);
        }
        else{
            optionLayout.setVisibility(View.VISIBLE);
        }






        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
                .controls(0).rel(0)
                .ivLoadPolicy(1)
                .ccLoadPolicy(1)
                .build();

        getLifecycle().addObserver(youTubePlayerView);

        YouTubePlayerTracker tracker = new YouTubePlayerTracker();








        youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
           @Override
           public void onReady(@NonNull YouTubePlayer youTubePlayer) {
               super.onReady(youTubePlayer);
           }
       },true,iFramePlayerOptions);


        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                //firebase data retrieve check
                DocumentReference docRef = db.collection("class10notes").document("ap1");
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());


                                videoId= document.getData().get("videoid").toString();
                                List<String> startList = (List<String>) document.get("startArray");
                                List<String> endList = (List<String>) document.get("endArray");
                                List<String> rightOptionList = (List<String>) document.get("rightOptionArray");
                                List<String> rightList = (List<String>) document.get("rightArray");
                                List<String> wrongList = (List<String>) document.get("wrongArray");

                                Toast.makeText(MainActivity.this, "DocumentSnapshot data: " + startList.get(0),
                                        Toast.LENGTH_SHORT).show();



                                //startArray= (String[]) document.getData().get("startArray");
//                                endArray= new String[]{"10", "110", "160", "175", "195"};
//                                rightOptionArray= new String[]{"2", "-1", "0", "-1", "-1"};
//                                rightArray= new String[]{"1", "3", "3", "-1", "-1"};
//                                wrongArray= new String[]{"2", "4", "4", "-1", "-1"};
//                                Toast.makeText(MainActivity.this, "DocumentSnapshot data: " + videoId,
//                                        Toast.LENGTH_SHORT).show();

                                youTubePlayer.loadVideo(videoId, 0);
                                youTubePlayer.addListener(tracker);


                            } else {
                                Log.d(TAG, "No such document");
                                Toast.makeText(MainActivity.this, "No such document",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

                //check complete




//                videoId = "9fY5wPpK3FM";
//                youTubePlayer.loadVideo(videoId, 0);
//                youTubePlayer.addListener(tracker);


                forwardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        youTubePlayer.seekTo(25);

                    }
                });


                backwardButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        youTubePlayer.seekTo(10);

                    }
                });


                playPauseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((Integer) playPauseButton.getTag()) == R.drawable.pause) {
                            youTubePlayer.pause();
                            playPauseButton.setTag(R.drawable.play);
                        }
                        else{
                            youTubePlayer.play();
                            playPauseButton.setTag(R.drawable.pause);
                        }

                    }
                });



                optionButtonA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected(optionButtonA,0,youTubePlayer);

                    }
                });


                optionButtonB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected(optionButtonB,1,youTubePlayer);

                    }
                });


                optionButtonC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected(optionButtonC,2,youTubePlayer);

                    }
                });


                optionButtonD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected(optionButtonD,3,youTubePlayer);

                    }
                });






            }
        });


        youTubePlayerView.addYouTubePlayerListener(new YouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

            }

            @Override
            public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState playerState) {
                if(playerState.toString().equals("PAUSED")){
                    playPauseButton.setImageResource(R.drawable.play);
                    Toast.makeText(MainActivity.this, String.valueOf(tracker.getCurrentSecond()), Toast.LENGTH_SHORT).show();

                }

                if(playerState.toString().equals("PLAYING")){
                    playPauseButton.setImageResource(R.drawable.pause);

                }




            }

            @Override
            public void onPlaybackQualityChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackQuality playbackQuality) {

            }

            @Override
            public void onPlaybackRateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackRate playbackRate) {

            }

            @Override
            public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerError playerError) {

            }

            @Override
            public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float v) {


                if(v>endTime){
                    if(rightAnswer!=-1){
                        youTubePlayer.pause();
                    }
                    else{
                        if( Integer.parseInt(rightArray[currentLevel])==-1){
                            youTubePlayer.pause();
                        }
                        else{
                            currentLevel=Integer.parseInt(rightArray[currentLevel]);
                            startTime=Integer.parseInt(startArray[currentLevel]);
                            endTime=Integer.parseInt(endArray[currentLevel]);
                            rightAnswer=Integer.parseInt(rightOptionArray[currentLevel]);
                            nextRightId=Integer.parseInt(rightArray[currentLevel]);
                            nextWrongId=Integer.parseInt(wrongArray[currentLevel]);
                            youTubePlayer.seekTo(startTime);
                            youTubePlayer.play();

                        }

                    }
                }










//                           if(v>5 && v<5.52){
//                               Toast.makeText(MainActivity.this, String.valueOf(v), Toast.LENGTH_SHORT).show();
//                           }






            }

            @Override
            public void onVideoDuration(@NonNull YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoLoadedFraction(@NonNull YouTubePlayer youTubePlayer, float v) {

            }

            @Override
            public void onVideoId(@NonNull YouTubePlayer youTubePlayer, @NonNull String s) {

            }

            @Override
            public void onApiChange(@NonNull YouTubePlayer youTubePlayer) {

            }
        });




//        tracker.getState();
//
//        tracker.getVideoDuration();
//        tracker.getVideoId();

    }


    void selected(Button selectedBtn,int num,YouTubePlayer player) {
        if (rightAnswer == -1) {
            return;
        }

        Toast.makeText(MainActivity.this, Integer.toString(rightAnswer), Toast.LENGTH_SHORT).show();


        if (select == -1) {
            select = num;
            if (num == rightAnswer) {
                selectedBtn.setBackgroundColor(Color.parseColor("#00ff00"));

                selectedBtn.setTextColor(Color.parseColor("#ffffff"));

                if (Integer.parseInt(rightArray[currentLevel]) == -1) {
                    player.pause();
                } else {
                    currentLevel = Integer.parseInt(rightArray[currentLevel]);
                    startTime = Integer.parseInt(startArray[currentLevel]);
                    endTime = Integer.parseInt(endArray[currentLevel]);
                    rightAnswer = Integer.parseInt(rightOptionArray[currentLevel]);
                    nextRightId = Integer.parseInt(rightArray[currentLevel]);
                    nextWrongId = Integer.parseInt(wrongArray[currentLevel]);
                    player.seekTo(startTime);
                    player.play();

                }
            } else {

                selectedBtn.setBackgroundColor(Color.parseColor("#ff0000"));

                selectedBtn.setTextColor(Color.parseColor("#ffffff"));
                if (rightAnswer == 0) {
                    optionButtonA.setBackgroundColor(Color.parseColor("#00ff00"));
                    optionButtonA.setTextColor(Color.parseColor("#ffffff"));
                } else if (rightAnswer == 1) {
                    optionButtonB.setBackgroundColor(Color.parseColor("#00ff00"));
                    optionButtonB.setTextColor(Color.parseColor("#ffffff"));
                } else if (rightAnswer == 2) {
                    optionButtonC.setBackgroundColor(Color.parseColor("#00ff00"));
                    optionButtonC.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    optionButtonD.setBackgroundColor(Color.parseColor("#00ff00"));
                    optionButtonD.setTextColor(Color.parseColor("#ffffff"));
                }

                if (Integer.parseInt(wrongArray[currentLevel]) == -1) {
                    player.pause();
                } else {
                    currentLevel = Integer.parseInt(wrongArray[currentLevel]);
                    startTime = Integer.parseInt(startArray[currentLevel]);
                    endTime = Integer.parseInt(endArray[currentLevel]);
                    rightAnswer = Integer.parseInt(rightOptionArray[currentLevel]);
                    nextRightId = Integer.parseInt(rightArray[currentLevel]);
                    nextWrongId = Integer.parseInt(wrongArray[currentLevel]);
                    player.seekTo(startTime);
                    player.play();

                }

            }


        }

        select = -1;

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                clear(optionButtonA);
                clear(optionButtonB);
                clear(optionButtonC);
                clear(optionButtonD);
                if (rightAnswer == -1) {
                    optionLayout.setVisibility(View.GONE);
                } else {
                    optionLayout.setVisibility(View.VISIBLE);
                }
            }
        }, 5000);
    }


    void clear(Button button){
        button.setBackgroundColor(Color.parseColor("#ffffff"));
        button.setTextColor(Color.parseColor("#000000"));
    }




    }


