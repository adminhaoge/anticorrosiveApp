package com.play.mvp.presenter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.play.mvp.view.IBaseVIew;
import com.play.mvp.view.IGoodsView;

import java.lang.ref.WeakReference;

public class IBasePresenter<T extends IBaseVIew> implements LifecycleObserver {
    public WeakReference<T> iBaseView;

    public void attachView(T view){
        iBaseView = new WeakReference<>(view);
    }

    public void detachView(T view){
        if (iBaseView != null){
            iBaseView.clear();
            iBaseView = null;
        }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void OnCreate(){

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void OnStart(){

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void OnResume(){

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void OnPause(){

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void OnStop(){

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void OnDestroy(){

    }

}
