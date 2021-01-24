package com.play.mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.play.mvp.adapter.listAdapter;
import com.play.mvp.bean.Goods;
import com.play.mvp.presenter.GoodsPresenter;
import com.play.mvp.view.IGoodsView;

import java.util.List;

public class MainActivity extends BaseActivity<GoodsPresenter,IGoodsView> implements IGoodsView{
    private ListView listView;


    @Override
    protected void initView() {
        listView = findViewById(R.id.list_demo);
        presenter.fetch();
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_main;
    }

    @Override
    protected GoodsPresenter getPresenter() {
        return new GoodsPresenter();
    }

    @Override
    public void showGoods(List<Goods> goods) {
        listView.setAdapter(new listAdapter(goods,this));
    }

    @Override
    public void showError() {

    }
}