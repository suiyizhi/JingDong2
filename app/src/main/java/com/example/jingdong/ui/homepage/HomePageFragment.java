package com.example.jingdong.ui.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dash.zxinglibrary.activity.CaptureActivity;
import com.dash.zxinglibrary.activity.CodeUtils;
import com.example.jingdong.R;
import com.example.jingdong.activity.GoodsListActivity;
import com.example.jingdong.activity.ListDetailsActivity;
import com.example.jingdong.activity.MainActivity;
import com.example.jingdong.activity.SearchActivity;
import com.example.jingdong.activity.WebViewActivity;
import com.example.jingdong.bean.AdBean;
import com.example.jingdong.bean.CatagoryBean;
import com.example.jingdong.component.DaggerHttpComponent;
import com.example.jingdong.inter.OnItemClickListener;
import com.example.jingdong.module.HttpModule;
import com.example.jingdong.ui.base.BaseFragment;
import com.example.jingdong.ui.homepage.adapter.RvClassAdapter;
import com.example.jingdong.ui.homepage.adapter.RvRecommendAdapter;
import com.example.jingdong.ui.homepage.adapter.RvSecKillAdapter;
import com.example.jingdong.ui.homepage.contract.HomePageContract;
import com.example.jingdong.ui.homepage.presenter.HomePagePresenter;
import com.example.jingdong.util.GlideImageLoader;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends BaseFragment<HomePagePresenter> implements HomePageContract.View {

    private ImageView img_zxing;
    private TextView tv_inputSearch;
    private Banner ad_banner;
    private RecyclerView rvClass;
    private TextView tvJD;
    private LinearLayout llMore;
    private MarqueeView marqueeView;
    private RecyclerView rvSecKill;
    private RecyclerView rvRecommend;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_homepage;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .httpModule(new HttpModule())
                .build()
                .inject(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        ad_banner.stopAutoPlay();
    }

    @Override
    public void initView(View view) {
        img_zxing = view.findViewById(R.id.img_zxing);
        tv_inputSearch = view.findViewById(R.id.tv_inputSearch);
        //跳转到搜索页面
        tv_inputSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        //设置轮播
        ad_banner = view.findViewById(R.id.ad_banner);
        ad_banner.setImageLoader(new GlideImageLoader());
        //分类
        rvClass = view.findViewById(R.id.rvClass);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.HORIZONTAL, false);
        rvClass.setLayoutManager(gridLayoutManager);
        tvJD = view.findViewById(R.id.tvJD);
        llMore = view.findViewById(R.id.llMore);
        marqueeView = view.findViewById(R.id.marqueeView);
        //初始化marqueeView
        initMarqueeView();
        //秒杀
        rvSecKill = view.findViewById(R.id.rvSecKill);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false);
        rvSecKill.setLayoutManager(gridLayoutManager1);
        //推荐
        rvRecommend = view.findViewById(R.id.rvRecommend);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        rvRecommend.setLayoutManager(gridLayoutManager2);
        //设置zxing监听
        setListener();
        //请求数据
        initData();
    }

    private void initData() {
        mPresenter.getAd();
        mPresenter.getCatagory();
    }


    public void setListener(){
        //扫描二维码的监听
        img_zxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CaptureActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    //回调显示结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1 && data!=null){
            Bundle bundle = data.getExtras();
            if (bundle!=null){
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String string = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getContext(), "扫描内容是：" + string, Toast.LENGTH_SHORT).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getContext(), "扫描失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //显示banner
    @Override
    public void showAd(AdBean adBean) {
        List<AdBean.DataBean> data = adBean.getData();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            list.add(data.get(i).getIcon());
        }
        ad_banner.setImages(list);
        ad_banner.start();
        //显示秒杀
        showSeckill(adBean);
        //显示为您推荐
        showRecommend(adBean);
    }

    private void showRecommend(AdBean adBean) {
        AdBean.TuijianBean tuijian = adBean.getTuijian();
        final List<AdBean.TuijianBean.ListBean> list = tuijian.getList();
        RvRecommendAdapter rvRecommendAdapter = new RvRecommendAdapter(getContext(), list);
        rvRecommend.setNestedScrollingEnabled(false);
        rvRecommend.setAdapter(rvRecommendAdapter);
        //设置点击跳转监听
        /*rvRecommendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String detailUrl = list.get(position).getDetailUrl();
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url",detailUrl);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });*/

        rvRecommendAdapter.setOnListItemClickListener(new RvRecommendAdapter.OnListItemClickListener() {
            @Override
            public void OnItemClick(AdBean.TuijianBean.ListBean listBean) {
                Intent intent = new Intent(getActivity(), ListDetailsActivity.class);
                intent.putExtra("bean",listBean);
                intent.putExtra("flag",1000);
                startActivity(intent);
            }
        });

    }

    private void showSeckill(AdBean adBean) {
        AdBean.MiaoshaBean miaosha = adBean.getMiaosha();
        final List<AdBean.MiaoshaBean.ListBeanX> list = miaosha.getList();
        RvSecKillAdapter rvSecKillAdapter = new RvSecKillAdapter(list, getContext());
        rvSecKill.setAdapter(rvSecKillAdapter);
        //设置点击跳转监听
        rvSecKillAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String detailUrl = list.get(position).getDetailUrl();
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url",detailUrl);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });
    }

    //显示分类
    @Override
    public void showCatagory(CatagoryBean catagoryBean) {
        List<CatagoryBean.DataBean> data = catagoryBean.getData();
        RvClassAdapter rvClassAdapter = new RvClassAdapter(data, getContext());
        rvClass.setAdapter(rvClassAdapter);
    }


    //初始化跑马灯数据
    private void initMarqueeView() {
        ArrayList<String> info = new ArrayList<>();
        info.add("欢迎访问京东app");
        info.add("大家有没有在 听课");
        info.add("是不是还有人在睡觉");
        info.add("你妈妈在旁边看着呢");
        info.add("赶紧的好好学习吧 马上毕业了");
        info.add("你没有时间睡觉了");
        marqueeView.startWithList(info);
    }

}
