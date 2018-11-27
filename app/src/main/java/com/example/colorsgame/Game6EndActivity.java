package com.example.colorsgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class Game6EndActivity extends AppCompatActivity {
    TextView result;
    TextView record;
    TextView upPoint;
    TextView downPoint;
    TextView newRecord, recordT;
    GifImageView firework;
    ImageButton menu;
    ImageButton retry;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game6_end);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        upPoint = findViewById(R.id.up_point);
        upPoint.setTextSize(size.x / 30 > size.y / 53 ? size.y / 30 : size.x / 53);
        downPoint = findViewById(R.id.down_point);
        downPoint.setTextSize(size.x / 30 > size.y / 53 ? size.y / 30 : size.x / 53);
        result = findViewById(R.id.result);
        result.setTextSize(size.x / 8 > (int) (size.y / 4.5) ? (int) (size.y / 4.5) : size.x / 8);
        recordT = findViewById(R.id.recordT);
        recordT.setTextSize(size.x / 40 > (int) (size.y / 22.5) ? (int) (size.y / 22.5) : size.x / 40);
        record = findViewById(R.id.record);
        record.setTextSize(size.x / 15 > (int) (size.y / 8.4) ? (int) (size.y / 8.4) : size.x / 15);
        newRecord = findViewById(R.id.new_record);
        newRecord.setTextSize(size.x / 40 > (int) (size.y / 22.5) ? (int) (size.y / 22.5) : size.x / 40);
        firework = findViewById(R.id.firework);
        menu = findViewById(R.id.menu);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) menu.getLayoutParams();
        params.bottomMargin = size.y / 40;
        params.topMargin = size.y / 40;
        menu.setLayoutParams(params);
        retry = findViewById(R.id.retry);

        Intent intent = getIntent();
        int up = intent.getIntExtra("up", 0);
        upPoint.setText(String.valueOf(up));
        int down = intent.getIntExtra("down", 0);
        downPoint.setText(String.valueOf(down));
        result.setText(String.valueOf(up - down));
        int rec = intent.getIntExtra("record", 0);
        if (up - down > rec) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.level_up);
                    mp.start();
                }
            }, 200);
            record.setText(String.valueOf(up - down));
            firework.setVisibility(View.VISIBLE);
        } else {
            newRecord.setVisibility(View.INVISIBLE);
            record.setText(String.valueOf(rec));
        }

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        retry.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (retry.isClickable()) {
                            retry.setColorFilter(0x45000000, PorterDuff.Mode.SRC_ATOP);
                            retry.invalidate();
                            break;
                        }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        retry.clearColorFilter();
                        retry.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Game6Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mp != null)
            mp.stop();
        finish();
        super.onBackPressed();
    }
}
