package com.play.mvp.presenter;

import android.util.Log;

import com.play.mvp.bean.Goods;
import com.play.mvp.model.GoodsModel;
import com.play.mvp.model.IBaseModel;
import com.play.mvp.view.IBaseVIew;
import com.play.mvp.view.IGoodsView;

import java.util.List;

public class GoodsPresenter<T extends IGoodsView> extends IBasePresenter {
    public GoodsModel goodsModel;
    public GoodsPresenter() {
        goodsModel = new GoodsModel();
    }

    public void fetch(){
       if (iBaseView.get() != null &&  goodsModel != null){
           goodsModel.setRequestNetWorkData(new IBaseModel.OnRequest<Goods>() {
               @Override
               public void RequestError(int code, String msg) {
                   Log.e("TAG",String.format("code失败状态码: %s,message失败原因: %s",code,msg));
               }

               @Override
               public void RequestSuccess(List<Goods> data) {
                   ((IGoodsView)iBaseView.get()).showGoods(data);
               }
           });
       }
    }


}
