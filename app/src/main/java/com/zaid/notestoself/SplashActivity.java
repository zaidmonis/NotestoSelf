package com.zaid.notestoself;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        textView = findViewById(R.id.splashText);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        textView.startAnimation(animation);

        init();
    }

    private void init() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), FirstActivity.class));
                finish();
            }
        }, 1000);
    }
}
