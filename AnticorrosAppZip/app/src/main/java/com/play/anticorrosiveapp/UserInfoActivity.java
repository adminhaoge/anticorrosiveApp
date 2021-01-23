package com.play.anticorrosiveapp;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.play.anticorrosiveapp.R;
import com.play.anticorrosiveapp.custom.RoundImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int ALBUM_OK = 3432;
    private static final int CUT_OK = 3487;
    private static final String TAG = UserInfoActivity.class.getName();
    private static final int REQUEST_CODE = 8213;
    private ImageView mUserLogoIV;
    private Dialog dialog;
    private static String path="/sdcard/myHead/";//sd路径
    private Bitmap head;//头像Bitmap
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initListener();
    }

    private void initListener() {
        mUserLogoIV.setOnClickListener(this);
    }

    private void initView() {
        mUserLogoIV = findViewById(R.id.user_logo);
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
        if(bt!=null){
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            mUserLogoIV.setImageDrawable(drawable);
        }else{
            /** * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中 * */
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_logo:
                showDialogView();
                break;
            case R.id.image_depot:
                startIntentMedia();
                break;
            case R.id.photo_camre:
                Intent intent = new Intent();
                intent.setAction("android.media.action.IMAGE_CAPTURE");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(intent,REQUEST_CODE);
                dialog.dismiss();
                break;
            case R.id.user_cancel:
                dialog.dismiss();
                dialog.cancel();
                break;
        }
    }

    private void startIntentMedia() {
        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent1,ALBUM_OK );
        dialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ALBUM_OK:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }
                break;

            case REQUEST_CODE:
                Bitmap extra = data.getParcelableExtra("data");
                if (extra != null){
                    setPicToView(extra);//保存在SD卡中
                    mUserLogoIV.setImageBitmap(extra);
                }

            case CUT_OK:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if(head!=null){
                        /** * 上传服务器代码 */
                        setPicToView(head);//保存在SD卡中
                        mUserLogoIV.setImageBitmap(head);//用ImageView显示出来
                    }
                }
                break;

            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    /** * 调用系统的裁剪 * @param uri */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CUT_OK);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName =path + "head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void showDialogView() {
        dialog = new Dialog(this, R.style.dialog);
        View view = View.inflate(this,R.layout.custom_dialog,null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.dialog_anim_window);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        viewInitListener(view);
        dialog.show();
    }

    private void viewInitListener(View view) {
        Button ImageDepot= view.findViewById(R.id.image_depot);
        Button PhotoCamre = view.findViewById(R.id.photo_camre);
        Button UserCancle = view.findViewById(R.id.user_cancel);

        ImageDepot.setOnClickListener(this);
        PhotoCamre.setOnClickListener(this);
        UserCancle.setOnClickListener(this);
    }
}
