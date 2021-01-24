package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.myapplication.apadter.Adapter_panking;
import com.example.myapplication.bean.Panking_bean;
import com.example.myapplication.custom.SnakePanelView;

public class activity_panking extends Activity {

    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panking);
        list = findViewById(R.id.list_panking);
        SnakePanelView snakePanelView = new SnakePanelView(this);
        Adapter_panking panking = new Adapter_panking(this,R.layout.listview_item_panking,snakePanelView.score_list);
        list.setAdapter(panking);
    }
}