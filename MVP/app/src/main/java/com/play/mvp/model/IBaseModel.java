package com.play.mvp.model;

import com.play.mvp.bean.Goods;

import java.io.Serializable;
import java.util.List;

public interface IBaseModel<T> {
    void setRequestNetWorkData(OnRequest<T> onRequest);
    interface OnRequest<T>{
        void RequestError(int code ,String msg);
        void RequestSuccess(List<T> data);
    }
}
