package com.example.jingdong.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingdong.R;
import com.example.jingdong.bean.GoodsListBean;
import com.example.jingdong.ui.base.BaseActivity;
import com.example.jingdong.util.GlideImageLoader;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.youth.banner.Banner;

import java.util.Arrays;

public class ListDetailsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private ImageView ivShare;
    private Banner banner_detail;
    private TextView tv_goodsTitle;
    private TextView tv_goodsPrice;
    private TextView tv_VipPrice;
    /**
     * 购物车
     */
    private TextView tv_ShopCar;
    /**
     * 加入购物车
     */
    private TextView tv_AddCar;
    private GoodsListBean.DataBean dataBean;

    @Override
    public int getContentLayout() {
        return R.layout.activity_list_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获得传过来的值
        Intent intent = getIntent();
        dataBean = (GoodsListBean.DataBean) intent.getSerializableExtra("bean");
        initView();
        //设置数据
        setData();
        //设置监听
        setListener();
    }

    //初始化控件
    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        ivShare = (ImageView) findViewById(R.id.ivShare);
        banner_detail = (Banner) findViewById(R.id.banner_detail);
        tv_goodsTitle = (TextView) findViewById(R.id.tv_goodsTitle);
        tv_goodsPrice = (TextView) findViewById(R.id.tv_goodsPrice);
        tv_VipPrice = (TextView) findViewById(R.id.tv_VipPrice);
        tv_ShopCar = (TextView) findViewById(R.id.tv_ShopCar);
        tv_ShopCar.setOnClickListener(this);
        tv_AddCar = (TextView) findViewById(R.id.tv_AddCar);
        tv_AddCar.setOnClickListener(this);
    }

    //注入对象
    @Override
    public void inject() {

    }

    //设置数据
    private void setData() {
        if (dataBean == null) {
            return;
        }
        //设置数据
        banner_detail.setImageLoader(new GlideImageLoader());
        String[] split = dataBean.getImages().split("\\|");
        banner_detail.setImages(Arrays.asList(split));
        banner_detail.start();
        tv_goodsTitle.setText(dataBean.getTitle());
        tv_goodsPrice.setText("原价:" + dataBean.getSalenum());
        tv_goodsPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        tv_VipPrice.setText("现价:" + dataBean.getPrice());
    }

    //设置监听
    private void setListener() {
        //返回
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //点击按钮分享
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UMWeb web = new UMWeb(dataBean.getDetailUrl());

                new ShareAction(ListDetailsActivity.this).withMedia(web).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(shareListener).open();
            }
        });
    }

    //分享的回调
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //结束轮播
    @Override
    protected void onStop() {
        super.onStop();
        banner_detail.stopAutoPlay();
    }

    //点击
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ShopCar:
                //跳转到购物车页面
//                Intent intent = new Intent(ListDetailsActivity.this, ShopCarActivity.class);
//                startActivity(intent);
                break;
            case R.id.tv_AddCar:
//                addCar();
                break;
        }
    }

    //友盟分享的监听
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(ListDetailsActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ListDetailsActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ListDetailsActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };
}
