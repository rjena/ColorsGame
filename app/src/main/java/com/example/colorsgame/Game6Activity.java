package com.example.colorsgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ankushgrover.hourglass.Hourglass;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game6Activity extends AppCompatActivity {
    TextView tvColor;
    TextView tvUpPoint;
    TextView tvDownPoint;
    ImageButton ibColor1;
    ImageButton ibColor2;
    ImageButton ibColor3;
    ImageButton ibColor4;
    ImageButton ibColor5;
    static ImageButton[] ibColors;
    ImageButton info;
    ProgressBar time;

    int[] colors = {Color.parseColor("#ffffff"),
            Color.parseColor("#009900"),
            Color.parseColor("#f00000"),
            Color.parseColor("#fff000"),
            Color.parseColor("#0000ff")};
    int[] darkColors = {Color.parseColor("#f0f0f0"),
            Color.parseColor("#008000"),
            Color.parseColor("#d00000"),
            Color.parseColor("#ffd000"),
            Color.parseColor("#0000d0")};

    int upPoint, downPoint;
    int level;
    int ansColorNum;
    List<Integer> colorsNum;
    String[] colorsTitles = {"Белый", "Зелёный", "Красный", "Желтый", "Синий"};
    static Hourglass timer;
    int duration;
    int curDur;
    MediaPlayer clickMP;
    static SharedPreferences rec;
    final String RECORD = "record";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game6);

        rec = getPreferences(MODE_PRIVATE);
        info = findViewById(R.id.info);
        time = findViewById(R.id.timer);
        tvColor = findViewById(R.id.r_color);
        tvUpPoint = findViewById(R.id.up_point);
        tvDownPoint = findViewById(R.id.down_point);
        ibColor1 = findViewById(R.id.bt_color1);
        ibColor2 = findViewById(R.id.bt_color2);
        ibColor3 = findViewById(R.id.bt_color3);
        ibColor4 = findViewById(R.id.bt_color4);
        ibColor5 = findViewById(R.id.bt_color5);
        ibColors = new ImageButton[5];
        ibColors[0] = ibColor1;
        ibColors[1] = ibColor2;
        ibColors[2] = ibColor3;
        ibColors[3] = ibColor4;
        ibColors[4] = ibColor5;
        clickMP = MediaPlayer.create(this, R.raw.balloon_pop);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) info.getLayoutParams();
        params.height = size.y / 12;
        params.width = params.height;
        info.setLayoutParams(params);

        params = (FrameLayout.LayoutParams) time.getLayoutParams();
        params.width = size.x / 3;
        time.setLayoutParams(params);

        tvUpPoint.setTextSize(size.x / 45 > size.y / 25 ? size.y / 20 : size.x / 35);
        tvDownPoint.setTextSize(size.x / 45 > size.y / 25 ? size.y / 20 : size.x / 35);
        tvColor.setTextSize(size.x / 45 > size.y / 25 ? size.y / 10 : size.x / 20);

        level = 1;
        duration = 5000;
        curDur = duration;
        time.setMax(duration);
        time.incrementProgressBy(30);
        time.setProgress(duration);

        setTimer(curDur);
        timer.startTimer();
        upPoint = 0;
        downPoint = 0;
        tvUpPoint.setText(String.valueOf(upPoint));
        tvDownPoint.setText(String.valueOf(downPoint));

        newRound();

        info.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (info.isClickable()) {
                            info.setColorFilter(0x65000000, PorterDuff.Mode.SRC_ATOP);
                            info.invalidate();
                            break;
                        }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        info.clearColorFilter();
                        info.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.pauseTimer();
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putExtra("activity", "game6");
                startActivity(intent);
            }
        });
        for (int i = 0; i < 5; i++) {
            final int ii = i;
            ibColors[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            ibColors[ii].setColorFilter(darkColors[colorsNum.get(ii) - 1]);
                            ibColors[ii].invalidate();
                            break;
                        }
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL: {
                            ibColors[ii].setColorFilter(colors[colorsNum.get(ii) - 1]);
                            ibColors[ii].invalidate();
                            break;
                        }
                    }
                    return false;
                }
            });
            ibColors[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickMP.start();
                    if (colorsNum.get(ii) == ansColorNum) {
                        upPoint++;
                        tvUpPoint.setText(String.valueOf(upPoint));
                        timer.pauseTimer();
                        timer = null;
                        if (curDur + 300 *(level+2)/level > duration)
                            setTimer(duration);
                        else
                            setTimer(curDur + 300 *(level+2)/level);
                        timer.startTimer();
                    } else {
                        downPoint++;
                        tvDownPoint.setText(String.valueOf(downPoint));
                        if (curDur - 300 * level/(level+2) <= 0)
                            timer.stopTimer();
                        else {
                            timer.pauseTimer();
                            timer = null;
                            setTimer(curDur - 300 * level/(level+2));
                            timer.startTimer();
                        }
                    }
                    level += (upPoint-downPoint) / 15;
                    newRound();
                }
            });
        }
    }

    private void newRound() {
        setBtColors();
        setColor();
    }

    private void setTimer(int dur) {
        timer = new Hourglass(dur, 30) {
            @Override
            public void onTimerTick(long timeRemaining) {
                curDur = (int) timeRemaining;
                time.setProgress(curDur);
            }

            @Override
            public void onTimerFinish() {
                time.setProgress(0);
                for (ImageButton color : ibColors)
                    color.setClickable(false);
                clickMP.reset();
                clickMP.release();
                clickMP = MediaPlayer.create(getApplicationContext(), R.raw.sad_whisle);
                clickMP.start();
                info.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinishing()) {
                            Intent intent = new Intent(getApplicationContext(), Game6EndActivity.class);
                            intent.putExtra("up", upPoint);
                            intent.putExtra("down", downPoint);
                            intent.putExtra("record", getRec());
                            startActivity(intent);
                            if (upPoint - downPoint > getRec())
                                saveRec(upPoint - downPoint);
                            finish();
                        }
                    }
                }, 1000);
            }
        };
    }

    private void setBtColors() {
        colorsNum = Arrays.asList(1, 2, 3, 4, 5);
        Collections.shuffle(colorsNum);
        for (int i = 0; i < 5; i++)
            ibColors[i].setColorFilter(colors[colorsNum.get(i) - 1]);
    }

    private void setColor() {
        ansColorNum = new Random().nextInt(5) + 1;
        tvColor.setText(colorsTitles[new Random().nextInt(5)]);
        tvColor.setTextColor(colors[ansColorNum - 1]);
    }

    public static void resume() {
        for (ImageButton color : ibColors)
            color.setClickable(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (ImageButton color : ibColors)
                    color.setClickable(true);
                timer.resumeTimer();
            }
        }, 70);
    }

    @Override
    public void onBackPressed() {
        timer.pauseTimer();
        clickMP.stop();
        finish();
    }

    public static void resetRec() {
        SharedPreferences.Editor ed = rec.edit();
        ed.clear();
        ed.apply();
    }

    public int getRec() {
        String record = rec.getString(RECORD, "0");
        return Integer.valueOf(record);
    }

    void saveRec(int res) {
        SharedPreferences.Editor ed = rec.edit();
        ed.putString(RECORD, String.valueOf(res));
        ed.apply();
    }
}