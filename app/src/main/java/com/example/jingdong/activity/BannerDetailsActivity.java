package com.example.jingdong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.jingdong.R;
import com.example.jingdong.bean.GoodsListBean;
import com.example.jingdong.ui.classify.adapter.MyBannerImgAdapter;
import com.example.jingdong.ui.widget.HackyViewPager;

import java.util.Arrays;
import java.util.List;

public class BannerDetailsActivity extends AppCompatActivity {

    private int position;
    private HackyViewPager hvp;
    private TextView tv;
    private List<String> list;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_details);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        String images = intent.getStringExtra("images");
        String[] split = images.split("\\|");
        list = Arrays.asList(split);
        count = list.size();
        initView();
    }

    private void initView() {
        hvp = (HackyViewPager) findViewById(R.id.hvp);
        tv = (TextView) findViewById(R.id.tv);

        MyBannerImgAdapter myBannerImgAdapter = new MyBannerImgAdapter(list, this);

        hvp.setAdapter(myBannerImgAdapter);

        hvp.setCurrentItem(position);
        tv.setText((position+1)+"/"+count);

        hvp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BannerDetailsActivity.this.position=position;
                tv.setText((position+1)+"/"+count);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
