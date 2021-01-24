package com.play.anticorrosiveapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.play.anticorrosiveapp.DetailLinkActivity;
import com.play.anticorrosiveapp.R;
import com.play.anticorrosiveapp.adapter.ListBaseAdapter;
import com.play.anticorrosiveapp.bean.BaseMsgBean;
import com.play.anticorrosiveapp.bean.DataBean;
import com.play.anticorrosiveapp.custom.ImplantListView;
import com.play.anticorrosiveapp.okhttputils.LoadingCallBack;
import com.play.anticorrosiveapp.okhttputils.OkHttpHelper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class FragmentHome extends BaseFragment implements View.OnClickListener {

    private ImageView head_image,center_image;
    private ImplantListView listBody;
    private String url = "http://v2.ffu365.com/index.php?m=Api&c=Index&a=home&appid=1";
    private BaseMsgBean<DataBean> dataBeanBaseMsgBean;
    private OkHttpHelper okHttp;
    private Context context;
    private View view;

    @Override
    protected void initView(View view) {
        this.view = view;
        this.context = getActivity();
        head_image = view.findViewById(R.id.head_viewpageimage);
        center_image = view.findViewById(R.id.view_viewpageimage);
        listBody = view.findViewById(R.id.text_list);
    }

    @Override
    protected void initListener() {
        head_image.setOnClickListener(this);
        center_image.setOnClickListener(this);
    }

    @Override
    protected int ResourceLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void RequestNetworkDataStart() {
        okHttp = OkHttpHelper.getmOkHttp();

        okHttp.get(url, new LoadingCallBack<BaseMsgBean<DataBean>>(context){
            @Override
            //无法访问服务器或者是网络错误都会调用该类
            public void onFailure(Call call, IOException e) {
                //网络安全策略不允许与v2.ffu365.com进行明文通信
                Log.e("TAG",e.getMessage());
            }

            @Override
            //访问成功并且是200返回码。
            public void onResponseSuccess(Response response, BaseMsgBean<DataBean> dataBeanBaseMsgBean) {
                showRequestData(dataBeanBaseMsgBean);
            }

            @Override
            //访问服务器成功但是不是200，其他的一些服务器资源错误，或者服务关闭之类就会调用该类
            public void onResponseError(Response response, int code, Exception e) {

            }
        });
    }

    @Override
    protected void RequestNetworkStop() {
        if (okHttp != null){
            okHttp = null;
            dataBeanBaseMsgBean = null;
        }

    }


    private void showRequestData(BaseMsgBean<DataBean> dataBeanBaseMsgBean) {
        this.dataBeanBaseMsgBean =dataBeanBaseMsgBean;
        if (dataBeanBaseMsgBean.getErrmsg().equals("操作成功")){
            //获取两张网络图片
            String HeadImageUrl = dataBeanBaseMsgBean.getData().getAd_list().get(0).getImage();
            Glide.with(context).load(HeadImageUrl).into(head_image);
            String CenterImageUrl = dataBeanBaseMsgBean.getData().getCompany_list().get(0).getImage();
            Glide.with(context).load(CenterImageUrl).into(center_image);
            //接下来是获取listview列表的对用信息
            listBody.setAdapter(new ListBaseAdapter(context,dataBeanBaseMsgBean.getData().getNews_list()));
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, DetailLinkActivity.class);
        switch (v.getId()){
            case R.id.head_viewpageimage:
                String Ad_list_link = dataBeanBaseMsgBean.getData().getAd_list().get(0).getLink();
                intent.putExtra("AD_LINK",Ad_list_link);
                startActivity(intent);
                break;
            case R.id.view_viewpageimage:
                String Company_list_link = dataBeanBaseMsgBean.getData().getCompany_list().get(0).getLink();
                intent = new Intent(context, DetailLinkActivity.class);
                intent.putExtra("COMPANY_LINK",Company_list_link);
                startActivity(intent);
                break;
        }
    }
}
