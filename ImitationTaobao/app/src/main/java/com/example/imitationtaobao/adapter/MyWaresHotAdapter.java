package com.example.imitationtaobao.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.imitationtaobao.R;
import com.example.imitationtaobao.activity.WareDetailActivity;
import com.example.imitationtaobao.bean.CartProvider;
import com.example.imitationtaobao.bean.WaresHot;
import com.example.imitationtaobao.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWaresHotAdapter extends RecyclerView.Adapter<MyWaresHotAdapter.WaresHotHolder> {

    private List<WaresHot> WaresList;

    private CartProvider cartProvider;

    public MyWaresHotAdapter(List<WaresHot> waresList) {
        WaresList = waresList;
    }

    @NonNull
    @Override
    public WaresHotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        cartProvider = CartProvider.getInstance(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_chart_cardview, parent, false);
        return new WaresHotHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaresHotHolder holder, int position) {
        WaresHot waresHot = WaresList.get(position);
        holder.title.setText(waresHot.getName());
        holder.itemMoney.setText(String.valueOf(waresHot.getPrice()));
        Glide.with(holder.itemView).load(waresHot.getImgUrl()).into(holder.img_header);
        holder.go_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartProvider.put(waresHot);
                Toast.makeText(v.getContext(), "已添加到购物车", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return WaresList.size();
    }

    public void clearData() {
        WaresList.clear();
        notifyItemRangeRemoved(0, WaresList.size());
    }

    public void addData(List<WaresHot> hot) {
        addData(0, hot);
    }

    public void addData(int position, List<WaresHot> hot) {
        if (hot != null && hot.size() > 0) {
            WaresList.addAll(hot);
            notifyItemRangeChanged(position, WaresList.size());
        }
    }

    class WaresHotHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.img_header)
        ImageView img_header;
        @BindView(R.id.item_money)
        TextView itemMoney;
        @BindView(R.id.bt_go_load)
        Button go_load;
        @BindView(R.id.title_text)
        TextView title;
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.cons_layout)
        ConstraintLayout consLayout;

        public WaresHotHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            consLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            WaresHot waresHot = WaresList.get(getLayoutPosition());
            Intent intent = new Intent(view.getContext(), WareDetailActivity.class);
            intent.putExtra(Constant.WARE,waresHot);
            view.getContext().startActivity(intent);
        }
    }

}
