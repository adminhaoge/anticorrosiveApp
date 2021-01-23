package com.play.anticorrosiveapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getName();
    private boolean isViewCreated = false;
    private boolean isCurrentVisibleState = false;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onCreateView");
        view = inflater.inflate(ResourceLayout(), container, false);
        initView(view);
        isViewCreated = true;
        if (getUserVisibleHint()){
            setUserVisibleHint(true);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        initListener();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.e(TAG,"setUserVisibleHint");
        super.setUserVisibleHint(isVisibleToUser);
        if (isViewCreated){
            if (isVisibleToUser && !isCurrentVisibleState){
                //如果onCreateView执行完就进行网络操作
                dispatchUserVisibleHint(true);
            }else if (!isVisibleToUser && isCurrentVisibleState){
                dispatchUserVisibleHint(false);
            }
        }
    }


    @Override
    public void onResume() {
        Log.e(TAG,"onResume");
        super.onResume();
        if (getUserVisibleHint() && !isCurrentVisibleState){
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onPause() {
        Log.e(TAG,"onPause");
        super.onPause();
        if (getUserVisibleHint() && isCurrentVisibleState){
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.e(TAG,"onDestroyView");
        super.onDestroyView();
    }

    private void dispatchUserVisibleHint(boolean visible) {
        isCurrentVisibleState = visible;
            if (visible){
                RequestNetworkDataStart();
            }else {
                RequestNetworkStop();
            }
    }



    protected abstract void initView(View view);
    protected abstract void initListener();
    protected abstract int ResourceLayout();
    protected abstract void RequestNetworkDataStart();
    protected abstract void RequestNetworkStop();
}
