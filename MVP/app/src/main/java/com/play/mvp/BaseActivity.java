package com.play.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.play.mvp.model.IBaseModel;
import com.play.mvp.presenter.IBasePresenter;
import com.play.mvp.view.IBaseVIew;

public abstract class BaseActivity<P extends IBasePresenter, M extends IBaseVIew> extends AppCompatActivity {
    public P presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourcesId());
        presenter = getPresenter();
        presenter.attachView((M)this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView((M)this);
    }

    protected abstract void initView();
    protected abstract int getResourcesId();
    protected abstract P getPresenter();

}
