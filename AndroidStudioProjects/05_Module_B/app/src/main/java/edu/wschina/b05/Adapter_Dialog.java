package edu.wschina.b05;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class Adapter_Dialog extends RecyclerView.Adapter<Adapter_Dialog.MyDialogs> {

    List<ShoppingCart> listShop;
    TextView money;
    Context context;
    CartProvider cartProvider;
    private SharedPreferences.Editor edit;
    private Bitmap bitmap;

    public Adapter_Dialog(Context context,List<ShoppingCart> listShop){
        this.context = context;
        this.listShop = listShop;
    }
    public Adapter_Dialog(Context context, TextView money, List<ShoppingCart> listShop) {
        this.listShop = listShop;
        this.money = money;
        this.context = context;
    }

    @NonNull
    @Override
    public MyDialogs onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_adapter_items,
                null,false);
        cartProvider = new CartProvider(parent.getContext());
        SharedPreferences cart_json = parent.getContext().getSharedPreferences("Why", 0);
        edit = cart_json.edit();
        return new MyDialogs(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDialogs holder, int position) {
        ShoppingCart shoppingCart = listShop.get(position);
        holder.moneys.setText("￥" + shoppingCart.getPrice());
        holder.caipin.setText(shoppingCart.getName());
        holder.numberAddSub.setValue(shoppingCart.getCount());
        holder.numberAddSub.getValue();
        new MyAsyncTask(holder.head).execute(shoppingCart.getImage_url());
        AddTotalPrice();
        holder.numberAddSub.setOnButtonClickListener(new NumberAddSub.ButtonClickListener() {
            @Override
            public void onAddClick(int value) {
                shoppingCart.setCount(value);
                AddTotalPrice();
                cartProvider.update(shoppingCart);
                edit.putInt("Count",shoppingCart.getCount());
                edit.commit();

            }

            @Override
            public void onSubClick(int value) {
                shoppingCart.setCount(value);
                AddTotalPrice();
                cartProvider.update(shoppingCart);
                edit.putInt("Count",shoppingCart.getCount());
                edit.commit();
                if (shoppingCart.getCount() == 0){
                    cartProvider.delete(shoppingCart);
                }
            }

        });
    }

    class MyAsyncTask extends AsyncTask<String,Void,Bitmap>{
        private ImageView head;

        public MyAsyncTask(ImageView head) {
            this.head = head;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            head.setImageBitmap(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                int responseCode = conn.getResponseCode();
                if (responseCode == 200){
                    InputStream inputStream = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

    private void AddTotalPrice(){
        int sum = 0;
        if (listShop != null){
            for (ShoppingCart shoppingCart : listShop) {
                sum += shoppingCart.getPrice() * shoppingCart.getCount();
                if (money != null){
                    money.setText("总价 ："+sum);
                }
            }
        }
    }


    public void clearData(){
        listShop.clear();
        notifyItemRangeRemoved(0,listShop.size());
    }

    @Override
    public int getItemCount() {
        return listShop.size();
    }

    class MyDialogs extends RecyclerView.ViewHolder{
        private ImageView head;
        private TextView caipin;
        private TextView moneys;
        private NumberAddSub numberAddSub;
        public MyDialogs(@NonNull View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.img_style);
            caipin = itemView.findViewById(R.id.txt_pin);
            moneys = itemView.findViewById(R.id.txt_qian);
            numberAddSub = itemView.findViewById(R.id.sub_add);
        }
    }
}
