package com.play.anticorrosiveapp.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.play.anticorrosiveapp.R;

@SuppressLint({"AppCompatCustomView","HandlerLeak"})
public class VerificationCodeButton extends Button {

    private String mNormalStr;
    private ColorStateList mNormalColorList;
    private Drawable mNormalDrawable;
    private int mTimerBackGround;
    private int mTimerColor;
    // 是否开始加载
    private boolean mStartLoad = false;
    private int mTime;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
                if (mTime > 0){
                    mTime -= 1 ;
                    timer();
                }else {
                    stopRequest();
                }
        }
    };

    public VerificationCodeButton(Context context) {
        this(context,null);
    }

    public VerificationCodeButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VerificationCodeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mNormalStr = getText().toString();
        mNormalColorList = getTextColors();
        mNormalDrawable = getBackground();
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.VerificationCodeButton);
        mTimerBackGround = array.getResourceId(R.styleable.VerificationCodeButton_timing_background, 0);
        mTimerColor = array.getColor(R.styleable.VerificationCodeButton_timing_textColor, 0);
        array.recycle();
    }

    public void setNoraml(){
        this.setEnabled(true);
        this.setText(mNormalStr);
        this.setTextColor(mNormalColorList);
        this.setBackground(mNormalDrawable);
    }

    public void startLoad(){
        mStartLoad =true;
        setEnabled(false);
        setAttribute();
        setText("请稍后...");
    }

    public void setAttribute(){
        if (mTimerBackGround != 0){
            setBackgroundResource(mTimerBackGround);
        }
        if (mTimerColor != 0){
            setTextColor(mTimerColor);
        }
    }

    public void aginAfterTime(int time){
        setAttribute();
        this.mTime =time;
        this.setEnabled(false);
        timer();
    }

    private void timer() {
        setText(formatDuring(mTime));
        handler.sendEmptyMessageDelayed(0,1000);
    }

    private CharSequence formatDuring(int mTime) {
        if (mTime < 10){
            return "0"+mTime+"秒后获取";
        }
        return mTime +"秒后获取";
    }


    private void stopRequest() {
        mStartLoad = false;
        setNoraml();
    }




}
