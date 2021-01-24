package com.play.anticorrosiveapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.play.anticorrosiveapp.adapter.ViewPageAdapter;
import com.play.anticorrosiveapp.fragments.FragmentCenter;
import com.play.anticorrosiveapp.fragments.FragmentHome;
import com.play.anticorrosiveapp.fragments.FragmentMessage;
import com.play.anticorrosiveapp.fragments.FragmentRim;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{

    private ViewPager mainViewPage;
    private RadioButton center,message,rim,home;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }


    private void initView() {
        home = findViewById(R.id.home_rb);
        rim = findViewById(R.id.rim_rb);
        message = findViewById(R.id.message_rb);
        center = findViewById(R.id.center_rb);
        mainViewPage = findViewById(R.id.main_viewpage);
    }

    private void initData() {
        home.setOnClickListener(this);
        rim.setOnClickListener(this);
        message.setOnClickListener(this);
        center.setOnClickListener(this);



        fragments = new ArrayList<>(5);
        fragments.add(new FragmentHome());
        fragments.add(new FragmentRim());
        fragments.add(new FragmentMessage());
        fragments.add(new FragmentCenter());
        mainViewPage.setAdapter(new ViewPageAdapter(getSupportFragmentManager(),fragments));
        mainViewPage.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.e("TAG", String.valueOf(v.getId()));
        switch (v.getId()){
            case R.id.home_rb:
                mainViewPage.setCurrentItem(0,true);
                break;
            case R.id.rim_rb:
                mainViewPage.setCurrentItem(1,true);
                break;
            case R.id.message_rb:
                mainViewPage.setCurrentItem(2,true);
                break;
            case R.id.center_rb:
                mainViewPage.setCurrentItem(3,true);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                home.setChecked(true);
                break;
            case 1:
                rim.setChecked(true);
                break;
            case 2:
                message.setChecked(true);
                break;
            case 3:
                center.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}