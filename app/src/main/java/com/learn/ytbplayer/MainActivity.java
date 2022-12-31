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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton forwardButton ;
    ImageButton backwardButton ;
    ImageButton playPauseButton ;
    Button optionButtonA ,optionButtonB,optionButtonC, optionButtonD;
    Button clearButton, notClearButton;

    ConstraintLayout optionLayout,clearLayout ;


    int rightAnswer;
    int startTime;
    int endTime;
    int nextRightId;
    int nextWrongId;

    int select;
    int currentLevel;



//    String[] startArray;
//    String[] endArray;
//    String[] rightOptionArray;
//    String[] rightArray;
//    String[] wrongArray;
    String videoId;

    List<String> startList = new ArrayList<>();
    List<String> endList = new ArrayList<>();
    List<String> rightOptionList = new ArrayList<>();
    List<String> rightList = new ArrayList<>();
    List<String> wrongList = new ArrayList<>();


     FirebaseFirestore db;
    private static final String TAG = "Firestore";


    String value,chapter;

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
        clearButton = findViewById(R.id.button_clear);
        notClearButton = findViewById(R.id.button_not_clear);

         optionLayout = findViewById(R.id.option_layout);
        clearLayout = findViewById(R.id.clear_or_not_layout);


         rightAnswer=0;
         startTime=0;
         endTime=1000;
         nextRightId=-1;
         nextWrongId=-1;

         select=-1;
         currentLevel=0;



//       startArray= new String[]{"0", "100", "150", "170", "190"};
//         endArray= new String[]{"10", "110", "160", "175", "195"};
//        rightOptionArray= new String[]{"2", "-1", "0", "-1", "-1"};
//         rightArray= new String[]{"1", "3", "3", "-1", "-1"};
//         wrongArray= new String[]{"2", "4", "4", "-1", "-1"};



        playPauseButton.setTag(R.drawable.pause);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("key");
            chapter = extras.getString("chapter");


            //The key argument here must match that used in the other activity
        }















        if(rightAnswer==-1){
            optionLayout.setVisibility(View.GONE);
            clearLayout.setVisibility(View.GONE);



        }
        else {
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
                DocumentReference docRef = db.collection("class11notes").document("content").
                        collection(chapter).document(value);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());


                                videoId= document.getData().get("videoid").toString();
                                 startList = (List<String>) document.get("startArray");
                                 endList = (List<String>) document.get("endArray");
                                 rightOptionList = (List<String>) document.get("rightOptionArray");
                                 rightList = (List<String>) document.get("rightArray");
                                 wrongList = (List<String>) document.get("wrongArray");




                                startTime=Integer.parseInt(startList.get(0));

                                endTime=Integer.parseInt(endList.get(0));
//                                Toast.makeText(MainActivity.this, "DocumentSnapshot data: " + endTime,
//                                        Toast.LENGTH_SHORT).show();
                                rightAnswer=Integer.parseInt(rightOptionList.get(0));
                                nextRightId=Integer.parseInt(rightList.get(0));
                                nextWrongId=Integer.parseInt(wrongList.get(0));



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
//                                Toast.makeText(MainActivity.this, "No such document",
//                                        Toast.LENGTH_SHORT).show();
                                Toast.makeText(MainActivity.this, "Sorry for inconvenience",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                            Log.d(TAG, "No such document");

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
                        if( Integer.parseInt(rightList.get(currentLevel))==-1){
                            youTubePlayer.pause();
                        }
                        else{
                            currentLevel=Integer.parseInt(rightList.get(currentLevel));
                            startTime=Integer.parseInt(startList.get(currentLevel));
                            endTime=Integer.parseInt(endList.get(currentLevel));
                            rightAnswer=Integer.parseInt(rightOptionList.get(currentLevel));
                            nextRightId=Integer.parseInt(rightList.get(currentLevel));
                            nextWrongId=Integer.parseInt(wrongList.get(currentLevel));
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
                selectedBtn.setBackground(getResources().getDrawable(R.drawable.button_bg_green));


                selectedBtn.setTextColor(Color.parseColor("#ffffff"));

                if (Integer.parseInt(rightList.get(currentLevel)) == -1) {
                    player.pause();
                } else {
                    currentLevel=Integer.parseInt(rightList.get(currentLevel));
                    startTime=Integer.parseInt(startList.get(currentLevel));
                    endTime=Integer.parseInt(endList.get(currentLevel));
                    rightAnswer=Integer.parseInt(rightOptionList.get(currentLevel));
                    nextRightId=Integer.parseInt(rightList.get(currentLevel));
                    nextWrongId=Integer.parseInt(wrongList.get(currentLevel));
                    player.seekTo(startTime);
                    player.play();

                }
            } else {

                selectedBtn.setBackground(getResources().getDrawable(R.drawable.button_bg_red));

                selectedBtn.setTextColor(Color.parseColor("#ffffff"));
                if (rightAnswer == 0) {
                    optionButtonA.setBackground(getResources().getDrawable(R.drawable.button_bg_green));
                    optionButtonA.setTextColor(Color.parseColor("#ffffff"));
                } else if (rightAnswer == 1) {
                    optionButtonB.setBackground(getResources().getDrawable(R.drawable.button_bg_green));
                    optionButtonB.setTextColor(Color.parseColor("#ffffff"));
                } else if (rightAnswer == 2) {
                    optionButtonC.setBackground(getResources().getDrawable(R.drawable.button_bg_green));
                    optionButtonC.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    optionButtonD.setBackground(getResources().getDrawable(R.drawable.button_bg_green));
                    optionButtonD.setTextColor(Color.parseColor("#ffffff"));
                }

                if (Integer.parseInt(wrongList.get(currentLevel)) == -1) {
                    player.pause();
                } else {
                    currentLevel=Integer.parseInt(wrongList.get(currentLevel));
                    startTime=Integer.parseInt(startList.get(currentLevel));
                    endTime=Integer.parseInt(endList.get(currentLevel));
                    rightAnswer=Integer.parseInt(rightOptionList.get(currentLevel));
                    nextRightId=Integer.parseInt(rightList.get(currentLevel));
                    nextWrongId=Integer.parseInt(wrongList.get(currentLevel));
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
        button.setBackground(getResources().getDrawable(R.drawable.button_bg));
        button.setTextColor(Color.parseColor("#000000"));
    }




    }


