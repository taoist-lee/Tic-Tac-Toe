package com.tictactoe.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation textanim1, textanim2, textanim3;
    TextView t1, t2, t3, c1, c2, i, a, o, e;

    private static int SPLASH_SCREEN = 1250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hides Status Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Animations
        textanim1 = AnimationUtils.loadAnimation(this, R.anim.textfade);
        textanim2 = AnimationUtils.loadAnimation(this, R.anim.textfade);
        textanim3 = AnimationUtils.loadAnimation(this, R.anim.textfade);

        //Hooks
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        i = findViewById(R.id.i);
        a = findViewById(R.id.a);
        o = findViewById(R.id.o);
        e = findViewById(R.id.e);

        //Delays
        textanim2.setStartOffset(200);
        textanim3.setStartOffset(400);

        //Start Animations
        t1.startAnimation(textanim1);
        t2.startAnimation(textanim3);
        t3.startAnimation(textanim3);
        c1.startAnimation(textanim2);
        c2.startAnimation(textanim1);
        i.startAnimation(textanim3);
        a.startAnimation(textanim2);
        o.startAnimation(textanim1);
        e.startAnimation(textanim2);

        //Delayed Splash Screen Exit
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentModeSelection = new Intent(MainActivity.this, ModeSelection.class);
                startActivity(intentModeSelection);
                finish();      //Destroy SplashScreen from Activity List to prevent returning back on exit.
            }
        }, SPLASH_SCREEN);
    }
}
