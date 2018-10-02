package com.example.colorsgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

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
        ImageButton info = findViewById(R.id.infoIB);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putExtra("activity","main");
                startActivity(intent);
            }
        });

        ArrayList<Integer> gamesImages = new ArrayList<>();
        gamesImages.add(R.drawable.images);
        gamesImages.add(R.drawable.images);
        gamesImages.add(R.drawable.images);
        gamesImages.add(R.drawable.images);
        gamesImages.add(R.drawable.images);
        gamesImages.add(R.drawable.images);

        ImageButton[] gamesIB = new ImageButton[7];
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

            gamesIB[i] = ib;
        }

        final Class[] classes = new Class[6];
        classes[0] = Game1Activity.class;
        /*classes.add(Game2Activity.class);
        classes.add(Game3Activity.class);
        classes.add(Game4Activity.class);
        classes.add(Game5Activity.class);
        classes.add(Game6Activity.class);

        for (ImageButton ib: gamesIB) {
            ib.setOnClickListener(new View.OnClickListener() {*/
            gamesIB[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), classes[0]);
                    startActivity(intent);
                }
            });
        //}
    }
}
