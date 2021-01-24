package com.play.anticorrosiveapp.utils.autoextras;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ReflexAnnToExtras {
    public static void getExtras(Activity activity){
        Class<? extends Activity> aClass = activity.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        Bundle bundle = activity.getIntent().getExtras();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(annotationExtrasType.class)) {
                annotationExtrasType annotation = declaredField.getAnnotation(annotationExtrasType.class);
                String key = TextUtils.isEmpty(annotation.value()) ? declaredField.getName() : annotation.value();
                if (bundle.containsKey(key)){
                    Object o = bundle.get(key);
                    Class<?> type = declaredField.getType().getComponentType();
                    if (declaredField.getType().isArray() && Parcelable.class.isAssignableFrom(type)){
                        Object[] obj= (Object[]) o;
                        Object[] objects =  Arrays.copyOf(obj, obj.length, (Class<? extends Object[]>) declaredField.getType());
                        obj = objects;
                    }
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(activity,o);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                }
            }
        }
    }
}
