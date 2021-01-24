package com.example.myappnotepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.wear.widget.SwipeDismissFrameLayout;

import com.example.myappnotepad.bean.CartProvider;
import com.example.myappnotepad.bean.TitleBean;

public class Edit_Page extends WearableActivity implements View.OnTouchListener {

    private EditText title;
    private EditText content;
    private LinearLayout body;
    private GestureDetector detector;
    private int count_id = 0 ;
    private SharedPreferences why;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        why = getSharedPreferences("why", 0);
        initView();
    }


    private void initView() {
        title = findViewById(R.id.detail_title);
        content = findViewById(R.id.detail_content);
        body = findViewById(R.id.layout_body);
        detector = new GestureDetector(new MyOnGestureListener());
        body.setOnTouchListener(this);
        body.setLongClickable(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(e2.getX()-e1.getX())>50){
                body.setAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.screen_cutting));
                Business();
                Log.e("TAG","onFling");
            }
            return false;
        }
    }



    private void Business() {
        String title_txt = title.getText().toString().trim();
        String content_txt = content.getText().toString().trim();
        count_id = why.getInt("ID", 0);
        if (title_txt.length() != 0 && content_txt.length() != 0){
            count_id++;
            CartProvider cartProvider = new CartProvider(this);
            cartProvider.put(convertDate(new TitleBean(),title_txt,content_txt));
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }

    public TitleBean convertDate(TitleBean data, String title_txt, String content_txt){
        SharedPreferences.Editor edit = why.edit();
        edit.putInt("ID",count_id);
        edit.commit();
        data.setId(count_id);
        data.setTitle(title_txt);
        data.setContent(content_txt);
        return data;
    }
}
