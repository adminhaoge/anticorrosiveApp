package com.example.imitationtaobao.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.imitationtaobao.R;
import com.example.imitationtaobao.bean.CartProvider;
import com.example.imitationtaobao.bean.WaresHot;
import com.example.imitationtaobao.mToolbar;
import com.example.imitationtaobao.utils.Constant;
import com.mob.MobSDK;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class WareDetailActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar_detail)
    mToolbar toolbarDetail;
    @BindView(R.id.webView)
    WebView webView;
    private WaresHot waresHot;
    private WebAppInterface webAppInterface;
    private CartProvider cartProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_detall);
        ButterKnife.bind(this);
        cartProvider = CartProvider.getInstance(this);
        webAppInterface = new WebAppInterface(this);
        Serializable serializableExtra = getIntent().getSerializableExtra(Constant.WARE);
        if (serializableExtra == null) finish();
        waresHot = (WaresHot) serializableExtra;

        initToolBar();

        initWebView();
    }

    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);

        webView.loadUrl("http://112.124.22.238:8081/course_api/wares/detail.html");
        webView.addJavascriptInterface(webAppInterface,"appInterface");
        webView.setWebViewClient(new Wc());
    }

    private void initToolBar() {
        toolbarDetail.setNavigationOnClickListener(this);
        toolbarDetail.getRightButton().setVisibility(View.VISIBLE);
        toolbarDetail.setRightButtonText("分享");
        toolbarDetail.getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnekeyShare oks = new OnekeyShare();
                // title标题，微信、QQ和QQ空间等平台使用
                oks.setTitle(getString(R.string.share));
                // titleUrl QQ和QQ空间跳转链接
                oks.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                oks.setText(waresHot.getName());
                // setImageUrl是网络图片的url
                oks.setImageUrl(waresHot.getImgUrl());
                // url在微信、Facebook等平台中使用
                oks.setUrl("http://sharesdk.cn");
                // 启动分享GUI
                oks.show(MobSDK.getContext());
            }
        });
    }

    class Wc extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webAppInterface.showDetail();
        }
    }

    class WebAppInterface{
        private  Context context;
        public WebAppInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void showDetail(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:showDetail("+waresHot.getId()+")");
                }
            });
        }
        @JavascriptInterface
        public void buy(long id){
            cartProvider.put(waresHot);
            Toast.makeText(context,"已添加到购物车!",Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void addFavorites(long id){

        }
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}

