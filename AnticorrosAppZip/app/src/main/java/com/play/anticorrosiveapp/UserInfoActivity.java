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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.play.anticorrosiveapp.R;
import com.play.anticorrosiveapp.bean.UserLoginResult;
import com.play.anticorrosiveapp.custom.RoundImageView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pub.devrel.easypermissions.EasyPermissions;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int ALBUM_OK = 3432;
    private static final int CUT_OK = 3487;
    private static final String TAG = UserInfoActivity.class.getName();
    private static final int REQUEST_CODE = 8213;
    private ImageView mUserLogoIV;
    private Dialog dialog;
    private static String path="/sdcard/myHead/";//sd路径
    private Uri uritempFile;
    private String fileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initListener();
    }

    //获取权限
    private void getPermission() {
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //已经打开权限
            Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //框架要求必须这么写
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



    private void initListener() {
        mUserLogoIV.setOnClickListener(this);
    }

    private void initView() {
        mUserLogoIV = findViewById(R.id.user_logo);
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
        if(bt!=null){
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
                //调用系统相册
            case R.id.image_depot:
                getPermission();
                startIntentMedia();
                break;
                //调用系统相机
            case R.id.photo_camre:
                getPermission();
                Intent intent = new Intent();
                intent.setAction("android.media.action.IMAGE_CAPTURE");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(intent,REQUEST_CODE);
                dialog.dismiss();
                break;
                //dialog取消按钮
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
        if (resultCode == RESULT_OK){
            //打开相册的状态码
            if (requestCode == ALBUM_OK){
                Uri uri = data.getData();
                cropPhoto(uri);
            }
            //调用相机的返回状态码
            else if (requestCode == REQUEST_CODE){
                Bitmap extra = data.getParcelableExtra("data");
                if (extra != null){
                    setPicToView(extra);//保存在SD卡中
                    mUserLogoIV.setImageBitmap(extra);
                    //调用成功后将sd卡中的图片上传到服务器
                    upLoadImageToServer();
                }
            }
            //裁剪成功后返回的状态码
            else if (requestCode == CUT_OK){
                if (data != null) {
                    mUserLogoIV.setImageURI(uritempFile);
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                        setPicToView(bitmap);//保存在SD卡中
                        //调用成功后将sd卡中的图片上传到服务器

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void upLoadImageToServer() {
        File file = new File(fileName);
        RequestBody requestBody = MultipartBody.create(MediaType.parse("application/octet-stream"), file);
        String Url = "http://v2.ffu365.com/index.php?m=Api&c=Member&a=userUploadAvatar";
        String json = getSharedPreferences("info", 0).getString("user_info", null);
        if (!TextUtils.isEmpty(json)){
            Gson gson = new Gson();
            UserLoginResult.DataBean dataBean = gson.fromJson(json, UserLoginResult.DataBean.class);
            MultipartBody multipartBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("appid", "1")
                    .addFormDataPart("uid", dataBean.getMember_info().getUid())
                    .addFormDataPart("file", file.getName(), requestBody)
                    .build();
            Request build = new Request.Builder()
                    .url(Url)
                    .post(multipartBody)
                    .build();
            OkHttpClient build1 = new OkHttpClient();
            build1.newCall(build).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.e("afafafaf",e.getMessage());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String string = response.body().string();
                    Log.e("TAG",string);
                }
            });


        }


    }



    /** * 调用系统的裁剪 * @param uri */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("scale", true);
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        Log.e("TAG", String.valueOf(uritempFile));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
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
        //图片名字
        fileName = path + "head.jpg";
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
