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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

import pl.droidsonroids.gif.GifImageView;


public class Game3Activity extends AppCompatActivity {
    ImageView resultIV;
    ImageView taskIV;
    GifImageView successGif;
    TextView redTV;
    TextView greenTV;
    TextView blueTV;
    SeekBar redSB;
    SeekBar greenSB;
    SeekBar blueSB;

    int taskColor, red, green, blue;
    MediaPlayer sMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game3);

        sMP = MediaPlayer.create(this, R.raw.level_up);

        final ImageButton infoIB = findViewById(R.id.infoIB);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        FrameLayout.LayoutParams infoParams = (FrameLayout.LayoutParams) infoIB.getLayoutParams();
        infoParams.height = size.y / 12;
        infoParams.width = infoParams.height;
        infoIB.setLayoutParams(infoParams);

        LinearLayout seekBarsLL = findViewById(R.id.seekBarsLL);
        seekBarsLL.setPadding(0, size.y / 10, 0, size.y / 10);

        resultIV = findViewById(R.id.resultIV);
        taskIV = findViewById(R.id.taskIV);
        successGif = findViewById(R.id.success);
        successGif.setVisibility(View.GONE);
        FrameLayout.LayoutParams gifParams = (FrameLayout.LayoutParams) successGif.getLayoutParams();
        gifParams.setMargins(size.y / 10, size.y / 20, 0, size.y / 20);
        successGif.setLayoutParams(gifParams);

        redTV = findViewById(R.id.redTV);
        greenTV = findViewById(R.id.greenTV);
        blueTV = findViewById(R.id.blueTV);
        redTV.setTextSize(size.x / 48 > (int) (size.y / 22.5) ? (int) (size.y / 22.5) : size.x / 48);
        greenTV.setTextSize(size.x / 48 > (int) (size.y / 22.5) ? (int) (size.y / 22.5) : size.x / 48);
        blueTV.setTextSize(size.x / 48 > (int) (size.y / 22.5) ? (int) (size.y / 22.5) : size.x / 48);

        redSB = findViewById(R.id.redSB);
        greenSB = findViewById(R.id.greenSB);
        blueSB = findViewById(R.id.blueSB);

        //updateBackground();

        redSB.setOnSeekBarChangeListener(seekBarChangeListener);
        greenSB.setOnSeekBarChangeListener(seekBarChangeListener);
        blueSB.setOnSeekBarChangeListener(seekBarChangeListener);

        infoIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putExtra("activity", "game3");
                startActivity(intent);
            }
        });
        infoIB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        infoIB.setColorFilter(0x65000000, PorterDuff.Mode.SRC_ATOP);
                        infoIB.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        infoIB.clearColorFilter();
                        infoIB.invalidate();
                        break;
                    }
                }
                return false;
            }
        });

        newGame();
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            updateBackground();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    //изменение значений бегунков
    private void newGame() {
        Random rnd = new Random();
        red = rnd.nextInt(256);
        green = rnd.nextInt(256);
        blue = rnd.nextInt(256);

        redSB.setProgress(127);
        greenSB.setProgress(127);
        blueSB.setProgress(127);

        taskColor = android.graphics.Color.rgb(red, green, blue);
        taskIV.setColorFilter(taskColor);
        resultIV.setColorFilter(android.graphics.Color.rgb(123, 123, 123));
    }

    //изменение значений бегунков
    private void updateBackground() {
        int accuracy = 40;
        int redValue, greenValue, blueValue;
        redValue = redSB.getProgress();
        greenValue = greenSB.getProgress();
        blueValue = blueSB.getProgress();

        redTV.setText(String.valueOf(redValue));
        greenTV.setText(String.valueOf(greenValue));
        blueTV.setText(String.valueOf(blueValue));

        int RGB = android.graphics.Color.rgb(redValue, greenValue, blueValue);

        resultIV.setColorFilter(RGB);

        if (((redValue > red - accuracy) & (redValue < red + accuracy))
                & ((greenValue > green - accuracy) & (greenValue < green + accuracy))
                & ((blueValue > blue - accuracy) & (blueValue < blue + accuracy))) {
            sMP.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    successGif.setVisibility(View.VISIBLE);
                }
            }, 100);
            resultIV.setColorFilter(taskColor);
            redTV.setText(String.valueOf(red));
            greenTV.setText(String.valueOf(green));
            blueTV.setText(String.valueOf(blue));
            redSB.setProgress(red);
            greenSB.setProgress(green);
            blueSB.setProgress(blue);
            redSB.setEnabled(false);
            greenSB.setEnabled(false);
            blueSB.setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    successGif.setVisibility(View.GONE);
                    newGame();
                    redSB.setEnabled(true);
                    greenSB.setEnabled(true);
                    blueSB.setEnabled(true);
                }
            }, 1500);
        }
    }

    @Override
    public void onBackPressed() {
        if (sMP != null)
            sMP.stop();
        finish();
        super.onBackPressed();
    }
}
