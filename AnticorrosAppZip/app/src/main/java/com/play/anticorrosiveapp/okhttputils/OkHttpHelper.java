package com.play.anticorrosiveapp.okhttputils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpHelper {
    private static OkHttpClient mOkHttp;
    private Gson gson;
    private Handler handler;
    private OkHttpHelper(){
        OkHttpClient.Builder builder = mOkHttp.newBuilder();
        builder.readTimeout(10,TimeUnit.SECONDS);
        builder.writeTimeout(10,TimeUnit.SECONDS);
        builder.connectTimeout(10,TimeUnit.SECONDS);
        gson = new Gson();
        handler = new Handler(Looper.getMainLooper());

    }

    public synchronized static OkHttpHelper getmOkHttp() {
        if (mOkHttp == null){
            mOkHttp = new OkHttpClient();
        }
        return new OkHttpHelper();
    }

    public void get(String url,BaseCallBack callBack){
        Request request = buildRequest(url,null, HttpMethodType.GET);
        doRequest(request,callBack);
    }


    public void post(String url , Map<String,String> params, BaseCallBack callBack){
        Request request = buildRequest(url, params, HttpMethodType.POST);
        doRequest(request,callBack);
    }

    private Request buildRequest(String url ,Map<String,String> params ,HttpMethodType type){
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (type == HttpMethodType.GET){
            builder.get();
        }else if (type == HttpMethodType.POST){
            FormBody.Builder fb = new FormBody.Builder();
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                fb.add(entry.getKey(),entry.getValue());
            }
            builder.post(fb.build());
        }
        return builder.build();
    }
    enum HttpMethodType{
        GET,
        POST
    }

    public void doRequest(final Request request, final BaseCallBack callBack){
        callBack.onRequestBefore(request);
        mOkHttp.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                try {
                    Thread.sleep(5000);
                    callBack.onFailure(call,e);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                callBack.onRequest(request);
                if (response.isSuccessful()){
                    String string = response.body().string();
                    if (callBack.type == String.class){
                         callBack.onResponseSuccess(response,string);
                         MainHandlerSuccess(response,string,callBack);
                    }else {
                       try{
                           Object json = gson.fromJson(string, callBack.type);
                           MainHandlerSuccess(response,json,callBack);
                       }catch (JsonParseException j){
                           callBack.onResponseError(response,response.code(),j);
                           MainHandlerError(callBack,response,response.code(),j);
                       }
                    }
                }else {
                    callBack.onResponseError(response,response.code(),null);
                    MainHandlerError(callBack,response,response.code(),null);
                }
            }
        });
    }

    private void MainHandlerSuccess(final Response response, final Object object, final BaseCallBack callBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onResponseSuccess(response,object);
            }
        });
    }

    private void MainHandlerError(BaseCallBack callBack, Response response, int code, JsonParseException j){
            callBack.onResponseError(response,code,j);
    }
}
