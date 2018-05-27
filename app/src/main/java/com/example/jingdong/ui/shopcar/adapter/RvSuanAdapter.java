package com.example.jingdong.ui.shopcar.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jingdong.R;
import com.example.jingdong.bean.SellerBean;
import com.example.jingdong.bean.ShopCarBean;

import java.util.List;

public class RvSuanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SellerBean> glist;
    private List<List<ShopCarBean.DataBean.ListBean>> clist;
    private Context context;
    private final LayoutInflater inflater;

    public RvSuanAdapter(List<SellerBean> glist, List<List<ShopCarBean.DataBean.ListBean>> clist, Context context) {
        this.glist = glist;
        this.clist = clist;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rvorder_item, parent, false);
        ShopViewHolder shopViewHolder=new ShopViewHolder(view);
        return shopViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShopViewHolder shopViewHolder= (ShopViewHolder) holder;
        shopViewHolder.tv_seller.setText(glist.get(position).getSellerName());
        shopViewHolder.rv_order.setLayoutManager(new LinearLayoutManager(context));
        RvSuanChildAdapter rvSuanChildAdapter = new RvSuanChildAdapter(clist.get(position), context);
        shopViewHolder.rv_order.setAdapter(rvSuanChildAdapter);
    }

    @Override
    public int getItemCount() {
        return clist.size();
    }

    class ShopViewHolder extends RecyclerView.ViewHolder{
        TextView tv_seller;
        RecyclerView rv_order;

        public ShopViewHolder(View view) {
            super(view);
            this.tv_seller = (TextView) view.findViewById(R.id.tv_seller);
            this.rv_order = (RecyclerView) view.findViewById(R.id.rv_order);
        }
    }
}
