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
import com.example.jingdong.bean.GoodsListBean;

import java.util.List;

public class GoodsListRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GoodsListBean.DataBean> list;
    private Context context;
    private final LayoutInflater inflater;

    public GoodsListRvAdapter(List<GoodsListBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private OnListItemClickListener onListItemClickListener;
    public interface OnListItemClickListener {
        void OnItemClick(GoodsListBean.DataBean dataBean);
    }
    //设置点击事件
    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener){
        this.onListItemClickListener=onListItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.goodslistrv_item, parent, false);
        GoodsViewHolder goodsViewHolder=new GoodsViewHolder(view);
        return goodsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        GoodsViewHolder goodsViewHolder= (GoodsViewHolder) holder;
        goodsViewHolder.tv_title.setText(list.get(position).getTitle());
        goodsViewHolder.tv_price.setText("￥"+list.get(position).getPrice()+"");
        Glide.with(context).load(list.get(position).getImages().split("\\|")[0]).into(goodsViewHolder.img_goods);

        //设置点击事件
        goodsViewHolder.goodslistrv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListItemClickListener.OnItemClick(list.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder{
        ImageView img_goods;
        TextView tv_title;
        TextView tv_price;
        LinearLayout goodslistrv_item;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            goodslistrv_item = itemView.findViewById(R.id.goodslistrv_item);
            this.img_goods = (ImageView) itemView.findViewById(R.id.img_goods);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_price = (TextView) itemView.findViewById(R.id.tv_price);
        }
    }
}
