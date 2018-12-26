package com.example.colorsgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class InfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_info);

        ImageButton back = findViewById(R.id.backIB);
        TextView tv = findViewById(R.id.textView);
        final GifImageView rgbit = findViewById(R.id.rgbit);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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

        Intent intent = getIntent();
        String mode = intent.getStringExtra("activity");

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        tv.setTextSize(size.x / 48 > size.y / 27 ? size.y / 27 : size.x / 48);

        switch (mode) {
            case "game1":
                tv.setText(R.string.game1_info);
                break;
            case "game2":
                tv.setText(R.string.game2_info);
                break;
            case "game3":
                tv.setText(R.string.game3_info);
                break;
            case "game4":
                tv.setText(R.string.game4_info);
                break;
            case "game5":
                tv.setText(R.string.game5_info);
                break;
            case "game6":
                tv.setText(R.string.game6_info);
                break;
            default:
                tv.setText(R.string.description);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
