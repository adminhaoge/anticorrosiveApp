package com.play.anticorrosiveapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.play.anticorrosiveapp.utils.autoextras.ReflexAnnToExtras;
import com.play.anticorrosiveapp.utils.autoextras.annotationExtrasType;

public class DetailLinkActivity extends AppCompatActivity {
    private WebView mWebView;
    private WebSettings mWebSettings;

    @annotationExtrasType
    private String AD_LINK;
    @annotationExtrasType
    private String COMPANY_LINK;
    private ProgressBar pb_bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_link);
        initAnnExtras();
        initView();
        initData();
    }

    private void initAnnExtras() {
        ReflexAnnToExtras.getExtras(this);
    }

    private void initView() {
        mWebView = findViewById(R.id.web_view);
        pb_bar = findViewById(R.id.pb_view);
    }

    private void initData() {
        mWebSettings = mWebView.getSettings(); //获取mWebSettings参数设置
        mWebSettings.setUseWideViewPort(false); //将图片调整到合适webVIew的大小
        mWebSettings.setJavaScriptEnabled(true); //支持js
        mWebSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pb_bar.setProgress(newProgress);
                int progress = pb_bar.getProgress();
                if (progress == 100){
                    pb_bar.setVisibility(View.GONE);
                }
            }

        });
        mWebView.loadUrl(AD_LINK != null ? AD_LINK:COMPANY_LINK);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mWebView.canGoBack()){//判断是否能返回
            mWebView.goBack();//返回
        }else {
            finish();//关闭当前页面
        }
    }
}
