package com.play.anticorrosiveapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class WelComeActivity extends Activity {

    private ImageView welcomeimage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        //设置延迟跳转方式有很多 比如（1.sleep（） 2.handle（） 发送延迟消息  3.Timer 类 4.用动画 0.7-1.0）
        //现在主流公司都是利用动画延迟跳转，这样会更友好一点。
        ToComeMainAnim();
    }

    private void ToComeMainAnim() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(welcomeimage, "alpha", 0.5f, 1.0f);
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(welcomeimage, "ScaleX", 0, 1f);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(welcomeimage, "ScaleY", 0, 1f);
//        AnimatorSet animatorSet = new AnimatorSet();
//        /**
//         * public Builder with(Animator anim) 和前面动画一起执行
//         * public Builder before(Animator anim) 执行前面的动画后再执行该动画
//         * public Builder after(Animator anim) 先执行这个动画再执行前面动画
//         * public Builder after(long delay) 延迟n毫秒之后执行动画
//         */
//        animatorSet.play(alpha).with(scaleX).with(scaleY);
        alpha.setDuration(3000);
        alpha.start();
        alpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startActivity(new Intent(getApplication(),MainActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);
            }
        });
    }

    private void initView() {
        welcomeimage = findViewById(R.id.WelComeImage);
    }
}
