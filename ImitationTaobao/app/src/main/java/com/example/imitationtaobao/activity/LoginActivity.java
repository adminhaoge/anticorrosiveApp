package com.example.imitationtaobao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.imitationtaobao.R;
import com.example.imitationtaobao.customview.EditTextWithDelete;
import com.example.imitationtaobao.mToolbar;
import com.example.imitationtaobao.utils.ToastUtils;
import com.example.imitationtaobao.utils.ValidatorUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity {
    @BindView(R.id.yh_toolbar)
    mToolbar yhToolbar;
    @BindView(R.id.top_edit)
    EditTextWithDelete topEdit;
    @BindView(R.id.button_edit)
    EditTextWithDelete buttonEdit;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.bt_register)
    Button btRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initEditStyle();
    }

    private void initEditStyle() {
        topEdit.setDrawableLeft(R.drawable.ic_name);
        buttonEdit.setDrawableLeft(R.drawable.ic_password);
    }
    @OnClick(R.id.bt_login)
    public void login(View view){
        String phone  = topEdit.getEditText();
        if (TextUtils.isEmpty(phone)){
            ToastUtils.show(this,"请输入手机号码");
        }else if (!ValidatorUtils.isMobile(phone)){
            ToastUtils.show(this,"请输入符合格式的手机号");
        }
        String pwd = buttonEdit.getEditText();
        if (TextUtils.isEmpty(pwd)){
            ToastUtils.show(this,"请输入密码");
        }else if (!ValidatorUtils.isPassword(pwd)){
            ToastUtils.show(this,"密码中必须包含字母和数字");
        }
    }
}
