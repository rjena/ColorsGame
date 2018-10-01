package com.example.colorsgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String mode = intent.getStringExtra("activity");

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
            case "game7":
                tv.setText(R.string.game7_info);
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
