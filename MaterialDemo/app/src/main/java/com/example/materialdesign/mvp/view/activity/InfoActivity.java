package com.example.materialdesign.mvp.view.activity;

import android.graphics.Bitmap;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.materialdesign.mvp.base.BaseActivity;
import com.example.materialdesign.mvp.contract.InfoBodyContract;
import com.example.materialdesign.mvp.di.component.AppComponent;

import com.example.materialdesign.mvp.di.component.DaggerInfoComponent;
import com.example.materialdesign.mvp.di.module.InfoModule;
import com.example.materialdesign.mvp.presenter.InfoPresenter;
import com.example.materialdesign.mvp.utils.ACache;
import com.example.materialdesign.mvp.utils.Constant;
import com.example.materialdesign.mvp.utils.UrlImageGetterUtils;
import com.example.materialdesign.R;
import com.example.materialdesign.mvp.bean.ContentBean;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import javax.inject.Inject;

import butterknife.BindView;

public class InfoActivity extends BaseActivity implements InfoBodyContract.InfoBodyView {
    @BindView(R.id.img_activity_info)
    ImageView imgActivityInfo;
    @BindView(R.id.title_activity_info)
    TextView titleActivityInfo;
    @BindView(R.id.toolbar_activity_info)
    Toolbar toolbarActivityInfo;
    @BindView(R.id.tv_activity_info)
    TextView tvActivityInfo;
    ActionBar actionBar;
    @BindView(R.id.cool_toolbar)
    CollapsingToolbarLayout coolToolbar;
    @Inject
    InfoPresenter mPresenter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerInfoComponent.builder()
                .infoModule(new InfoModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        ACache aCache = ACache.get(this);
        int BodyId = (int) aCache.getAsObject(Constant.BODYID);
        mPresenter.attachView(this);
        mPresenter.fetch(BodyId);
//        AppApplication appApplication = AppApplication.get(this);
//        View view = appApplication.getView();
//        Bitmap viewImageCache = getViewImageCache(view);
//        if (viewImageCache != null){
//            imgActivityInfo.setBackgroundDrawable(new BitmapDrawable(viewImageCache));
//        }
//        int[] outLocation =new int[2];
//        view.getLocationOnScreen(outLocation);
//        int left = outLocation[0];
//        int top = outLocation[1];
//        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(imgActivityInfo.getLayoutParams());
//        marginLayoutParams.topMargin = top;
//        marginLayoutParams.leftMargin = left;
//        marginLayoutParams.width = view.getWidth();
//        marginLayoutParams.height = view.getHeight();
//        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(marginLayoutParams);
//        imgActivityInfo.setLayoutParams(params);

        setSupportActionBar(toolbarActivityInfo);
        titleActivityInfo.setMovementMethod(LinkMovementMethod.getInstance());
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        coolToolbar.setTitleEnabled(false);


    }
    //获取对应点击view的缓存转换成bitmap格式
    private Bitmap getViewImageCache(View view){
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap drawingCache = view.getDrawingCache();
        if (drawingCache == null) return null;

            Bitmap bitmap = Bitmap.createBitmap(drawingCache);
            view.destroyDrawingCache();
            return bitmap;
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_toolbarlayout;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void showRequestBody(String body, ContentBean contentBean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Spanned spanned = Html.fromHtml(body,new UrlImageGetterUtils(getApplication()), null);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvActivityInfo.setText(spanned);
                        Glide.with(getApplication()).load(contentBean.getImage()).into(imgActivityInfo);
                        titleActivityInfo.setText(contentBean.getTitle());
                    }
                });
            }
        }).start();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
