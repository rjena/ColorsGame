package com.example.colorsgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        final FrameLayout main = findViewById(R.id.main);

        ArrayList<ImageButton> gamesIB = new ArrayList<>();
        int numViews = 7;
        for (int i = 0; i < numViews; i++) {
            ImageButton ib = new ImageButton(this);
            ib.setImageResource(R.drawable.images);
            ib.setForegroundGravity(Gravity.CENTER);
            ib.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ib.setBackground(null);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(200, 200);
            lp.gravity = Gravity.CENTER;
            ib.setLayoutParams(lp);

            float angleDeg = i * 360.0f / numViews - 90.0f;
            float angleRad = (float) (angleDeg * Math.PI / 180.0f);
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int height = size.y;
            height /= 2.6;
            ib.setTranslationX(height * (float) Math.cos(angleRad));
            ib.setTranslationY(height * (float) Math.sin(angleRad));
            //ib.setRotation(angleDeg + 90.0f);
            main.addView(ib);

            gamesIB.add(ib);
        }

        ArrayList<Class> classes = new ArrayList<>();
        classes.add(Game1Activity.class);
        /*classes.add(Game1Activity.class);
        classes.add(Game1Activity.class);
        classes.add(Game1Activity.class);
        classes.add(Game1Activity.class);
        classes.add(Game1Activity.class);*/

        //for (ImageButton ib: gamesIB) {
            //ib.setOnClickListener(new View.OnClickListener() {
            gamesIB.get(0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Game1Activity.class);
                    startActivity(intent);
                }
            });
        //}

    }
}
