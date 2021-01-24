package com.play.mvp.view;

import com.play.mvp.bean.Goods;

import java.util.List;

public interface IGoodsView extends IBaseVIew {
    void showGoods(List<Goods> goods);
}
