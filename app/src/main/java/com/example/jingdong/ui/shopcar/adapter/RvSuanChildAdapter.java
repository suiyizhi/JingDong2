package com.example.jingdong.ui.shopcar.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jingdong.R;
import com.example.jingdong.bean.ShopCarBean;

import java.util.List;

public class RvSuanChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ShopCarBean.DataBean.ListBean> list;
    private Context context;
    private final LayoutInflater inflater;

    public RvSuanChildAdapter(List<ShopCarBean.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.rvorderchild_item, parent, false);
        OrderChildViewHolder orderChildViewHolder=new OrderChildViewHolder(view);
        return orderChildViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OrderChildViewHolder orderChildViewHolder= (OrderChildViewHolder) holder;
        Glide.with(context).load(list.get(position).getImages().split("\\|")[0]).into(orderChildViewHolder.img_orderchild);
        orderChildViewHolder.tv_title.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class OrderChildViewHolder extends RecyclerView.ViewHolder{
        ImageView img_orderchild;
        TextView tv_title;

        public OrderChildViewHolder(View view) {
            super(view);
            this.img_orderchild = (ImageView) view.findViewById(R.id.img_orderchild);
            this.tv_title = (TextView) view.findViewById(R.id.tv_title);
        }
    }
}
