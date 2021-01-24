package com.play.anticorrosiveapp.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.play.anticorrosiveapp.R;

@SuppressLint("AppCompatCustomView")
public class CustomImageProportion extends ImageView {

    private int width;
    private int height;

    public CustomImageProportion(Context context) {
        this(context,null);
    }

    public CustomImageProportion(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomImageProportion(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomImageProportion);
        width = array.getInteger(R.styleable.CustomImageProportion_width_proportion, 0);
        height = array.getInteger(R.styleable.CustomImageProportion_height_proportion, 0);
        array.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = (widthSize * height / width);

        setMeasuredDimension(widthSize,heightSize);
    }
}
