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
import com.example.jingdong.bean.AdBean;
import com.example.jingdong.bean.GoodsListBean;
import com.example.jingdong.inter.OnItemClickListener;

import java.util.List;

public class RvRecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<AdBean.TuijianBean.ListBean> list;
    private LayoutInflater inflater;

    public RvRecommendAdapter(Context context, List<AdBean.TuijianBean.ListBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    private OnListItemClickListener onListItemClickListener;
    public interface OnListItemClickListener {
        void OnItemClick(AdBean.TuijianBean.ListBean listBean);
    }
    //设置点击事件
    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener){
        this.onListItemClickListener=onListItemClickListener;
    }

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rvrecommend_item, parent, false);
        RecommendViewHolder recommendViewHolder = new RecommendViewHolder(view);
        return recommendViewHolder;
    }


    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
        AdBean.TuijianBean.ListBean listBean = list.get(position);
        String images = listBean.getImages();
        String[] split = images.split("\\|");
        String url = split.length == 0 ? images : split[0];
        Glide.with(context).load(url).into(recommendViewHolder.iv);
        recommendViewHolder.tv.setText(listBean.getTitle());
        //点击跳转
        recommendViewHolder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListItemClickListener.OnItemClick(list.get(position));
            }
        });
    }


    public int getItemCount() {
        return list.size();
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv;
        private final TextView tv;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
