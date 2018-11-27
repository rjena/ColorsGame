package com.example.colorsgame;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Game4Activity extends AppCompatActivity {
    int curColor;
    Paint mPaint = new Paint();
    ImageView[] pickedIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game4);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final int[] colors = {Color.rgb(240, 0, 0),
                Color.rgb(255, 140, 0),
                Color.rgb(255, 240, 0),
                Color.rgb(0, 153, 0),
                Color.rgb(135, 206, 250),
                Color.rgb(0, 0, 255),
                Color.rgb(90, 0, 176),
                Color.rgb(255, 255, 255),
                Color.rgb(255, 155, 170),
                Color.rgb(0, 0, 0),
                Color.rgb(140, 70, 20)};
        ImageButton[] colorsIB = new ImageButton[11];
        colorsIB[0] = findViewById(R.id.color1IB);
        colorsIB[1] = findViewById(R.id.color2IB);
        colorsIB[2] = findViewById(R.id.color3IB);
        colorsIB[3] = findViewById(R.id.color4IB);
        colorsIB[4] = findViewById(R.id.color5IB);
        colorsIB[5] = findViewById(R.id.color6IB);
        colorsIB[6] = findViewById(R.id.color7IB);
        colorsIB[7] = findViewById(R.id.color8IB);
        colorsIB[8] = findViewById(R.id.color9IB);
        colorsIB[9] = findViewById(R.id.color10IB);
        colorsIB[10] = findViewById(R.id.color11IB);
        pickedIV = new ImageView[colorsIB.length];
        pickedIV[0] = findViewById(R.id.picked1IV);
        pickedIV[1] = findViewById(R.id.picked2IV);
        pickedIV[2] = findViewById(R.id.picked3IV);
        pickedIV[3] = findViewById(R.id.picked4IV);
        pickedIV[4] = findViewById(R.id.picked5IV);
        pickedIV[5] = findViewById(R.id.picked6IV);
        pickedIV[6] = findViewById(R.id.picked7IV);
        pickedIV[7] = findViewById(R.id.picked8IV);
        pickedIV[8] = findViewById(R.id.picked9IV);
        pickedIV[9] = findViewById(R.id.picked10IV);
        pickedIV[10] = findViewById(R.id.picked11IV);
        for (int i = 0; i < colorsIB.length; i++) {
            final int ii = i;
            colorsIB[i].setColorFilter(colors[i]);
            pickedIV[i].setColorFilter(colors[i]);
            colorsIB[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    curColor = colors[ii];
                    removePicks();
                    pickedIV[ii].setVisibility(View.VISIBLE);
                }
            });
        }
        removePicks();
        curColor = colors[0];
        pickedIV[0].setVisibility(View.VISIBLE);

        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);

        final ImageView board = findViewById(R.id.boardIV);
        board.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                final ImageView img = (ImageView) v;
                final Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();

                int x = (int) (event.getX() * bitmap.getWidth() / img.getWidth());
                int y = (int) (event.getY() * bitmap.getHeight() / img.getHeight());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchStart(x, y);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touchMove(x, y, bitmap, curColor);
                        break;
                    case MotionEvent.ACTION_UP:
                        mPath.reset();
                        break;
                }
                return true;
            }
        });
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 5;
    private Path mPath = new Path();

    private void touchStart(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y, Bitmap bitmap, int color) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            closeOptionsMenu();
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
        mPath.lineTo(mX, mY);

        Bitmap bitmapResult = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(bitmapResult);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(10);
        mCanvas.drawBitmap(bitmap, 0, 0, mPaint);
        mCanvas.drawPath(mPath, mPaint);

        ImageView firstImageView = findViewById(R.id.boardIV);
        firstImageView.setImageBitmap(bitmapResult);
    }

    private void removePicks() {
        for (ImageView iv : pickedIV)
            iv.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}