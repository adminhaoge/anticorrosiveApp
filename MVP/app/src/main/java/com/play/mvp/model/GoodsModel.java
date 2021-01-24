package com.play.mvp.model;

import com.play.mvp.bean.Goods;
import com.play.mvp.view.IBaseVIew;

import java.util.ArrayList;
import java.util.List;

public class GoodsModel implements IBaseModel<Goods> {
    @Override
    public void setRequestNetWorkData(OnRequest<Goods> onRequest) {
        onRequest.RequestSuccess(getData());
    }

    private List<Goods> getData() {
        ArrayList<Goods> goodsArrayList = new ArrayList<>(3);
        goodsArrayList.add(new Goods(1,"衣服","50"));
        goodsArrayList.add(new Goods(2,"裤子","30"));
        goodsArrayList.add(new Goods(3,"内裤","10"));
        return goodsArrayList;
    }


}
