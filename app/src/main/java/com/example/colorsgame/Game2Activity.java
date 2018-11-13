package com.example.colorsgame;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class Game2Activity extends AppCompatActivity {
    ImageView arc1IV;
    ImageView arc5IV;
    ImageView arcsContourIV;
    GifImageView successGif;
    GifImageView failGif;

    int k = 0;
    ImageView[] arcsIV = new ImageView[3];
    int[] mixedColors = new int[3];
    ArrayList<Integer> playerComb;
    List<Integer> rightComb = Arrays.asList(1, 2, 3);
    ;
    int[] setOfColors;
    ImageButton[] colorsIB = new ImageButton[3];
    ImageView[] colorsIV = new ImageView[3];
    ImageView[] flowersIV = new ImageView[3];
    List<Integer> mix;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final ImageButton infoIB = findViewById(R.id.infoIB);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        FrameLayout.LayoutParams infoParams = (FrameLayout.LayoutParams) infoIB.getLayoutParams();
        infoParams.height = size.y / 12;
        infoParams.width = infoParams.height;
        infoIB.setLayoutParams(infoParams);

        FrameLayout rainbowFL = findViewById(R.id.rainbowFL);
        LinearLayout.LayoutParams rainbowParams = (LinearLayout.LayoutParams) rainbowFL.getLayoutParams();
        rainbowParams.setMargins(0, size.y / 10, 0, 0);
        rainbowFL.setLayoutParams(rainbowParams);

        successGif = findViewById(R.id.success);
        failGif = findViewById(R.id.fail);

        colorsIB[0] = findViewById(R.id.color1IB);
        colorsIB[1] = findViewById(R.id.color2IB);
        colorsIB[2] = findViewById(R.id.color3IB);

        colorsIV[0] = findViewById(R.id.color1IV);
        colorsIV[1] = findViewById(R.id.color2IV);
        colorsIV[2] = findViewById(R.id.color3IV);

        flowersIV[0] = findViewById(R.id.flower1IV);
        flowersIV[1] = findViewById(R.id.flower2IV);
        flowersIV[2] = findViewById(R.id.flower3IV);

        arc1IV = findViewById(R.id.arc1IV);
        arcsIV[0] = findViewById(R.id.arc2IV);
        arcsIV[1] = findViewById(R.id.arc3IV);
        arcsIV[2] = findViewById(R.id.arc4IV);
        arc5IV = findViewById(R.id.arc5IV);
        arcsContourIV = findViewById(R.id.arcsIV);

        newGame();

        infoIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putExtra("activity", "game2");
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

        for (int i = 0; i < 3; i++) {
            final int ii = i;
            colorsIB[ii].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            if (colorsIB[ii].isClickable()) {
                                flowersIV[ii].setColorFilter(0x99d3edff, PorterDuff.Mode.SRC_ATOP);
                                flowersIV[ii].invalidate();
                                break;
                            }
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            if (colorsIB[ii].isClickable()) {
                                flowersIV[ii].clearColorFilter();
                                flowersIV[ii].invalidate();
                                break;
                            }
                    }
                    return false;
                }
            });
            colorsIB[ii].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    arcsIV[k].setColorFilter(mixedColors[ii]);
                    colorsIB[ii].setColorFilter(0x99d3edff, PorterDuff.Mode.SRC_ATOP);
                    colorsIB[ii].setClickable(false);
                    colorsIV[ii].setColorFilter(0x99d3edff, PorterDuff.Mode.SRC_ATOP);
                    flowersIV[ii].setColorFilter(0x99d3edff, PorterDuff.Mode.SRC_ATOP);
                    playerComb.add(mix.get(ii));
                    if (k < 2) {
                        k++;
                        arcsIV[k].clearColorFilter();
                    } else
                        check();
                }
            });
        }
    }

    private void newGame() {
        Random r = new Random();
        int inVar = r.nextInt(3);           // число, которое не будет изменяться
        int f = r.nextInt(3);               // число, которое начнет изменяться первым
        if (f == inVar)
            f = (inVar + 1) % 3;                   // взять просто следующее число...
        int c = r.nextInt(200) + 25;        // color[inVar] = c
        int d = (r.nextInt(5) + 5) * 10;    // на сколько будут изменяться значения r/g/b
        boolean redir = false;                     // color[f]:  false => 0 -> 255;  true => 255 -> 0
        boolean from0 = false;
        if (r.nextInt(1000) % 2 == 1)
            redir = true;
        if (r.nextInt(1000) % 2 == 0)
            from0 = true;

        setOfColors = genColors(redir, from0, inVar, f, c, d);

        arc1IV.setColorFilter(setOfColors[0]);
        arc5IV.setColorFilter(setOfColors[4]);

        redraw();
        k = 0;
        playerComb = new ArrayList<>();
    }

    private int compColor(int[] color) {
        return Color.rgb(color[0], color[1], color[2]);
    }

    private int[] genColors(boolean redir, boolean from0, int inVar, int f, int c, int d) {
        /*
        color[0] = r;
        color[1] = g;
        color[2] = b;
         */
        int[] color = new int[3];
        color[inVar] = c;
        int[] setOfGenColors = new int[5];
        int k = 0;
        if (redir)
            f = 3 - f - inVar;

        if (from0) {
            color[f] = 0;
            color[3 - f - inVar] = 255;            // т.к. 0+1+2 = 3
        } else {
            color[f] = 255 - 5 * d / 2;
            color[3 - f - inVar] = 5 * d / 2;
        }
        setOfGenColors[0] = compColor(color);
        while (k < 4) {
            if (color[f] + d < 256)
                color[f] += d;
            else {
                color[3 - f - inVar] -= d - 255 + color[f];
                color[f] = 255;
            }
            k++;
            setOfGenColors[k] = compColor(color);
        }
        return setOfGenColors;
    }

    private void check() {
        int delay = 4000;
        if (playerComb.equals(rightComb)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    arcsContourIV.setVisibility(View.GONE);
                }
            }, delay / 10);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    successGif.setVisibility(View.VISIBLE);
                }
            }, delay / 4);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    newGame();
                }
            }, delay);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    arcsContourIV.setVisibility(View.GONE);
                }
            }, delay / 10);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    failGif.setVisibility(View.VISIBLE);
                }
            }, delay / 4);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    k = 0;
                    redraw();
                    playerComb = new ArrayList<>();
                }
            }, delay);
        }
    }

    private void redraw() {
        mix = Arrays.asList(1, 2, 3);
        Collections.shuffle(mix);

        for (int i = 0; i < 3; i++) {
            mixedColors[i] = setOfColors[mix.get(i)];
            colorsIB[i].setColorFilter(mixedColors[i]);
            colorsIB[i].setClickable(true);
            colorsIV[i].clearColorFilter();
            flowersIV[i].clearColorFilter();
            arcsIV[i].clearColorFilter();
        }
        arcsIV[1].setColorFilter(0x99d3edff, PorterDuff.Mode.SRC_ATOP);
        arcsIV[2].setColorFilter(0x99d3edff, PorterDuff.Mode.SRC_ATOP);

        arcsContourIV.setVisibility(View.VISIBLE);

        successGif.setVisibility(View.GONE);
        failGif.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
