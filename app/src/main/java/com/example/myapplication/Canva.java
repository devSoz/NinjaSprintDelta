package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import static android.content.Context.MODE_PRIVATE;

public class customView extends View
{
    private Canvas mCanvas;
    private Float xCanvas, yCanvas, xSal, ySal,yBottom, speedSal, score;
    int width1, cntNinja=0;
    private Float xDeltaSal = Float.valueOf(10), yDeltaSal = Float.valueOf(10), vX, vX2, vMid;
    private Boolean updateView=false;
    private SoundPool soundPool;
    private RectF endRect;
    private Bitmap bitmap, bmpNinja;
    private Integer interval = 25, flagClick = 0, flagUp=1, flagStart=0, velocity=35, secondsPassed=0;
    private Ninja ninjaObj;
    private Integer cnt=0,endSound, flagGameOver = 0, flagSpeedReset=0, timerCount=0, flagEnd=0, flagVolume = 0;
    private int min, sec;
    private Rect rect;
    private Sal salObj, salObj2;
    private Drawable mCustomImage;
    private Paint paintSal, paintNinja, paintLine;


    private class UpdateViewRunnable implements Runnable {
        public void run()
        {


            if(updateView) {
                if(flagStart==1) {
                    //secondsPassed++;
                    if(flagEnd==0) {
                        timerCount++;
                        if (ninjaObj != null)
                            moveSal();
                    }
                    //}

                }
                postDelayed(this, interval);
            }
        }
    }

