package edu.wschina.b05;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Adapter_OrdePage extends RecyclerView.Adapter<Adapter_OrdePage.MyOrdePage> {
    List<MenuBean> menuBeanList;
    Context context;
    CartProvider cartProvider;
    TextView countMoney;
    private Bitmap bitmap;

    public Adapter_OrdePage(Context context, List<MenuBean> menuBeanList, TextView countMoney) {
        this.menuBeanList = menuBeanList;
        this.context = context;
        this.countMoney = countMoney;
    }


    @NonNull
    @Override
    public MyOrdePage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_items,
                null,false);
        cartProvider = new CartProvider(context);
        return new MyOrdePage(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyOrdePage holder, int position) {
        MenuBean menuBean = menuBeanList.get(position);
        holder.caipin.setText(menuBean.getName()+"");
        holder.money.setText(menuBean.getPrice()+"");
        new MyTask(holder.head).execute(menuBean.getImage_url());

        holder.numberAddSub.setOnButtonClickListener(new NumberAddSub.ButtonClickListener() {
            @Override
            public void onAddClick(int value) {
                menuBean.setCountx(value);
                if (value >0){
                    cartProvider.put(converDate(menuBean));
                    int sum = AddTotalPrice();
                    countMoney.setText("¥"+ sum);
                }

            }

            @Override
            public void onSubClick(int value) {
                if (value == 0){
                    menuBean.setCountx(value);
                    int sum = AddTotalPrice();
                    countMoney.setText("¥"+ sum);
                    cartProvider.delete(converDate(menuBean));
                }else {
                    menuBean.setCountx(value);
                    int sum = AddTotalPrice();
                    countMoney.setText("¥"+ sum);
                    cartProvider.subput(converDate(menuBean));
                }
            }
        });
    }

    class MyTask extends AsyncTask<String,Void,Bitmap>{
        private ImageView head;

        public MyTask(ImageView head) {
            this.head = head;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                //1.创建URL
                URL url = new URL(strings[0]);
                //2.打开连接
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //4.判断响应码
                if (urlConnection.getResponseCode() == 200) {
                    //5.获取数据
                    InputStream inputStream = urlConnection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            head.setImageBitmap(bitmap);
        }
    }


    private int AddTotalPrice(){
        int sum = 0;
        if (menuBeanList != null){
            for (MenuBean menuBean : menuBeanList) {
                sum += menuBean.getPrice() * menuBean.getCountx();
            }
        }
        return sum;
    }




    private ShoppingCart converDate(MenuBean menuBean) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(menuBean.getId());
        shoppingCart.setImage_url(menuBean.getImage_url());
        shoppingCart.setName(menuBean.getName());
        shoppingCart.setPrice(menuBean.getPrice());
        return shoppingCart;
    }



    @Override
    public int getItemCount() {
        return menuBeanList.size();
    }

    class MyOrdePage extends RecyclerView.ViewHolder{
        public ImageView head;
        private TextView caipin;
        private TextView money;
        private NumberAddSub numberAddSub;
        public MyOrdePage(@NonNull View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.img_head);
            caipin = itemView.findViewById(R.id.txt_cai);
            money = itemView.findViewById(R.id.txt_money);
            numberAddSub = itemView.findViewById(R.id.num_view);
        }
    }
}






