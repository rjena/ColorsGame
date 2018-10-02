package com.example.colorsgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class Game1Activity extends AppCompatActivity {
    ImageButton[] colorsIB;
    TextView color1TV;
    TextView color2TV;
    TextView color3TV;
    ImageView color1IV;
    ImageView color2IV;
    ImageView color3IV;
    ArrayList<ResultColor> resColors;
    boolean firstClick;
    int n = 0;
    String[] mainColorsTitles;
    int[] mainColors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game1);

        ImageButton info = findViewById(R.id.infoIB);

        color1TV = findViewById(R.id.color1TV);
        color2TV = findViewById(R.id.color2TV);
        color3TV = findViewById(R.id.color3TV);

        color1IV = findViewById(R.id.color1IV);
        color2IV = findViewById(R.id.color2IV);
        color3IV = findViewById(R.id.color3IV);

        colorsIB = new ImageButton[5];
        colorsIB[0] = findViewById(R.id.color1IB);
        colorsIB[1] = findViewById(R.id.color2IB);
        colorsIB[2] = findViewById(R.id.color3IB);
        colorsIB[3] = findViewById(R.id.color4IB);
        colorsIB[4] = findViewById(R.id.color5IB);

        mainColorsTitles = new String[5];
        mainColors = new int[5];

        mainColorsTitles[0] = "Белый";
        mainColorsTitles[1] = "Красный";
        mainColorsTitles[2] = "Жёлтый";
        mainColorsTitles[3] = "Синий";
        mainColorsTitles[4] = "Чёрный";

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

        color3IV.setColorFilter(resColors.get(2).color);
        color3TV.setText(resColors.get(2).title);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putExtra("activity","game1");
                startActivity(intent);
            }
        });

        Collections.shuffle(resColors);

        while (n < resColors.size())
            round();

    }

    public void round() {
        firstClick = true;
        color3IV.setColorFilter(resColors.get(n).color);
        color3TV.setText(resColors.get(n).title);
        final int[] clickedColors = new int[2];
        for (int i = 0; i < 5; i++) {
            final int ii = i;
            colorsIB[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (firstClick) {
                        color1IV.setColorFilter(mainColors[ii]);
                        color1TV.setText(mainColorsTitles[ii]);
                        firstClick = false;
                        clickedColors[0] = ii;
                    } else {
                        color2IV.setColorFilter(mainColors[ii]);
                        color2TV.setText(mainColorsTitles[ii]);
                        firstClick = true;
                        clickedColors[1] = ii;
                        checkColors(clickedColors);
                    }
                }
            });
        }
    }
    public void checkColors(int[] clColors) {
        color1IV.clearColorFilter();
        color1TV.setText("?");
        color2IV.clearColorFilter();
        color2TV.setText("?");
        if ((resColors.get(n).color1 == clColors[0] && resColors.get(n).color2 == clColors[1])
                || (resColors.get(n).color1 == clColors[1] && resColors.get(n).color2 == clColors[0])) {
            n++;
        }
    }
}

class ResultColor {
    String title;
    int color1;
    int color2;
    int color;
}