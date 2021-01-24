package com.example.imitationtaobao.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.TintTypedArray;

import com.example.imitationtaobao.R;

public class CustomPersonal extends RelativeLayout {

    private ImageView head_img;
    private TextView tv_body;
    private View view_div;
    private ImageView icon_ar;

    public CustomPersonal(Context context) {
        this(context,null);
    }

    public CustomPersonal(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomPersonal(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        defStyleAttrs(context,attrs,defStyleAttr);
    }
    @SuppressLint("RestrictedApi")
    private void defStyleAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
         TintTypedArray array = TintTypedArray.obtainStyledAttributes(context,attrs
                , R.styleable.CustomPersonal,defStyleAttr,0);
        setLine(array.getBoolean(R.styleable.CustomPersonal_show_bottomline, true));
        setTextBody(array.getString(R.styleable.CustomPersonal_show_text));
        Drawable drawable = array.getDrawable(R.styleable.CustomPersonal_show_leftimg);
        setHeadDraw(drawable);
        array.recycle();
    }

    private void setHeadDraw(Drawable drawable) {
        head_img.setBackground(drawable);
    }


    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_personallist, this, true);
        head_img = view.findViewById(R.id.item_img);
        tv_body = view.findViewById(R.id.item_text);
        view_div = view.findViewById(R.id.divider);
        icon_ar = view.findViewById(R.id.icon_arrow);
    }

    private void setTextBody(String body) {
        if (body != null){
            tv_body.setText(body);
        }
    }



    private void setLine(boolean line) {
        if (line){
            view_div.setVisibility(View.VISIBLE);
        }else {
            view_div.setVisibility(View.GONE);
        }
    }

}
