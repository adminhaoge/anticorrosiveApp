package com.play.anticorrosiveapp.utils;

import android.app.Activity;
import android.app.ActivityManager;

import java.util.HashMap;
import java.util.Map;

public class ActivityManagerUtil {
private Map<String, Activity> mActivity;
public static ActivityManagerUtil mActivityManagerUtil;

private ActivityManagerUtil(){
    mActivity = new HashMap<>();
}

public static ActivityManagerUtil getInstance(){
    if (mActivityManagerUtil == null){
        synchronized (ActivityManagerUtil.class){
            if (mActivityManagerUtil == null){
                mActivityManagerUtil = new ActivityManagerUtil();
            }
        }
    }
    return mActivityManagerUtil;
}

public void addActivity(Activity activity){
    mActivity.put(activity.getClass().getName(),activity);
}

public void finishActivity(Activity activity){
    Activity activity1 = mActivity.get(activity.getClass().getName());
    activity1.finish();
    mActivity.remove(activity.getClass().getName());
}

public void finishActivity(Class<? extends Activity> classActivity){
    Activity activity = mActivity.get(classActivity);
    activity.finish();
    mActivity.remove(activity.getClass().getName());
}



}
