package com.example.myappnotepad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.wearable.activity.WearableActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;

public class Details extends WearableActivity implements View.OnTouchListener,View.OnClickListener {

    private TextView _detail_txt;
    private DrawerLayout drawerLayout;
    private LinearLayout right;
    private ScrollView scrollView;
    private GestureDetector detector;
    private Button add;
    private Button sub;
    private Button red;
    private Button black;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailspage);
        initView();
        initGetExtra();
        initOnClick();
    }

    private void initOnClick() {
        add.setOnClickListener(this);
        sub.setOnClickListener(this);
        red.setOnClickListener(this);
        black.setOnClickListener(this);

    }

    private void initGetExtra() {
        Intent intent = getIntent();
        if (intent != null){
            String content = intent.getStringExtra("content");
            _detail_txt.setText(content);
        }
    }

    private void initView() {
        _detail_txt = findViewById(R.id.detail_txt);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        right = (LinearLayout) findViewById(R.id.right_draw);
        scrollView = findViewById(R.id.scr_body);
        add = findViewById(R.id.bt_add);
        sub = findViewById(R.id.bt_sub);
        red = findViewById(R.id.bt_red);
        black = findViewById(R.id.bt_black);


        detector = new GestureDetector(new MyOnFling());
        scrollView.setOnTouchListener(this);
        scrollView.setLongClickable(true);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return detector.onTouchEvent(event);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_red:
                _detail_txt.setTextColor(R.color.color_red);
                break;
            case R.id.bt_black:
                _detail_txt.setTextColor(R.color.color_black);
                break;
            case R.id.bt_add:
                if (_detail_txt.getTextSize()<20){
                    _detail_txt.setTextSize(_detail_txt.getTextSize()+1);
                }
                break;
            case R.id.bt_sub:
                _detail_txt.setTextSize(_detail_txt.getTextSize()-10);
                break;
        }

    }


    class MyOnFling  extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > 50){
                drawerLayout.closeDrawer(right);
                drawerLayout.openDrawer(right);
            }
            return false;
        }
    }
}
