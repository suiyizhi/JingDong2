package com.example.jingdong.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingdong.R;
import com.example.jingdong.bean.AdBean;
import com.example.jingdong.bean.BaseBean;
import com.example.jingdong.bean.GoodsListBean;
import com.example.jingdong.component.DaggerHttpComponent;
import com.example.jingdong.ui.base.BaseActivity;
import com.example.jingdong.ui.classify.contract.AddCarContract;
import com.example.jingdong.ui.classify.presenter.AddCarPresenter;
import com.example.jingdong.util.GlideImageLoader;
import com.example.jingdong.util.SpUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.Arrays;

public class ListDetailsActivity extends BaseActivity<AddCarPresenter> implements View.OnClickListener,AddCarContract.View {

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
    private String images;
    private String title;
    private int salenum;
    private double price;
    private String detailUrl;
    private int pid;


    @Override
    public int getContentLayout() {
        return R.layout.activity_list_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获得传过来的值
        Intent intent = getIntent();
        int flag = intent.getIntExtra("flag", 0);
        if (flag==1000){
            AdBean.TuijianBean.ListBean dataBean = (AdBean.TuijianBean.ListBean) intent.getSerializableExtra("bean");
            images = dataBean.getImages();
            title = dataBean.getTitle();
            salenum = dataBean.getSalenum();
            price = dataBean.getPrice();
            detailUrl = dataBean.getDetailUrl();
            pid = dataBean.getPid();
        }else {
            GoodsListBean.DataBean dataBean = (GoodsListBean.DataBean) intent.getSerializableExtra("bean");
            images = dataBean.getImages();
            title = dataBean.getTitle();
            salenum = dataBean.getSalenum();
            price = dataBean.getPrice();
            detailUrl = dataBean.getDetailUrl();
            pid = dataBean.getPid();
        }


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
        DaggerHttpComponent.builder()
                .build()
                .inject(this);
    }

    //设置数据
    private void setData() {

        //设置数据
        banner_detail.setImageLoader(new GlideImageLoader());
        String[] split = images.split("\\|");
        banner_detail.setImages(Arrays.asList(split));
        banner_detail.start();
        tv_goodsTitle.setText(title);
        tv_goodsPrice.setText("原价:" + salenum);
        tv_goodsPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        tv_VipPrice.setText("现价:" + price);
        //banner图片点击的轮播
        banner_detail.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(ListDetailsActivity.this, BannerDetailsActivity.class);
                intent.putExtra("images",images);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
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

                UMWeb web = new UMWeb(detailUrl);

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
//        banner_detail.stopAutoPlay();
    }

    //点击
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ShopCar:
                //跳转到购物车页面
                Intent intent = new Intent(ListDetailsActivity.this, ShopCarActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_AddCar:
                addCar();
                break;
        }
    }

    //添加购物车
    private void addCar() {
        String uid = SpUtil.getString(ListDetailsActivity.this, "uid", "-1");
        if ("-1".equals(uid)){
            //没登录跳转到登录页面
            Intent intent = new Intent(ListDetailsActivity.this, LoginActivity.class);
            startActivity(intent);
        }else {
            //已经登录加购购物车
            String token = SpUtil.getString(ListDetailsActivity.this, "token", "");
            //添加购物车
            mPresenter.addCar(uid,pid+"",token);
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

    @Override
    public void addSuccess(BaseBean baseBean) {
        Toast.makeText(this, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
    }
}
