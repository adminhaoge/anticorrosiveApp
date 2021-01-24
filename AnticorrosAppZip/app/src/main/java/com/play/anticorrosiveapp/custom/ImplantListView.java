package com.play.anticorrosiveapp.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ImplantListView extends ListView {

    public ImplantListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >>2 ,MeasureSpec.AT_MOST));
    }
}
