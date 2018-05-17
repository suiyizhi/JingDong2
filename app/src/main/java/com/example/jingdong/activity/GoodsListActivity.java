package com.example.jingdong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.jingdong.R;
import com.example.jingdong.bean.GoodsListBean;
import com.example.jingdong.component.DaggerHttpComponent;
import com.example.jingdong.module.HttpModule;
import com.example.jingdong.ui.base.BaseActivity;
import com.example.jingdong.ui.classify.adapter.GoodsListRvAdapter;
import com.example.jingdong.ui.classify.contract.GoodsListContract;
import com.example.jingdong.ui.classify.presenter.GoodsListPresenter;

import java.util.List;

public class GoodsListActivity extends BaseActivity<GoodsListPresenter> implements GoodsListContract.View {

    private ImageView img_listback;
    private RecyclerView rv_goodslist;

    @Override
    public int getContentLayout() {
        return R.layout.activity_goods_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获得传来的值
        Intent intent = getIntent();
        int pscid = intent.getIntExtra("pscid", 0);
        initView();
        mPresenter.getGoodsList(pscid+"");
    }

    private void initView() {
        img_listback = (ImageView) findViewById(R.id.img_listback);
        rv_goodslist = (RecyclerView) findViewById(R.id.rv_goodslist);
        rv_goodslist.setLayoutManager(new LinearLayoutManager(this));
        //设置点击回退监听
        img_listback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .httpModule(new HttpModule())
                .build()
                .inject(this);
    }

    @Override
    public void showGoodsList(GoodsListBean goodsListBean) {
        List<GoodsListBean.DataBean> data = goodsListBean.getData();
        GoodsListRvAdapter goodsListRvAdapter = new GoodsListRvAdapter(data, this);
        rv_goodslist.setAdapter(goodsListRvAdapter);
        //设置点击事件
        goodsListRvAdapter.setOnListItemClickListener(new GoodsListRvAdapter.OnListItemClickListener() {
            @Override
            public void OnItemClick(GoodsListBean.DataBean dataBean) {
                Intent intent = new Intent(GoodsListActivity.this, ListDetailsActivity.class);
                intent.putExtra("bean",dataBean);
                startActivity(intent);
            }
        });
    }


}
