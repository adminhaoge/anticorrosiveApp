package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.myapplication.custom.SnakePanelView;

public class activity_game extends Activity {

    private SnakePanelView mSnakePanelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mSnakePanelView = findViewById(R.id.snake_view);
        //开启setClickable就可以使用手势动作
        mSnakePanelView.setClickable(true);
        mSnakePanelView.reStartGame();
    }
}