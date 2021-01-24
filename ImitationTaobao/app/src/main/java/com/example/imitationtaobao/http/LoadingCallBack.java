package com.example.imitationtaobao.http;

import android.content.Context;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;

public abstract class LoadingCallBack<T> extends BaseCallBack<T> {

    private final loading_Progressbar lp;

    public LoadingCallBack(Context context) {
        lp = new loading_Progressbar(context);
    }
    public void startAnim(){
        lp.alertDialog();
    }

    public void endAnim(){
        lp.stopDialog();
    }

    @Override
    public void onRequestBefore(Request request) {
        startAnim();
    }

    @Override
    public void onFailure(Call call,IOException e) {
        endAnim();
    }

    @Override
    public void onRequest(Request request) {
        endAnim();
    }
}
