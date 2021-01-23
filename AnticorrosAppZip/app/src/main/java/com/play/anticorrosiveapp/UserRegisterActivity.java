package com.play.anticorrosiveapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
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
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.play.anticorrosiveapp.bean.UserLoginResult;
import com.play.anticorrosiveapp.bean.UserRequestCodeResult;
import com.play.anticorrosiveapp.custom.VerificationCodeButton;
import com.play.anticorrosiveapp.utils.ActivityManagerUtil;
import com.play.anticorrosiveapp.utils.MD5Util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserRegisterActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener {

    private static final String TAG = UserRegisterActivity.class.getName();
    private TextView userAgreement;
    private Button userRegister;
    private VerificationCodeButton btVer;
    private EditText reCorner;
    private EditText rePassword;
    private EditText rePhone;
    private CheckBox checkPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        initView();
    }

    private void initView() {
        rePhone = findViewById(R.id.ed_re_phone);
        rePassword = findViewById(R.id.ed_re_password);
        reCorner = findViewById(R.id.ed_re_corner);
        btVer = findViewById(R.id.bt_ver);
        userRegister = findViewById(R.id.user_register_bt);
        userAgreement = findViewById(R.id.user_agreement_tv);
        checkPassword = findViewById(R.id.check_password_cb);

        checkPassword.setOnCheckedChangeListener(this);
        userRegister.setOnClickListener(this);
        btVer.setOnClickListener(this);
        userAgreement.setText(Html.fromHtml("我已阅读并同意<font color = '#24cfa2'>《享学咨询》</font>"));

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            rePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            rePassword.setSelection(rePassword.getText().length());
        }else {
            rePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            rePassword.setSelection(rePassword.getText().length());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_register_bt:
                dealUserRegister();
                break;
            case R.id.bt_ver:
                btVer.startLoad();
                requestUserCode();
                break;
        }
    }

    private void requestUserCode() {
        String userPhone = rePhone.getText().toString().trim();
        if(TextUtils.isEmpty(userPhone)){
            Toast.makeText(this,"请输入手机号",Toast.LENGTH_LONG).show();
            return;
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        // 2.构建参数的body  MultipartBody.FORM 表单形式
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        // 2.2封装参数
        builder.addFormDataPart("appid", "1");
        builder.addFormDataPart("sms_type","3");
        builder.addFormDataPart("cell_phone",userPhone); //  添加多个参数

        // TODO 同学们注意：四天后，服务器修改好后，再公布这个地址给同学们
        final String URL = "http://v2.ffu365.com/index.php?m=Api&c=Util&a=sendVerifyCode";

        // 3. 构建一个请求  post 提交里面是参数的builder   url()请求路径
        Request request = new Request.Builder().url(URL)
                .post(builder.build()).build();

        // 4.发送一个请求
        okHttpClient.newCall(request).enqueue(new Callback() {// 请求的回调
            @Override
            public void onFailure(Call call, IOException e) {
                // 失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {  // 这个不是运行在主线程中
                // 成功  数据在response里面  获取后台给我们的JSON 字符串
                String result = response.body().string();
                Gson gson = new Gson();
                // 服务器的数据 映射成 Bean
                UserRequestCodeResult codeResult = gson.fromJson(result, UserRequestCodeResult.class);

                // 更新UI
                dealCodeResult(codeResult);
            }
        });

    }

    private void dealCodeResult(final UserRequestCodeResult codeResult) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (codeResult.errcode == 1) {
                        btVer.aginAfterTime(60); // 再让用户等待下，计数 60秒
                    } else {
                        Toast.makeText(UserRegisterActivity.this, "验证码是：" + codeResult.errmsg, Toast.LENGTH_SHORT).show();
                        btVer.setNoraml(); // 恢复验证码按钮操作
                    }
                }
            });
    }

    private void dealUserRegister() {
        // 终端验证 验证OK，   再请求服务器
        String userPhone = rePhone.getText().toString().trim();
        String password = rePassword.getText().toString().trim();
        String userCode = reCorner.getText().toString().trim();

        if(TextUtils.isEmpty(userPhone)){
            Toast.makeText(this,"请输入手机号",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"请输入验证码",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(userCode)){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_LONG).show();
            return;
        }
        requestServiceLogin(userPhone,password,userCode);
    }

    private void requestServiceLogin(String userPhone, String password, String userCode) {


        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody build = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("appid", "1")
                .addFormDataPart("cell_phone", userPhone)
                .addFormDataPart("verify_code", userCode)
                .addFormDataPart("password", MD5Util.strToMd5(password))
                .build();
        Request request = new Request.Builder()
                .url("http://v2.ffu365.com/index.php?m=Api&c=Member&a=register")
                .post(build)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG,"失败");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();
                // 服务器的数据 映射成 Bean
                Gson gson = new Gson();
                final UserLoginResult userLoginResult = gson.fromJson(result, UserLoginResult.class);
                // 显示数据 （异步的）
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dealRegisterResult(userLoginResult);
                    }
                });
            }
        });
    }

    // 注册成功，更新UI
    private void dealRegisterResult(UserLoginResult loginResult) {
        // 首先判断有没有成功
        if(loginResult.getErrcode() == 1){
            // 成功处理
            // 1.需要保存登录状态   当前设置为已登录
            SharedPreferences sp =  getSharedPreferences("info",MODE_PRIVATE);
            sp.edit().putBoolean("is_login",true).commit();

            // 2.需要保存用户信息
            UserLoginResult.DataBean userData =  loginResult.getData();
            // SharedPreferences 怎么保存对象   把对象转为JSON String --> SharedPreferences
            Gson gson = new Gson();
            String uesrInfoStr =  gson.toJson(userData);
            // 保存的用户信息为Json格式的字符串
            sp.edit().putString("user_info",uesrInfoStr).commit();

            // 3.关掉这个页面  关闭登录界面
            // 模拟注册成功，先关闭当前页面，然后关闭登录页面
            ActivityManagerUtil.getInstance().finishActivity(this);
            // 调用自己的activity管理类关闭其他页面
            ActivityManagerUtil.getInstance().finishActivity(ActivityLogin.class);
        }else{
            // 登录失败
            Toast.makeText(this,loginResult.getErrmsg(),Toast.LENGTH_LONG).show();
        }
    }
}
