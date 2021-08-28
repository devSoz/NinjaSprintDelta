package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class customView extends View {

    private Float xCanvas, yCanvas;

    private Canvas mCanvas;
    public customView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public customView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public customView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(@Nullable AttributeSet set)
    {

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        mCanvas = canvas;
        setDefault();
    }

    public void setDefault()
    {
        xCanvas = Float.valueOf(mCanvas.getWidth());
        yCanvas = Float.valueOf(mCanvas.getHeight());

        mCanvas.drawColor(getResources().getColor(R.color.backgnd));

        Random random = new Random();
    }

}