    private UpdateViewRunnable updateViewRunnable = new UpdateViewRunnable();
    public customView(Context context)
    {
        super(context);

        init(null);
    }

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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateView = true;
        postDelayed(updateViewRunnable, interval);
    }

    @Override
    protected void onDetachedFromWindow() {
        updateView = false;
        super.onDetachedFromWindow();
    }

    private void init(@Nullable AttributeSet set)
    {

    }


    //play different sound based on the action
    public void playSound(int i)
    {
        soundPool.play(endSound, 1, 1, 0, 0, 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mCanvas = canvas;
        mCanvas.drawColor(getResources().getColor(R.color.backgnd));

        if(ninjaObj==null)
        {
            rect = new Rect();
            paintSal = new Paint();
            paintNinja = new Paint();
            paintLine = new Paint();
            setDefault();

            paintLine.setColor(getResources().getColor(R.color.clrGameLine));
            paintLine.setTextSize(70);
            paintLine.setTypeface(Typeface.create("Odyssey", Typeface.NORMAL));
            mCanvas.drawText(String.valueOf("Click any key to start!"), xCanvas/3, yCanvas/3, paintLine);
        }

        if(flagStart==1) {
            if (salObj == null ) {
                salObj = new Sal(vX, vX + vMid, 1);
                salObj2 = new Sal(vX2, vX2 + vMid, 2);
                ninjaObj = new Ninja(xCanvas, yCanvas);
                //   salObj2 = new Sal(xCanvas, yCanvas);
            }
            mCanvas.drawRect(salObj2.rSal, paintSal);

            mCustomImage = getResources().getDrawable(R.drawable.fsal);
            //mCustomImage.setBounds(100, 200, 200, 100);
            //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fsal);

            //  mCustomImage.setBounds(0, 0, 1000,50 );

            Drawable dr = getResources().getDrawable(R.drawable.fsal);

            bitmap = ((BitmapDrawable) dr).getBitmap();
            //  int ix = (int) xCanvas;
            mCustomImage= new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap,Math.round(xCanvas) ,80,true));
            // mCanvas.drawBitmap(bitmap, 0, 0, paintSal);

            bitmap = ((BitmapDrawable) mCustomImage).getBitmap();

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int w = width/10;
            salObj.rSal.round(rect);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, height);
            mCanvas.drawBitmap(bitmap, rect.left, rect.top, paintSal);

            debugCanvas(0);
            ////Ninja
            if(cntNinja>4)
                cntNinja=0;
            cntNinja++;

            //  ninjaObj.bmpNinja = Bitmap.createBitmap(ninjaObj.bmpNinja, width1*cntNinja, 0,(cntNinja*width1)+ width1, height);
            mCanvas.drawBitmap(ninjaObj.bmpNinja, ninjaObj.x, ninjaObj.y, paintSal);



            paintNinja.setStyle(Paint.Style.FILL_AND_STROKE);
            paintNinja.setColor(getResources().getColor(R.color.Ninja));
            if(flagClick==1)
            {
                mCanvas.drawCircle(ninjaObj.x-ninjaObj.radius, ninjaObj.y-ninjaObj.radius, ninjaObj.radius, paintNinja);
            }
            else {
                if(salObj.rSal.contains(ninjaObj.x+ninjaObj.radius, ninjaObj.y)||(salObj2.rSal.contains(ninjaObj.x+ninjaObj.radius, ninjaObj.y)))
                {
                    gameEnd();
                }
                mCanvas.drawCircle(ninjaObj.x, ninjaObj.y, ninjaObj.radius, paintNinja);
            }
            paintLine.setColor(getResources().getColor(R.color.clrGameLine));
            paintLine.setStyle(Paint.Style.STROKE);
            paintLine.setStrokeWidth(5f);
            setTimer();
            mCanvas.drawText(String.format ("%02d", min) + ":" + String.format ("%02d", sec) + "      Score : " +String.format("%.1f", secondsPassed*0.1) , (xCanvas - xCanvas/3.0f), 50, paintLine);
        }

        paintLine.setColor(getResources().getColor(R.color.clrGameLine));
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(20f);
        mCanvas.drawLine(0,yBottom+15, xCanvas,yBottom+15, paintLine);

        //debugCanvas();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        xSal= event.getX();
        ySal = event.getY();
        switch (event.getAction()) {

            case MotionEvent.ACTION_UP:
            {
                //if(flagEnd==0) {
                if (flagStart == 1)
                    flagClick = 1;
                flagStart = 1;
                //}
                break;
            }
        }

        postInvalidate();
        return true;
    }


    private void moveSal()
    {

        if (salObj.rSal.right < 0) {
            //salObj.rSal.left = xCanvas;
            //salObj.rSal.right = salObj.rSal.left + salObj.width;
            salObj.resetSal(salObj2.rSal.left, 1);
        } else {
            salObj.rSal.left -= xDeltaSal;
            salObj.rSal.right -= xDeltaSal;
        }

        if (salObj2.rSal.right < 0) {
            //salObj2.rSal.left = xCanvas;
            //salObj2.rSal.right = salObj.rSal.left + salObj.width;
            salObj2.resetSal(salObj.rSal.left, 2);
        } else {
            salObj2.rSal.left -= xDeltaSal;
            salObj2.rSal.right -= xDeltaSal;
        }

        invalidate();
    }

    public void setTimer()
    {
        paintLine.setColor(Color.WHITE);
        paintLine.setTypeface(Typeface.create("Odyssey", Typeface.NORMAL));
        if(timerCount%40==0)
            secondsPassed++;
        min = (int) (secondsPassed / 60);
        sec = (int) ((secondsPassed) % 60);
        paintLine.setTextSize(45);
        //mCanvas.drawText(String.format ("%02d", min) + ":" + String.format ("%02d", sec) + "      Score : " + secondsPassed , (xCanvas - xCanvas/2), 50, paintLine);
    }


    private void drawPic(Rect rect)
    {
        // mCustomImage = getResources().getDrawable(R.drawable.mine);
        //  mCustomImage.setBounds(rect);
        // mCustomImage.draw(mcanvas);
    }

    private class Ninja
    {
        Float x;
        Float y;
        Integer radius;
        Bitmap bmpNinja;

        public Ninja(Float xCanvas, Float yCanvas)
        {
            this.radius = 50;
            this.x = xCanvas/10;
            this.y = yBottom-this.radius;

            Drawable dr = getResources().getDrawable(R.drawable.run);

            this.bmpNinja = ((BitmapDrawable) dr).getBitmap();

            Drawable dr1= new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(this.bmpNinja,Math.round(3*(xCanvas/5)) , (int) (150),true));
            this.bmpNinja = ((BitmapDrawable) dr1).getBitmap();

            int width = this.bmpNinja.getWidth();
            int height = this.bmpNinja.getHeight();
            int w = width/10;
            width1=w; //this.bmpNinja.getWidth()/10;
            int x1=Math.round(this.x);
            int y1= Math.round(this.y);

            this.bmpNinja = Bitmap.createBitmap(this.bmpNinja, 0, 0, w, height);
        }
    }

    private class Sal
    {
        Integer width;
        Integer height, speed;
        RectF rSal;
        Float x1;
        Float x2;

        public Sal(Float x1, Float x2, Integer t)
        {
            this.height = 200;
            this.width = 50;
            this.x1 = x1;
            this.x2 = x2;
            rSal = new RectF();

            /*
            rSal.top = yBottom-this.height;
            rSal.bottom = rSal.top + this.height;
            rSal.left = xCanvas;
            rSal.right = rSal.left + this.width;*/
            Random random = new Random();
            if(t==1) {
                this.x1 = xCanvas;
                this.x2 = xCanvas+500;
            }
            if(t==2) {
                this.x1 = xCanvas+1000;
                this.x2 = xCanvas+1500;
            }
            rSal.left = random.nextInt((int)(this.x2 - this.x1))+this.x1;
            //this.speed = random.nextInt((int)(25 - 15))+15;
            this.width = random.nextInt (130 - 50)+50;
            this.height = random.nextInt (200 - 100)+100;
            Integer temp = random.nextInt(2-0)+0;
            if(temp==0) {
                paintSal.setColor(getResources().getColor(R.color.clrSal1));
                paintSal.setAntiAlias(true);
            }
            else
            {
                paintSal.setColor(getResources().getColor(R.color.clrSal2));
                paintSal.setAntiAlias(true);
            }
            Log.d("debug random", String.valueOf(rSal.left)+ ", "+this.x1+", "+ this.x2 +"   "+ t);
            rSal.right = rSal.left + this.width;
            rSal.top = yBottom-this.height;
            rSal.bottom = rSal.top + this.height;


            mCanvas.drawRect(rSal, paintSal);
        }

        public void resetSal(Float xtemp, Integer t)
        {
            Random random = new Random();
            this.x1 = xCanvas;
            this.x2 = (Float.parseFloat("1.8")*xCanvas);
            //   if(xtemp-this.rSal.left<1000)
            Float xy=xCanvas+(xtemp *Float.parseFloat( "0.5"));
            this.width = random.nextInt (130 - 50)+50;
            this.height = random.nextInt (200 - 100)+100;
            if(xy+this.width>(x2))
                xy=x2-this.width;
            this.x1=xy;
            rSal.left = random.nextInt((int)(this.x2 - this.x1))+this.x1;

            Integer temp = random.nextInt(3-0)+0;
            if(temp==2) {
                paintSal.setColor(getResources().getColor(R.color.clrSal1));
                paintSal.setAntiAlias(true);
            }
            else if(temp==1)
            {
                paintSal.setColor(getResources().getColor(R.color.clrSal2));
                paintSal.setAntiAlias(true);
            }
            else
            {
                paintSal.setColor(getResources().getColor(R.color.clrSal3));
                paintSal.setAntiAlias(true);
            }
            Log.d("debug random", String.valueOf(rSal.left)+ ", "+this.x1+", "+ this.x2 +"   "+ xtemp + "   t="+t);
            rSal.right = rSal.left + this.width;
            rSal.top = yBottom-this.height;
            rSal.bottom = rSal.top + this.height;
        }
    }

    public void setDefault()
    {
        xCanvas = Float.valueOf(mCanvas.getWidth());
        yCanvas = Float.valueOf(mCanvas.getHeight());
        vX = xCanvas+600;//6*(xCanvas/5);
        vX2 = xCanvas+1800;//8*(xCanvas/5);//7*(xCanvas/4);
        vMid = Float.valueOf(600); //Float.valueOf(xCanvas/5);
        yBottom = yCanvas-(yCanvas/4);
        mCanvas.drawColor(getResources().getColor(R.color.backgnd));
        flagVolume = ((Activity)getContext()).getIntent().getIntExtra("volume", 0);

        Random random = new Random();



    }


    public void gameEnd()
    {
        if(flagVolume==1)
        {   playSound(1);   }
        flagEnd=1;

        debugCanvas(1);
        Intent intentCanva = new Intent((Activity) getContext(), MainActivity.class);
        intentCanva.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ((Activity) getContext()).finish();
        getContext().startActivity(intentCanva);
    }

    public void debugCanvas(int x)
    {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);


        mCanvas.drawText(String.valueOf(ninjaObj.x), 200, 200, paint);
        mCanvas.drawText(String.valueOf(ninjaObj.y), 100, 300, paint);

        if(x==1)
            Log.d("debugcanvas game over", String.valueOf(ninjaObj.x) + " ," +String.valueOf(ninjaObj.y));
        else
            Log.d("debugcanvas", String.valueOf(ninjaObj.x) + " ," +String.valueOf(ninjaObj.y)+" ," +String.valueOf(ninjaObj.radius));

        //mcanvas.drawText(String.valueOf(xRunStart), 100, 100, paint);
        //mcanvas.drawText(String.valueOf(yRunStart), 200, 100, paint);
        //mcanvas.drawText(String.valueOf(NinjaSlope), 200, 200, paint);
        //mcanvas.drawText(String.valueOf(xDelta), 100, 400, paint);
        //mcanvas.drawText(String.valueOf(yDelta), 200, 400, paint);
    }


}
