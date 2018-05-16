package com.example.jingdong.ui.classify;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.example.jingdong.R;
import com.example.jingdong.bean.CatagoryBean;
import com.example.jingdong.bean.ChildCataBean;
import com.example.jingdong.component.DaggerHttpComponent;
import com.example.jingdong.inter.OnItemClickListener;
import com.example.jingdong.module.HttpModule;
import com.example.jingdong.ui.base.BaseFragment;
import com.example.jingdong.ui.classify.adapter.ElvAdapter;
import com.example.jingdong.ui.classify.adapter.RvLeftAdapter;
import com.example.jingdong.ui.classify.contract.ClassifyContract;
import com.example.jingdong.ui.classify.presenter.ClassifyPresenter;

import java.util.ArrayList;
import java.util.List;

public class ClassifyFragment extends BaseFragment<ClassifyPresenter> implements ClassifyContract.View {

    private ImageView ivZxing;
    private RecyclerView rvLeft;
    private ExpandableListView elv;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_classify;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .httpModule(new HttpModule())
                .build()
                .inject(this);
    }

    @Override
    public void initView(View view) {
        ivZxing = (ImageView) view.findViewById(R.id.ivZxing);
        rvLeft = (RecyclerView) view.findViewById(R.id.rvLeft);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvLeft.setLayoutManager(linearLayoutManager);
        rvLeft.addItemDecoration(new DividerItemDecoration(getContext(),RecyclerView.VERTICAL));
        elv = (ExpandableListView) view.findViewById(R.id.elv);
        //显示左侧分类
        mPresenter.getCatagory();
    }

    //显示左侧分类
    @Override
    public void showCatagory(CatagoryBean catagoryBean) {
        final List<CatagoryBean.DataBean> dataBeans = catagoryBean.getData();
        final RvLeftAdapter rvLeftAdapter = new RvLeftAdapter(dataBeans, getContext());
        rvLeft.setAdapter(rvLeftAdapter);
        //默认选中第一个数据
        rvLeftAdapter.changeCheck(0,true);
        //默认显示第一个cid的子分类数据
        mPresenter.getChildCatagory(dataBeans.get(0).getCid()+"");
        //点击的监听
        rvLeftAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                rvLeftAdapter.changeCheck(position,true);
                int cid = dataBeans.get(position).getCid();
                mPresenter.getChildCatagory(cid+"");
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });
    }

    @Override
    public void showChildCatagory(ChildCataBean childCataBean) {
        //定义数组存放数据
        List<String> list_group = new ArrayList<>();
        ArrayList<List<ChildCataBean.DataBean.ListBean>> child_lists = new ArrayList<>();
        //添加分组的数据
        List<ChildCataBean.DataBean> data = childCataBean.getData();

        for (int i = 0; i < data.size(); i++) {
            list_group.add(data.get(i).getName());
            child_lists.add(data.get(i).getList());
        }
        //设置适配器
        ElvAdapter elvAdapter = new ElvAdapter(list_group, child_lists, getContext());
        elv.setAdapter(elvAdapter);
        //默认展开列表
        for (int i = 0; i < list_group.size(); i++) {
            elv.expandGroup(i);
        }
        //隐藏父布局图标
        elv.setGroupIndicator(null);
        //设置点击禁止打开关闭
        elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }


}
