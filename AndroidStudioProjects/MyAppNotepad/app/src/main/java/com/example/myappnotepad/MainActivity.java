package com.example.myappnotepad;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.wear.widget.SwipeDismissFrameLayout;

import com.example.myappnotepad.bean.CartProvider;
import com.example.myappnotepad.bean.TitleBean;

import java.util.List;

public class MainActivity extends WearableActivity{

    GestureDetector detector;
    private LinearLayout layout;
    private ListView notepad;
    private AlertDialog alertDialog;
    private com.example.myappnotepad.notepadAdapter notepadAdapter;
    private CartProvider cartProvider;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (LinearLayout)findViewById(R.id.linearlayout);
        notepad = findViewById(R.id.list_biji);
        cartProvider = new CartProvider(this);
        detector=new GestureDetector(new GestureListener());
        notepad.setOnTouchListener(new TouhListener());
        notepad.setLongClickable(true);
        initListview();

    }


    private void initListview() {

        final CartProvider cartProvider = new CartProvider(this);
        final List<TitleBean> addListItem = cartProvider.getAll();
           if (addListItem != null){
               notepadAdapter = new notepadAdapter(this,R.layout.list_title_items,addListItem);
               notepad.setAdapter(notepadAdapter);
           }

           //点击对应的item然后将对应的组织
           notepad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   Intent intent=new Intent(getApplication(), Details.class);
                   TitleBean titleBean = addListItem.get(position);
                   intent.putExtra("id",titleBean.getId());
                   intent.putExtra("title",titleBean.getTitle());
                   intent.putExtra("content",titleBean.getContent());
                   startActivity(intent);
               }
           });

           notepad.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
               @Override
               public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                   alertDialog = new AlertDialog.Builder(MainActivity.this)
                           .setMessage("是否删除")
                           .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   notepadAdapter.remove(addListItem.get(position));
                                   List<TitleBean> all = cartProvider.getAll();
                                   TitleBean titleBean = all.get(position);
                                   cartProvider.delete(titleBean);
                                   notepadAdapter.notifyDataSetChanged();
                               }
                           })
                           .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                               }
                           })

                           .create();
                   Window dialogWindow = alertDialog.getWindow();
                   dialogWindow.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
                   alertDialog.show();
                   return true;
               }
           });

    }



    ///触摸屏幕监听
    class TouhListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return detector.onTouchEvent(event);
        }

    }
    //手势滑动监听
    class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(e2.getX()-e1.getX())>50) {
                layout.setAnimation(AnimationUtils.loadAnimation(getApplication(),R.anim.screen_cutting));
                Toast.makeText(getApplicationContext(), "向左滑动", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplication(), Edit_Page.class));
                finish();
            }
            return false;
        }
    }
}
