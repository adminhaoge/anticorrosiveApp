package com.play.mvp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.play.mvp.bean.Goods;

import java.util.List;

public class listAdapter extends BaseAdapter {
    private List<Goods> goodsList;
    private Context context;


    public listAdapter(List<Goods> goodsList, Context context) {
        this.goodsList = goodsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
