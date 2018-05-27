package com.example.jingdong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingdong.R;
import com.example.jingdong.bean.BaseBean;
import com.example.jingdong.bean.JieSuanBean;
import com.example.jingdong.bean.SellerBean;
import com.example.jingdong.bean.ShopCarBean;
import com.example.jingdong.component.DaggerHttpComponent;
import com.example.jingdong.ui.base.BaseActivity;
import com.example.jingdong.ui.mine.contract.UploadHeaderContract;
import com.example.jingdong.ui.shopcar.adapter.RvSuanAdapter;
import com.example.jingdong.ui.shopcar.contract.SubmitOrderContract;
import com.example.jingdong.ui.shopcar.presenter.SubmitOrderPresenter;
import com.example.jingdong.util.SpUtil;

import java.util.List;

public class ConfirmOrderActivity extends BaseActivity<SubmitOrderPresenter> implements SubmitOrderContract.View {

    /**
     * 地址
     */
    private TextView tv_address;
    private RecyclerView rv_shop;
    /**
     * 价格
     */
    private TextView tv_price;
    /**
     * 提交订单
     */
    private TextView tv_tijiao;
    private String sum;

    @Override
    public int getContentLayout() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        JieSuanBean jiesuanbean = (JieSuanBean) intent.getSerializableExtra("bean");
        initView();
        //设置数据
        List<SellerBean> glist = jiesuanbean.getgList();
        List<List<ShopCarBean.DataBean.ListBean>> clist = jiesuanbean.getcList();
        RvSuanAdapter rvSuanAdapter = new RvSuanAdapter(glist, clist, this);
        rv_shop.setAdapter(rvSuanAdapter);
        sum = jiesuanbean.getSum();
        //设置价格
        tv_price.setText("总计:￥"+sum);
    }

    private void initView() {
        tv_address = (TextView) findViewById(R.id.tv_address);
        rv_shop = (RecyclerView) findViewById(R.id.rv_shop);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_tijiao = (TextView) findViewById(R.id.tv_tijiao);
        rv_shop.setLayoutManager(new LinearLayoutManager(this));
        rv_shop.addItemDecoration(new DividerItemDecoration(this,RecyclerView.VERTICAL));
        //添加订单的监听
        tv_tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.submitOrder(SpUtil.getString(ConfirmOrderActivity.this,"uid",""),sum);
            }
        });
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .build()
                .inject(this);
    }


    @Override
    public void submitSuccess(BaseBean baseBean) {
        Toast.makeText(this, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
    }
}
