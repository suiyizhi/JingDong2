package com.example.jingdong.ui.classify.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jingdong.R;
import com.example.jingdong.bean.CatagoryBean;
import com.example.jingdong.inter.OnItemClickListener;


import java.util.List;

public class RvLeftAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private List<CatagoryBean.DataBean> list;
    private Context context;
    private LayoutInflater inflater;

    public RvLeftAdapter(List<CatagoryBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rvleft_item, parent, false);
        RvLeftViewHolder rvLeftViewHolder=new RvLeftViewHolder(view);
        return rvLeftViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        RvLeftViewHolder rvLeftViewHolder= (RvLeftViewHolder) holder;
        rvLeftViewHolder.tv_left.setText(list.get(position).getName());

        if (list.get(position).isChecked()){
            rvLeftViewHolder.tv_left.setTextColor(Color.RED);
            rvLeftViewHolder.tv_left.setBackgroundColor(Color.parseColor("#F3F5F7"));
        }else {
            rvLeftViewHolder.tv_left.setTextColor(Color.BLACK);
            rvLeftViewHolder.tv_left.setBackgroundColor(Color.WHITE);
        }

        //点击的监听
        rvLeftViewHolder.tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RvLeftViewHolder extends RecyclerView.ViewHolder {
        TextView tv_left;
        public RvLeftViewHolder(View itemView) {
            super(itemView);
            tv_left=itemView.findViewById(R.id.tv_left);
        }
    }


    public void changeCheck(int postion,boolean bool){
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(false);
        }
        list.get(postion).setChecked(bool);
        notifyDataSetChanged();
    }
}
