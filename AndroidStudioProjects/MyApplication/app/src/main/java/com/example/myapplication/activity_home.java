package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class activity_home extends AppCompatActivity  implements View.OnClickListener{

    private Button start_game;
    private Button panking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        start_game = findViewById(R.id.tb_start_game);
        panking = findViewById(R.id.bt_panking);
        start_game.setOnClickListener(this);
        panking.setOnClickListener(this);

        javaMath();
    }

    private void javaMath() {
        int abs = Math.abs(30-50);
        double random = Math.random();
        long round = Math.round(3.6);
        Log.e("TAG",abs+"---"+random+"-----"+round);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tb_start_game:
                startActivity(new Intent(getApplication(),activity_game.class));
                finish();
                break;
            case R.id.bt_panking:
                startActivity(new Intent(getApplication(),activity_panking.class));
                finish();
                break;
        }
    }
}