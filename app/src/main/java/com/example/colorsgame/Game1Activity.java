package com.example.colorsgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import pl.droidsonroids.gif.GifImageView;

public class Game1Activity extends AppCompatActivity {
    ImageButton infoIB;
    ImageView plusIV;
    ImageView equationIV;
    GifImageView successGif;
    GifImageView failGif;
    GifImageView finishGif;
    ImageButton[] colorsIB;
    ImageView[] colorsIV;
    TextView color1TV;
    TextView color2TV;
    TextView color3TV;
    ImageView color1IV;
    ImageView color2IV;
    ImageView color3IV;

    ArrayList<ResultColor> resColors;
    boolean firstClick;
    int n = 0;
    String[] mainColorsTitles = {"Белый", "Красный", "Жёлтый", "Синий", "Чёрный"};
    int[] mainColors;
    int[] clickedColors;

    MediaPlayer clickMP;
    MediaPlayer sMP;
    MediaPlayer fMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game1);

        sMP = MediaPlayer.create(this, R.raw.positive_action);
        fMP = MediaPlayer.create(this, R.raw.sad_whisle);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        infoIB = findViewById(R.id.infoIB);
        FrameLayout.LayoutParams infoParams = (FrameLayout.LayoutParams) infoIB.getLayoutParams();
        infoParams.height = size.y / 12;
        infoParams.width = infoParams.height;
        infoIB.setLayoutParams(infoParams);

        colorsIV = new ImageView[5];
        colorsIV[0] = findViewById(R.id.color1BIV);
        colorsIV[1] = findViewById(R.id.color2BIV);
        colorsIV[2] = findViewById(R.id.color3BIV);
        colorsIV[3] = findViewById(R.id.color4BIV);
        colorsIV[4] = findViewById(R.id.color5BIV);
        for (ImageView iv : colorsIV) {
            FrameLayout.LayoutParams ivParams = (FrameLayout.LayoutParams) iv.getLayoutParams();
            ivParams.setMargins(size.y / 17, size.y / 17, size.y / 17, size.y / 17);
            iv.setLayoutParams(ivParams);
        }

        plusIV = findViewById(R.id.plusIV);
        equationIV = findViewById(R.id.equationIV);
        successGif = findViewById(R.id.success);
        failGif = findViewById(R.id.fail);
        finishGif = findViewById(R.id.finish);
        finishGif.setVisibility(View.GONE);

        color1TV = findViewById(R.id.color1TV);
        color2TV = findViewById(R.id.color2TV);
        color3TV = findViewById(R.id.color3TV);
        color1TV.setTextSize(size.x / 64 > size.y / 36 ? size.y / 36 : size.x / 64);
        color2TV.setTextSize(size.x / 64 > size.y / 36 ? size.y / 36 : size.x / 64);
        color3TV.setTextSize(size.x / 64 > size.y / 36 ? size.y / 36 : size.x / 64);

        color1IV = findViewById(R.id.color1IV);
        color2IV = findViewById(R.id.color2IV);
        color3IV = findViewById(R.id.color3IV);

        colorsIB = new ImageButton[5];
        colorsIB[0] = findViewById(R.id.color1IB);
        colorsIB[1] = findViewById(R.id.color2IB);
        colorsIB[2] = findViewById(R.id.color3IB);
        colorsIB[3] = findViewById(R.id.color4IB);
        colorsIB[4] = findViewById(R.id.color5IB);

        mainColors = new int[5];

        mainColors[0] = ContextCompat.getColor(this, R.color.colorWhite);
        mainColors[1] = ContextCompat.getColor(this, R.color.colorRed);
        mainColors[2] = ContextCompat.getColor(this, R.color.colorYellow);
        mainColors[3] = ContextCompat.getColor(this, R.color.colorBlue);
        mainColors[4] = ContextCompat.getColor(this, R.color.colorBlack);

        resColors = new ArrayList<>();

        ResultColor green = new ResultColor();
        green.title = "Зелёный";
        green.color1 = 2;
        green.color2 = 3;
        green.color = ContextCompat.getColor(this, R.color.colorGreen);
        resColors.add(green);

        ResultColor violet = new ResultColor();
        violet.title = "Фиолетовый";
        violet.color1 = 1;
        violet.color2 = 3;
        violet.color = ContextCompat.getColor(this, R.color.colorViolet);
        resColors.add(violet);

        ResultColor orange = new ResultColor();
        orange.title = "Оранжевый";
        orange.color1 = 1;
        orange.color2 = 2;
        orange.color = ContextCompat.getColor(this, R.color.colorOrange);
        resColors.add(orange);

        ResultColor pink = new ResultColor();
        pink.title = "Розовый";
        pink.color1 = 0;
        pink.color2 = 1;
        pink.color = ContextCompat.getColor(this, R.color.colorPink);
        resColors.add(pink);

        ResultColor lightBlue = new ResultColor();
        lightBlue.title = "Голубой";
        lightBlue.color1 = 0;
        lightBlue.color2 = 3;
        lightBlue.color = ContextCompat.getColor(this, R.color.colorLightBlue);
        resColors.add(lightBlue);

        ResultColor grey = new ResultColor();
        grey.title = "Серый";
        grey.color1 = 0;
        grey.color2 = 4;
        grey.color = ContextCompat.getColor(this, R.color.colorGrey);
        resColors.add(grey);

        ResultColor darkRed = new ResultColor();
        darkRed.title = "Тёмно-красный";
        darkRed.color1 = 1;
        darkRed.color2 = 4;
        darkRed.color = ContextCompat.getColor(this, R.color.colorDarkRed);
        resColors.add(darkRed);

        ResultColor darkBlue = new ResultColor();
        darkBlue.title = "Тёмно-синий";
        darkBlue.color1 = 3;
        darkBlue.color2 = 4;
        darkBlue.color = ContextCompat.getColor(this, R.color.colorDarkBlue);
        resColors.add(darkBlue);

        infoIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putExtra("activity", "game1");
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

        for (int i = 0; i < 5; i++) {
            final int ii = i;
            colorsIB[ii].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickMP != null) {
                        clickMP.reset();
                        clickMP.release();
                    }
                    clickMP = MediaPlayer.create(getApplicationContext(), R.raw.balloon_pop);
                    clickMP.start();
                    if (firstClick) {
                        color1IV.setColorFilter(mainColors[ii]);
                        color1TV.setText(mainColorsTitles[ii]);
                        firstClick = false;
                        clickedColors[0] = ii;
                    } else {
                        color2IV.setColorFilter(mainColors[ii]);
                        color2TV.setText(mainColorsTitles[ii]);
                        clickedColors[1] = ii;
                        checkColors(clickedColors);
                    }
                }
            });
            colorsIB[ii].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (colorsIB[ii].isClickable()) {
                                colorsIB[ii].setColorFilter(0x40000000, PorterDuff.Mode.SRC_ATOP);
                                colorsIB[ii].invalidate();
                                break;
                            }
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL: {
                            colorsIB[ii].clearColorFilter();
                            colorsIB[ii].invalidate();
                            break;
                        }
                    }
                    return false;
                }
            });
        }

        Collections.shuffle(resColors);

        round();
    }

    public void round() {
        successGif.setVisibility(View.GONE);
        failGif.setVisibility(View.GONE);
        color1IV.clearColorFilter();
        color1TV.setText("?");
        color2IV.clearColorFilter();
        color2TV.setText("?");
        firstClick = true;
        color3IV.setColorFilter(resColors.get(n).color);
        color3TV.setText(resColors.get(n).title);
        clickedColors = new int[2];
        buttonsClickManager(true);
    }

    public void checkColors(int[] clColors) {
        if ((resColors.get(n).color1 == clColors[0] && resColors.get(n).color2 == clColors[1])
                || (resColors.get(n).color1 == clColors[1] && resColors.get(n).color2 == clColors[0])) {
            n++;
            sMP.start();
            buttonsClickManager(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    successGif.setVisibility(View.VISIBLE);
                }
            }, 100);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (n < resColors.size()) round();
                    else {
                        buttonsClickManager(false);
                        infoIB.setClickable(false);
                        color1TV.setText("");
                        color2TV.setText("");
                        color3TV.setText("");
                        color1IV.setImageDrawable(null);
                        color2IV.setImageDrawable(null);
                        color3IV.setImageDrawable(null);
                        plusIV.setImageDrawable(null);
                        equationIV.setImageDrawable(null);
                        successGif.setVisibility(View.GONE);
                        failGif.setVisibility(View.GONE);
                        finishGif.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 5000);
                    }
                }
            }, 1500);
        } else {
            fMP.start();
            buttonsClickManager(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    failGif.setVisibility(View.VISIBLE);
                }
            }, 100);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    round();
                }
            }, 1500);
        }
    }

    private void buttonsClickManager(boolean c) {
        for (ImageButton ib : colorsIB)
            if (c) ib.setClickable(true);
            else ib.setClickable(false);
    }
}

class ResultColor {
    String title;
    int color1;
    int color2;
    int color;
}