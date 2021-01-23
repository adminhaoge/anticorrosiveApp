package com.play.anticorrosiveapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.play.anticorrosiveapp.R;
import com.play.anticorrosiveapp.bean.DataBean;

import java.util.List;

public class ListBaseAdapter extends BaseAdapter {
    private Context context;
    private List<DataBean.NewsListBean> news_list;
    private View view;
    private TextView right_data,left_data;

    public ListBaseAdapter(Context context, List<DataBean.NewsListBean> news_list) {
        this.context = context;
        this.news_list = news_list;
    }

    @Override
    public int getCount() {
        return news_list.size();
    }

    @Override
    public Object getItem(int position) {
        DataBean.NewsListBean newsListBean = news_list.get(position);
        return newsListBean;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_list_data, parent, false);
        initView();
        initShowData(position);
        return view;
    }

    private void initShowData(int position) {
        left_data.setText(news_list.get(position).getTitle());
        right_data.setText(news_list.get(position).getCreate_time());
    }

    private void initView() {
        left_data = view.findViewById(R.id.left_body_data);
        right_data = view.findViewById(R.id.right_body_data);
    }

}
