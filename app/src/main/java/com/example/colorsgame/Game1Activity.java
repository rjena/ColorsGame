package com.example.colorsgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Game1Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game1);

        TextView color1TV = findViewById(R.id.color1TV);
        TextView color2TV = findViewById(R.id.color2TV);
        TextView color3TV = findViewById(R.id.color3TV);

        ImageView color1IV = findViewById(R.id.color1IV);
        ImageView color2IV = findViewById(R.id.color2IV);
        ImageView color3IV = findViewById(R.id.color3IV);

        ImageButton color1IB = findViewById(R.id.color1IB);
        ImageButton color2IB = findViewById(R.id.color2IB);
        ImageButton color3IB = findViewById(R.id.color3IB);
        ImageButton color4IB = findViewById(R.id.color4IB);
        ImageButton color5IB = findViewById(R.id.color5IB);

        ImageButton info = findViewById(R.id.infoIB);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Info1Activity.class);
                startActivity(intent);
            }
        });
    }
}
