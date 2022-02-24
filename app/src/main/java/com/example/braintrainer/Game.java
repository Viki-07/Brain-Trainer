package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class Game extends AppCompatActivity {
    TextView quest;
    TextView button0;
    TextView button1;
    TextView button2;
    TextView button3;
    TextView score;
    TextView timetext;
    TextView gameover;
    TextView scoretext;
    ImageButton playAgain;

    ArrayList<Integer>answers=new ArrayList<Integer>();
    int ans;
    int ansop;
    int count=0;
    int limit=1;
    private CountDownTimer cTimer;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        quest=findViewById(R.id.quest);
        button0=findViewById(R.id.op1);
        button1=findViewById(R.id.op2);
        button2=findViewById(R.id.op3);
        button3=findViewById(R.id.op4);
        score=findViewById(R.id.scor);
        timetext=findViewById(R.id.time);
        gameover=findViewById(R.id.gameovertext);
        playAgain=findViewById(R.id.playagainbutton);
        scoretext=findViewById(R.id.scoretext);


        CountDownTimer cTimer = null;
        process();

    }
    public void repeatPlay(View v)
    {
        count=0;
        limit=1;
        process();
    }
    public void counter(){

        long time;
        if(limit<7)
            time=16000;
        else
            time=6000;
        cTimer=new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long l) {
                timetext.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                gameover.setVisibility(View.VISIBLE);
                playAgain.setVisibility(View.VISIBLE);



            }

            private void repeatPlay() {
                count=0;
                limit=1;
                process();
            }
        }.start();
    }
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }



    public void process() {
        counter();


            scoretext.setVisibility(View.INVISIBLE);
            playAgain.setVisibility(View.INVISIBLE);
            gameover.setVisibility(View.INVISIBLE);

            score.setText(Integer.toString(count));
            ArrayList<Integer> answers = new ArrayList<Integer>();
            int a = (int) ((float) (Math.random() * 100));
            int b = (int) ((float) (Math.random() * 100));
            String q1 = String.valueOf(a);
            String q2 = String.valueOf(b);
            if(limit<11) {
                quest.setText(limit + ")  " + q1 + "+" + q2 + "= ?");
            }
            else {
                scoretext.setText("Your Score is "+count);
                scoretext.setVisibility(View.VISIBLE);
                quest.setText("Game Finished");

            }
            ans = a + b;
            ansop = ThreadLocalRandom.current().nextInt(0, 3 + 1);
            int wrongAns = 0;
            for (int i = 0; i < 4; i++) {
                if (i == ansop) {
                    answers.add(ans);
                } else {
                    wrongAns = (int) ((float) (Math.random() * 100));
                    if (wrongAns == ans) {
                        wrongAns = (int) ((float) (Math.random() * 100));

                    }
                    answers.add(wrongAns);

                }
            }

            button0.setText(String.valueOf(answers.get(0)));
            button1.setText(String.valueOf(answers.get(1)));
            button2.setText(String.valueOf(answers.get(2)));
            button3.setText(String.valueOf(answers.get(3)));
            limit++;
        }

        public void check (View v){
            if (Integer.toString(ansop).equals(v.getTag().toString())) {

                    if(limit<11) {
                        Toast.makeText(this, "Voila Correct Answer!!!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(this, "Game Finished !", Toast.LENGTH_SHORT).show();
                    count++;
                cancelTimer();
                    if(limit<12) {

                        process();
                    }
                gamefinisher();
            }
            else {
                if(limit<11) {
                    Toast.makeText(this, "Oops Wrong Answer!!!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this, "Game Finished !", Toast.LENGTH_SHORT).show();
                cancelTimer();
                if(limit<12) {

                    process();
                }
                gamefinisher();
            }
        }
        public void gamefinisher(){
        if(limit==12){
            cancelTimer();
            playAgain.setVisibility(View.VISIBLE);
            if (count==10) {
                gameover.setText("Congrats You Answered all Correct");
                gameover.setVisibility(View.VISIBLE);
                        }
                 }
        }
        public void reset(View view){
        cancelTimer();
            count=0;
            limit=1;
            process();
        }




}