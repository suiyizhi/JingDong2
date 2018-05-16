package com.example.jingdong.ui.homepage.adapter;

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
import com.example.jingdong.bean.CatagoryBean;

import java.util.List;

public class RvClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<CatagoryBean.DataBean> list;
    private Context context;
    private final LayoutInflater layoutInflater;
    private RvClassViewHolder rvClassViewHolder;

    public RvClassAdapter(List<CatagoryBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.rv_class_item, parent, false);
        rvClassViewHolder = new RvClassViewHolder(view);
        return rvClassViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        rvClassViewHolder= (RvClassViewHolder) holder;
        rvClassViewHolder.tv_rvclass.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getIcon()).into(rvClassViewHolder.img_rvclass);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class RvClassViewHolder extends RecyclerView.ViewHolder{

        private final ImageView img_rvclass;
        private final TextView tv_rvclass;

        public RvClassViewHolder(View itemView) {
            super(itemView);
            img_rvclass = itemView.findViewById(R.id.img_rvclass);
            tv_rvclass = itemView.findViewById(R.id.tv_rvclass);
        }
    }

}
