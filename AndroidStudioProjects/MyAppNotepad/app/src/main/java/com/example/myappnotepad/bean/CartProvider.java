package com.example.myappnotepad.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;

import com.example.myappnotepad.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartProvider {
    private SparseArray<TitleBean> dates;
    private Context context;
    private Gson gson = new Gson();
    private final SharedPreferences why;

    public CartProvider(Context context) {
        dates = new SparseArray<>(10);
        this.context = context;
        why = context.getSharedPreferences("why", Context.MODE_PRIVATE);
        listToSparse();
    }

    public void put(TitleBean cart){
        TitleBean temp = dates.get(cart.getId());
        if (temp != null){
            temp.setTitle(temp.getTitle());
        }else {
            temp =cart ;
        }

        dates.put(cart.getId(),temp);
        commit();
    }

    public void update(TitleBean cart){
        dates.put(cart.getId(),cart);
        commit();
    }

    public void delete(TitleBean cart){
        dates.delete(cart.getId());
        commit();
    }

    public void commit(){
        List<TitleBean> carts = sparesToList();
        SharedPreferences.Editor edit = why.edit();
        JSONArray jsonArray = new JSONArray();
        JSONObject object = null;
        for (int i = 0; i < carts.size(); i++) {
            object = new JSONObject();
            try {
                object.put("title",carts.get(i).getTitle());
                object.put("id",carts.get(i).getId());
                object.put("count",carts.get(i).getContent());
                jsonArray.put(object);
                object = null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String json = jsonArray.toString();
        edit.putString("CART_JSON",json);
        edit.commit();
    }


    public List<TitleBean> sparesToList(){
        int size = dates.size();
        List<TitleBean> list = new ArrayList<>(size);
        for (int i=0; i<size;i++){
            list.add(dates.valueAt(i));
        }
        return list;

    }

    public List<TitleBean> getAll(){

        return getDateFromLocal();
    }

    private void listToSparse(){
        List<TitleBean> carts = getDateFromLocal();
        if (carts != null && carts.size()>0){
            for (TitleBean cart : carts) {
                dates.put(cart.getId(),cart);
            }
        }

    }
    public List<TitleBean> getDateFromLocal(){
        List<TitleBean> carts = new ArrayList<>();
            String json = why.getString("CART_JSON",null);
            if (json != null){
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        TitleBean titleBean = new TitleBean();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        titleBean.setId(jsonObject.getInt("id"));
                        titleBean.setContent(jsonObject.getString("count"));
                        titleBean.setTitle(jsonObject.getString("title"));
                        carts.add(titleBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        return carts;
    }

}
