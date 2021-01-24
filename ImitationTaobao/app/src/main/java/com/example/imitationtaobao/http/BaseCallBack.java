package com.example.imitationtaobao.http;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class BaseCallBack<T> {
    public Type type;

    public BaseCallBack() {
        this.type = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> typeClass){
        Type genericSuperclass = typeClass.getGenericSuperclass();
        if (genericSuperclass instanceof Class){
            throw new RuntimeException("Missing");
        }
        ParameterizedType gs = (ParameterizedType) genericSuperclass;
        return $Gson$Types.canonicalize(gs.getActualTypeArguments()[0]);
    }
    public abstract void onRequestBefore(Request request);
    public abstract void onFailure(Call call, IOException e);
    public abstract void onRequest(Request request);
    public abstract void onResponseSuccess(Response response,T t);
    public abstract void onResponseError(Response response,int code,Exception e);
}
