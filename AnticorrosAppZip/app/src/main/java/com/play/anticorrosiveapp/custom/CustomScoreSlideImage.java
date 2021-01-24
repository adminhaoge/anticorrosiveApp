package com.play.anticorrosiveapp.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.play.anticorrosiveapp.R;

public class CustomScoreSlideImage extends View {


    private int totalWidth;
    private int totalHeight;

    public CustomScoreSlideImage(Context context) {
        this(context,null);
    }

    public CustomScoreSlideImage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomScoreSlideImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private Bitmap NormalImage;
    private Bitmap FcousImage;
    private int totalNumber = 5;
    private int simpleWidth;
    private int countBigger = 0;
    private void initView() {
        FcousImage = BitmapFactory.decodeResource(getResources(), R.drawable.bigger_grade_fcous);
        NormalImage = BitmapFactory.decodeResource(getResources(), R.drawable.bigger_grade_normal);
        simpleWidth = NormalImage.getWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        totalWidth = simpleWidth * totalNumber + getPaddingLeft() + getPaddingRight();
        totalHeight = NormalImage.getHeight() + getPaddingTop() + getPaddingBottom();
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY){
            int size = MeasureSpec.getSize(widthMeasureSpec);
            if (totalWidth > size){
                throw  new IllegalMonitorStateException("小于图片范围");
            }
            totalWidth = size;
            setMeasuredDimension(totalWidth, totalHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < totalNumber; i++) {
            if (i < countBigger){
                canvas.drawBitmap(FcousImage,i * simpleWidth , 0 , null);
            }else {
                canvas.drawBitmap(NormalImage,i * simpleWidth , 0 ,null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    float moveX = event.getX();
                    float moveY = event.getY();
                    if (moveX < totalWidth && moveY <totalHeight){
                        if (moveX <= 0){
                            countBigger = 0;
                        }else{
                            countBigger = (int) (moveX / simpleWidth +1);
                            Log.e("TAG", String.valueOf(countBigger)+"simpleWidth:"+simpleWidth + "moveX:"+moveX);
                        }
                    }
        }
        invalidate();
        return true;
    }
    public int getEachFraction(){
        return countBigger;
    }
}
