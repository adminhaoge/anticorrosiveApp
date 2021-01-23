package com.play.anticorrosiveapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.play.anticorrosiveapp.ActivityLogin;
import com.play.anticorrosiveapp.R;
import com.play.anticorrosiveapp.UserInfoActivity;
import com.play.anticorrosiveapp.bean.UserLoginResult;

public class FragmentCenter extends BaseFragment implements View.OnClickListener{

    private TextView loginText,user_name,user_location;
    private Context context;
    private LinearLayout user_logined_ll,user_logined_no;
    private SharedPreferences sp;
    private ImageView user_head;

    @Override
    protected void initView(View view) {
        this.context = getActivity();
        loginText = view.findViewById(R.id.login_text);
        user_logined_ll = view.findViewById(R.id.user_logined_ll);
        user_logined_no = view.findViewById(R.id.user_logined_no);
        user_head = view.findViewById(R.id.user_head_iv);
        user_name = view.findViewById(R.id.user_name_tv);
        user_location = view.findViewById(R.id.user_location_tv);

    }

    @Override
    protected void initListener() {
        loginText.setOnClickListener(this);
        user_logined_ll.setOnClickListener(this);
    }

    @Override
    protected int ResourceLayout() {
        return R.layout.fragment_center;
    }

    @Override
    protected void RequestNetworkDataStart() {
        sp = context.getSharedPreferences("info", 0);
        boolean is_login = sp.getBoolean("is_login", true);
        if (is_login){
            user_logined_no.setVisibility(View.GONE);
            user_logined_ll.setVisibility(View.VISIBLE);
            String user_info = sp.getString("user_info", null);
            if (!TextUtils.isEmpty(user_info)){
                Gson gson = new Gson();
                UserLoginResult.DataBean json = gson.fromJson(user_info, UserLoginResult.DataBean.class);
                Glide.with(context).load(json.getMember_info().getMember_avatar()).into(user_head);
                user_name.setText(json.getMember_info().getMember_name());
                user_location.setText(json.getMember_info().getMember_location_text());
            }else {
                user_logined_no.setVisibility(View.VISIBLE);
                user_logined_ll.setVisibility(View.GONE);
            }

        }else {
            user_logined_no.setVisibility(View.VISIBLE);
            user_logined_ll.setVisibility(View.GONE);
        }
    }

    @Override
    protected void RequestNetworkStop() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_text:
                startActivity(new Intent(context,ActivityLogin.class));
                break;
            case R.id.user_logined_ll:
                startActivity(new Intent(context, UserInfoActivity.class));
        }
    }

}
