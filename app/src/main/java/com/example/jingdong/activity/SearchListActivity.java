package com.example.jingdong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jingdong.R;
import com.example.jingdong.bean.GoodsListBean;
import com.example.jingdong.component.DaggerHttpComponent;
import com.example.jingdong.ui.base.BaseActivity;
import com.example.jingdong.ui.classify.adapter.GoodsListRvAdapter;
import com.example.jingdong.ui.homepage.contract.SearchContract;
import com.example.jingdong.ui.homepage.presenter.SearchPresenter;

import java.util.List;

public class SearchListActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {

    private ImageView img_searchback;
    /**
     * 请输入搜索内容
     */
    private TextView tv_keywords;
    private RecyclerView rv_goodslist;
    private String keyword;

    @Override
    public int getContentLayout() {
        return R.layout.activity_search_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取传过来的值
        Intent intent = getIntent();
        keyword = intent.getStringExtra("keyword");
        initView();
        //设置搜索栏的关键字
        tv_keywords.setText(keyword);
        //搜索
        mPresenter.search(keyword);
    }

    private void initView() {
        img_searchback = (ImageView) findViewById(R.id.img_searchback);
        //点击回退的监听
        img_searchback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_keywords = (TextView) findViewById(R.id.tv_keywords);
        rv_goodslist = (RecyclerView) findViewById(R.id.rv_goodslist);
        rv_goodslist.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .build()
                .inject(this);
    }

    @Override
    public void showSearch(GoodsListBean goodsListBean) {
        List<GoodsListBean.DataBean> data = goodsListBean.getData();
        GoodsListRvAdapter goodsListRvAdapter = new GoodsListRvAdapter(data, SearchListActivity.this);
        rv_goodslist.setAdapter(goodsListRvAdapter);
        //点击跳转到详情页的监听
        goodsListRvAdapter.setOnListItemClickListener(new GoodsListRvAdapter.OnListItemClickListener() {
            @Override
            public void OnItemClick(GoodsListBean.DataBean dataBean) {
                Intent intent = new Intent(SearchListActivity.this, ListDetailsActivity.class);
                intent.putExtra("bean",dataBean);
                startActivity(intent);
            }
        });
    }
}
