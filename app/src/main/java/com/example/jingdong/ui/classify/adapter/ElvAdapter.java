package com.example.jingdong.ui.classify.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.jingdong.R;
import com.example.jingdong.activity.GoodsListActivity;
import com.example.jingdong.bean.ChildCataBean;
import com.example.jingdong.inter.OnItemClickListener;


import java.util.List;

public class ElvAdapter extends BaseExpandableListAdapter{

    private List<String> grouplist;
    private List<List<ChildCataBean.DataBean.ListBean>> childlist;
    private Context context;
    private final LayoutInflater inflater;

    public ElvAdapter(List<String> grouplist, List<List<ChildCataBean.DataBean.ListBean>> childlist, Context context) {
        this.grouplist = grouplist;
        this.childlist = childlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return grouplist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return grouplist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childlist.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView==null){
            groupViewHolder=new GroupViewHolder();
            convertView=inflater.inflate(R.layout.elv_group,null);
            groupViewHolder.tv_elv=convertView.findViewById(R.id.tv_elv);
            convertView.setTag(groupViewHolder);
        }else {
            groupViewHolder= (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.tv_elv.setText(grouplist.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.elv_child,null);
            childViewHolder=new ChildViewHolder();
            childViewHolder.rv_elv=convertView.findViewById(R.id.rv_elv);
            convertView.setTag(childViewHolder);
        }else {
            childViewHolder= (ChildViewHolder) convertView.getTag();
        }
        //得到数据
        List<ChildCataBean.DataBean.ListBean> listBeans = childlist.get(groupPosition);
        childViewHolder.rv_elv.setLayoutManager(new GridLayoutManager(context,3));
        //创建适配器
        ElvRvAdapter elvRvAdapter = new ElvRvAdapter(listBeans, context);
        childViewHolder.rv_elv.setAdapter(elvRvAdapter);

        //点击跳转的监听
        elvRvAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int pscid = childlist.get(groupPosition).get(position).getPscid();
                Intent intent = new Intent(context, GoodsListActivity.class);
                intent.putExtra("pscid",pscid);
                context.startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder{
        TextView tv_elv;
    }
    class ChildViewHolder{
        RecyclerView rv_elv;
    }
}
