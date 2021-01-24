package com.example.imitationtaobao.bean;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.example.imitationtaobao.utils.ACache;
import com.example.imitationtaobao.utils.Constant;
import com.example.imitationtaobao.utils.PreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CartProvider {
    private SparseArray<ShoppingCart> dates = null;
    private Context context;
    private Gson gson = new Gson();

    private static CartProvider cartProvider;

    public static CartProvider getInstance(Context context){
        if (cartProvider == null){
            synchronized (CartProvider.class){
                if (cartProvider == null){
                    cartProvider = new CartProvider(context);
                }
            }
        }
        return cartProvider;
    }



    private CartProvider(Context context) {
        dates = new SparseArray<>(10);
        this.context = context;
        listToSparse();
    }

    private void put(ShoppingCart cart){
        ShoppingCart temp = dates.get(cart.getId());
        if (temp != null){
            temp.setCount(temp.getCount()+1);
        }else {
            temp =cart ;
            temp.setCount(1);
        }

        dates.put(cart.getId(),temp);
        commit();
    }

    public void put(WaresHot waresHot){
        ShoppingCart shoppingCart = convertDate(waresHot);
        put(shoppingCart);
    }

    public void put(ListBean listBean){
        ShoppingCart shoppingCart = convertDate(listBean);
        put(shoppingCart);
    }

        private ShoppingCart convertDate(WaresHot waresHot){
        ShoppingCart cart = new ShoppingCart();
        cart.setId(waresHot.getId());
        cart.setImgUrl(waresHot.getImgUrl());
        cart.setName(waresHot.getName());
        cart.setPrice(waresHot.getPrice());
        return cart;
    }


    private ShoppingCart convertDate(ListBean listBean){
        ShoppingCart cart = new ShoppingCart();
        cart.setId(listBean.getId());
        cart.setImgUrl(listBean.getImgUrl());
        cart.setName(listBean.getName());
        cart.setPrice(listBean.getPrice());
        return cart;
    }

    public void update(ShoppingCart cart){
        dates.put(cart.getId(),cart);
        commit();
    }

    public void delete(ShoppingCart cart){
        dates.delete(cart.getId());
        commit();
    }

    public void commit(){
        List<ShoppingCart> carts = sparesToList();
        PreferencesUtils.putString(context,Constant.CART_JSON,gson.toJson(carts));
    }


    public List<ShoppingCart> sparesToList(){
        int size = dates.size();
        List<ShoppingCart> list = new ArrayList<>(size);
        for (int i=0; i<size;i++){
            list.add(dates.valueAt(i));
        }
        return list;
    }

    public List<ShoppingCart> getAll(){

        return getDateFromLocal();
    }

    private void listToSparse(){
        List<ShoppingCart> carts = getDateFromLocal();
        if (carts != null && carts.size()>0){
            for (ShoppingCart cart : carts) {
                dates.put(cart.getId(),cart);
            }
        }

    }
    public List<ShoppingCart> getDateFromLocal(){
        List<ShoppingCart> carts =null;
            String json = PreferencesUtils.getString(context, Constant.CART_JSON);
            if (json != null){
                carts = gson.fromJson(json,new TypeToken<List<ShoppingCart>>(){}.getType());
        }
        return carts;
    }

}
