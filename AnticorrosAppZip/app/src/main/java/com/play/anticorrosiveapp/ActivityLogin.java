package com.play.anticorrosiveapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.play.anticorrosiveapp.bean.UserLoginResult;
import com.play.anticorrosiveapp.okhttputils.BaseCallBack;
import com.play.anticorrosiveapp.okhttputils.LoadingCallBack;
import com.play.anticorrosiveapp.okhttputils.OkHttpHelper;
import com.play.anticorrosiveapp.utils.MD5Util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ActivityLogin extends Activity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener{

    private static final String TAG = ActivityLogin.class.getName();
    private CheckBox visCb;
    private TextView quickLogin;
    private TextView noPassword;
    private Button login;
    private EditText password;
    private EditText phone;
    private TextView registerTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        initView();
        initListener();
    }

    private void initListener() {
        visCb.setOnCheckedChangeListener(this);
        login.setOnClickListener(this);
        noPassword.setOnClickListener(this);
        registerTxt.setOnClickListener(this);
    }

    private void initView() {
        phone = findViewById(R.id.ed_phone);
        password = findViewById(R.id.ed_password);
        login = findViewById(R.id.bt_login);
        noPassword = findViewById(R.id.no_password_txt);
        quickLogin = findViewById(R.id.quick_login_txt);
        visCb = findViewById(R.id.cb_vis);
        registerTxt = findViewById(R.id.register_txt);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            password.setSelection(password.getText().length());
        }else {
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            password.setSelection(password.getText().length());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.no_password_txt:
                break;
            case R.id.bt_login:
                NetworkLoginRequest();
                break;
            case R.id.register_txt:
                Intent intent = new Intent(this,UserRegisterActivity.class);
                startActivityForResult(intent,0);
                break;
        }
    }

    private void NetworkLoginRequest() {
        String userPhone = phone.getText().toString().trim();
        String userPwd = password.getText().toString().trim();
        if (TextUtils.isEmpty(userPhone)){
            Toast.makeText(this,"手机号不能为空！",Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(userPwd)){
            Toast.makeText(this,"密码不能为空！",Toast.LENGTH_SHORT).show();
        }

        requestServiceLogin(userPhone,userPwd);
    }

    private void requestServiceLogin( String userPhone, String userPwd ) {
        Map<String,String> mapPost = new HashMap<>();
        mapPost.put("appid", "1");
        mapPost.put("cell_phone", userPhone);
        mapPost.put("password", MD5Util.strToMd5(userPwd));
        OkHttpHelper.getmOkHttp().post("http://v2.ffu365.com/index.php?m=Api&c=Member&a=login", mapPost, new LoadingCallBack<UserLoginResult>(this) {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG",e.getMessage());
            }

            @Override
            public void onResponseSuccess(Response response, UserLoginResult userLoginResult) {
                // 刷新界面
                dealLoginResult(userLoginResult);
            }

            @Override
            public void onResponseError(Response response, int code, Exception e) {
            }
        });
    }

    private void dealLoginResult(UserLoginResult userLoginResult) {
        // 首先判断有没有成功
        if(userLoginResult.getErrcode() == 1){
            // 成功处理
            // 1.需要保存登录状态   当前设置为已登录
            SharedPreferences sp =  getSharedPreferences("info",0);
            sp.edit().putBoolean("is_login",true).commit();
            // 2.需要保存用户信息
            UserLoginResult.DataBean userData =  userLoginResult.getData();
            Gson gson = new Gson();
            String uesrInfoStr =  gson.toJson(userData);
            // 保存的用户信息为Json格式的字符串
            sp.edit().putString("user_info",uesrInfoStr).commit();
            // 3.关掉这个页面
            finish();
        }else{
            // 登录失败
            Toast.makeText(this,userLoginResult.getErrmsg(),Toast.LENGTH_LONG).show();
        }
    }
}
