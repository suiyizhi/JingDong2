package com.example.jingdong.ui.classify.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jingdong.R;
import com.example.jingdong.bean.ChildCataBean;
import com.example.jingdong.inter.OnItemClickListener;

import java.util.List;

public class ElvRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChildCataBean.DataBean.ListBean> list;
    private Context context;
    private final LayoutInflater inflater;

    private OnItemClickListener onItemClickListener;

    public ElvRvAdapter(List<ChildCataBean.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    //设置点击事件的方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.elv_rv_item, parent, false);
        ElvRvViewHolder elvRvViewHolder = new ElvRvViewHolder(view);
        return elvRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ElvRvViewHolder elvRvViewHolder= (ElvRvViewHolder) holder;
        elvRvViewHolder.tv_elvrv.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getIcon()).into(elvRvViewHolder.img_elvrv);
        //设置点击监听
        elvRvViewHolder.layout_elv_rv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ElvRvViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_elv_rv_item;
        ImageView img_elvrv;
        TextView tv_elvrv;

        public ElvRvViewHolder(View itemView) {
            super(itemView);
            layout_elv_rv_item = itemView.findViewById(R.id.layout_elv_rv_item);
            img_elvrv=itemView.findViewById(R.id.img_elvrv);
            tv_elvrv=itemView.findViewById(R.id.tv_elvrv);
        }
    }
}
