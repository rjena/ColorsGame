package com.example.colorsgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        final FrameLayout main = findViewById(R.id.main);
        final ImageButton infoIB = findViewById(R.id.infoIB);
        final GifImageView rgbit = findViewById(R.id.rgbit);

        infoIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putExtra("activity","main");
                startActivity(intent);
            }
        });
        infoIB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        infoIB.setColorFilter(0x40000000, PorterDuff.Mode.SRC_ATOP);
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

        rgbit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        rgbit.setImageResource(R.drawable.rgbit_contact);
                        rgbit.invalidate();
                        return true;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        rgbit.setImageResource(R.drawable.rgbit);
                        rgbit.invalidate();
                        break;
                    }
                }
                return false;
            }
        });

        ArrayList<Integer> gamesImages = new ArrayList<>();
        gamesImages.add(R.drawable.button1);
        gamesImages.add(R.drawable.button2);
        gamesImages.add(R.drawable.button3);
        gamesImages.add(R.drawable.button4);
        gamesImages.add(R.drawable.button5);
        gamesImages.add(R.drawable.button6);

        final ImageButton[] gamesIB = new ImageButton[6];
        int numViews = 6;
        for (int i = 0; i < numViews; i++) {
            ImageButton ib = new ImageButton(this);
            ib.setImageResource(gamesImages.get(i));
            ib.setForegroundGravity(Gravity.CENTER);
            ib.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ib.setBackground(null);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(200, 200);
            lp.gravity = Gravity.CENTER;
            ib.setLayoutParams(lp);

            float angleDeg = i * 360.0f / numViews - 320.0f;
            float angleRad = (float) (angleDeg * Math.PI / 250.0f);
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int height = size.y;
            height /= 2.7;
            ib.setTranslationX(height * (float) Math.cos(angleRad));
            ib.setTranslationY(height * (float) Math.sin(angleRad) + (i-i%2)*10);
            //ib.setRotation(angleDeg + 90.0f);
            main.addView(ib);

            gamesIB[i] = ib;
        }

        final Class[] classes = new Class[6];
        classes[0] = Game1Activity.class;
        classes[1] = Game2Activity.class;
        classes[2] = Game3Activity.class;
        classes[3] = Game4Activity.class;
        classes[4] = Game5Activity.class;
        classes[5] = Game6Activity.class;

        for (int i=0; i< gamesIB.length; i++) {
            final int ii = i;
            gamesIB[ii].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), classes[ii]);
                    startActivity(intent);
                }
            });
            gamesIB[ii].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            gamesIB[ii].setColorFilter(0x40000000, PorterDuff.Mode.SRC_ATOP);
                            gamesIB[ii].invalidate();
                            break;
                        }
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL: {
                            gamesIB[ii].clearColorFilter();
                            gamesIB[ii].invalidate();
                            break;
                        }
                    }
                    return false;
                }
            });
        }
    }
}
