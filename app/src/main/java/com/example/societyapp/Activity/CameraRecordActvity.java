package com.example.societyapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import com.example.societyapp.R;

public class CameraRecordActvity extends AppCompatActivity {
    Toolbar toolbar;
    VideoView videoview1, videoview2, videoview3, videoview4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_record_actvity);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        videoview1 = findViewById(R.id.videoview1);


       /* videoview1.setVideoPath("/mnt/sdcard/Movies/com.bnb.giggle/IMG_20130415184609.mp4");
        videoview1.start();*/
    }
}
