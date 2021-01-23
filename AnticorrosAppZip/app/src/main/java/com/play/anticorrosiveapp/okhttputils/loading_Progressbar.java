package com.play.anticorrosiveapp.okhttputils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.play.anticorrosiveapp.R;

public class loading_Progressbar {

    Context context;
    private AlertDialog alertDialog;

    public loading_Progressbar(Context context) {
        this.context = context;
    }

    public void alertDialog(){
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.height = 1000;
        window.setAttributes(attributes);
        window.setContentView(R.layout.custom_loading);
        ProgressBar pb = window.findViewById(R.id.progress_loading);
        final AnimationDrawable drawable = (AnimationDrawable) pb.getIndeterminateDrawable();
        pb.post(new Runnable() {
            @Override
            public void run() {
                drawable.start();
            }
        });
    }

    public void stopDialog(){
        alertDialog.dismiss();
    }
}
