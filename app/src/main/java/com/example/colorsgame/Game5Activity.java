package com.example.colorsgame;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game5Activity extends AppCompatActivity {
    ImageButton infoIB, resetIB;
    Dialog repeatDialog, resetDialog;

    ImageButton keysIB[] = new ImageButton[7];
    Integer level;
    List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6);
    ArrayList<Integer> user_numbers;
    final int[] uColors = {Color.rgb(240, 0, 0),
            Color.rgb(255, 140, 0),
            Color.rgb(255, 240, 0),
            Color.rgb(0, 153, 0),
            Color.rgb(135, 206, 250),
            Color.rgb(0, 0, 255),
            Color.rgb(90, 0, 176)};
    final int[] lightColors = {Color.rgb(230, 100, 100),
            Color.rgb(240, 200, 140),
            Color.rgb(255, 250, 190),
            Color.rgb(0, 230, 0),
            Color.rgb(210, 230, 255),
            Color.rgb(0, 130, 255),
            Color.rgb(160, 90, 225)};
    int[] sounds = {R.raw.key1_do,R.raw.key2_re,R.raw.key3_mi,
            R.raw.key4_fa,R.raw.key5_sol,R.raw.key6_la,R.raw.key7_si};
    Toast toast;

    MediaPlayer keyMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game5);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        keysIB[0] = findViewById(R.id.redIB);
        keysIB[1] = findViewById(R.id.orangeIB);
        keysIB[2] = findViewById(R.id.yellowIB);
        keysIB[3] = findViewById(R.id.greenIB);
        keysIB[4] = findViewById(R.id.cyanIB);
        keysIB[5] = findViewById(R.id.blueIB);
        keysIB[6] = findViewById(R.id.violetIB);

        for (int i = 0; i < 7; i++) {
            keysIB[i].setColorFilter(uColors[i]);
            final int ii = i;
            keysIB[ii].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (toast != null)
                        toast.cancel();
                    clickColor(ii);
                    user_numbers.add(ii);
                    if (user_numbers.size() == level)
                        check();
                }
            });
        }

        level = 1;
        keysUnEnable();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (toast != null)
                    toast.cancel();
                toast = Toast.makeText(getApplicationContext(),
                        "Уровень " + level, Toast.LENGTH_SHORT);
                toast.show();
            }
        }, 150);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                newGame();
            }
        }, 500);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        infoIB = findViewById(R.id.infoIB);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) infoIB.getLayoutParams();
        params.height = size.y / 12;
        params.width = params.height;
        infoIB.setLayoutParams(params);

        infoIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putExtra("activity", "game5");
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

        resetDialog = new Dialog(this);
        resetDialog.setContentView(R.layout.activity_dialog);
        Button resetB = resetDialog.findViewById(R.id.bt1);
        Button noResetB = resetDialog.findViewById(R.id.bt2);
        TextView resetTV = resetDialog.findViewById(R.id.tv);
        resetTV.setTextSize(size.x / 50 > size.y / 28 ? size.y / 28 : size.x / 50);
        resetTV.setText("Начать игру заново?");
        resetB.setTextSize(size.x / 75 > size.y / 42 ? size.y / 42 : size.x / 75);
        resetB.setText("Да");
        noResetB.setTextSize(size.x / 75 > size.y / 42 ? size.y / 42 : size.x / 75);
        noResetB.setText("Нет");

        resetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetDialog.dismiss();
                if (toast != null) toast.cancel();
                toast = Toast.makeText(getApplicationContext(),
                        "Хорошо, давай заново. Уровень 1.", Toast.LENGTH_SHORT);
                toast.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        level = 1;
                        newGame();
                    }
                }, 500);
            }
        });
        noResetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetDialog.dismiss();
                keysEnable();
            }
        });

        repeatDialog = new Dialog(this);
        repeatDialog.setContentView(R.layout.activity_dialog);
        Button repeatB = repeatDialog.findViewById(R.id.bt1);
        Button noRepeatB = repeatDialog.findViewById(R.id.bt2);
        TextView repeatTV = repeatDialog.findViewById(R.id.tv);
        repeatTV.setTextSize(size.x / 50 > size.y / 28 ? size.y / 28 : size.x / 50);
        repeatTV.setText("Молодец! Все уровни пройдены! Сыграем ещё?");
        repeatB.setTextSize(size.x / 75 > size.y / 42 ? size.y / 42 : size.x / 75);
        repeatB.setText("Да");
        noRepeatB.setTextSize(size.x / 75 > size.y / 42 ? size.y / 42 : size.x / 75);
        noRepeatB.setText("Нет");

        repeatB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatDialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        level = 1;
                        if (toast != null)
                            toast.cancel();
                        toast = Toast.makeText(getApplicationContext(),
                                "Уровень " + level, Toast.LENGTH_SHORT);
                        toast.show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                newGame();
                            }
                        }, 500);
                    }
                }, 500);
            }
        });
        noRepeatB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatDialog.dismiss();
                finish();
            }
        });

        resetIB = findViewById(R.id.resetIB);
        params = (FrameLayout.LayoutParams) resetIB.getLayoutParams();
        params.height = size.y / 12;
        params.width = params.height;
        resetIB.setLayoutParams(params);

        resetIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetIB.setEnabled(false);
                keysUnEnable();
                resetDialog.show();
            }
        });
        resetIB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        resetIB.setColorFilter(0x65000000, PorterDuff.Mode.SRC_ATOP);
                        resetIB.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        resetIB.clearColorFilter();
                        resetIB.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (toast != null)
            toast.cancel();
        toast = null;
        level = 0;
        super.onBackPressed();
    }

    private void newGame() {
        if (level < 8) {
            user_numbers = new ArrayList<>();
            Collections.shuffle(numbers);
            playit();
        }
    }

    public void playit() {
        resetIB.setEnabled(false);
        keysUnEnable();
        for (int i = 0; i < level; i++) {
            final int ii = i;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    clickColor(numbers.get(ii));
                }
            }, (ii + 1) * 1000);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                keysEnable();
                if (toast != null)
                    toast.cancel();
                if (level != 0) {
                    toast = Toast.makeText(getApplicationContext(),
                            "Твоя очередь", Toast.LENGTH_SHORT);
                    toast.show();
                }
                resetIB.setEnabled(true);
            }
        }, level * 1000 + 500);
    }

    public void check() {
        keysUnEnable();
        if (user_numbers.equals(numbers))
            repeatDialog.show();
        else if (user_numbers.equals(numbers.subList(0, level))) {
            if (toast != null)
                toast.cancel();
            toast = Toast.makeText(getApplicationContext(),
                    "Правильно!", Toast.LENGTH_SHORT);
            toast.show();
            level++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (toast != null)
                        toast.cancel();
                    toast = Toast.makeText(getApplicationContext(),
                            "Уровень " + level, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }, 800);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    newGame();
                }
            }, 1500);
        } else {
            if (toast != null)
                toast.cancel();
            toast = Toast.makeText(getApplicationContext(),
                    "Неправильно!", Toast.LENGTH_SHORT);
            toast.show();
            user_numbers = new ArrayList<>();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    playit();
                }
            }, 1000);
        }
    }

    public void clickColor(final int m) {
        if (keyMP != null) {
            keyMP.reset();
            keyMP.release();
        }
        keyMP = MediaPlayer.create(getApplicationContext(), sounds[m]);
        keyMP.start();
        resetIB.setEnabled(false);
        keysIB[m].setColorFilter(lightColors[m]);  // более светлый цвет
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                keysIB[m].setColorFilter(uColors[m]);  // первоначальный цвет
            }
        }, 300);
    }

    private void keysEnable() {
        for (int i = 0; i < 7; i++)
            keysIB[i].setEnabled(true);
    }

    private void keysUnEnable() {
        for (int i = 0; i < 7; i++)
            keysIB[i].setEnabled(false);
    }
}