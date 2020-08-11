package com.example.societyapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.societyapp.R;
import com.example.societyapp.Utils.RegPrefManager;

public class SplashScreenActivity extends AppCompatActivity {
    RelativeLayout relative;
    private static final int TIME = 5 * 1000;// 4 seconds
    private SharedPreferences shp;
    //ImageView logo;
    TextView text;
    ImageView image;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        image = findViewById(R.id.image);

        relative= (RelativeLayout) findViewById(R.id.relative);
        relative.getBackground().setAlpha(100);
        final Animation Rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pump_bottom);
        final Animation Rotat = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_in);
        final Animation Rotat1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_in);
        final Animation Rt=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);

        userId = RegPrefManager.getInstance(this).getUserId();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (userId!=null){
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
                    // startActivity(new Intent(SplashActivity.this,MainActivity.class));;
                }
                else {
                    Intent i = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    finish();
                }

            }
        }, TIME);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { } }, TIME);

    }
}