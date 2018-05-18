package com.example.jingdong.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.example.jingdong.R;
import com.example.jingdong.ui.classify.ClassifyFragment;
import com.example.jingdong.ui.homepage.HomePageFragment;
import com.example.jingdong.ui.shopcar.ShopCarFragment;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rg_main;
    private HomePageFragment homePageFragment;
    private ClassifyFragment classifyFragment;
    private ShopCarFragment shopCarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        homePageFragment = new HomePageFragment();
        classifyFragment = new ClassifyFragment();
        shopCarFragment = new ShopCarFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,homePageFragment).commit();
        setListener();
    }

    private void setListener() {
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_homepage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,homePageFragment).commit();
                        break;
                    case R.id.rb_fenlei:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,classifyFragment).commit();
                        break;
                    case R.id.rb_shopcar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,shopCarFragment).commit();
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void initView() {
        rg_main = (RadioGroup) findViewById(R.id.rg_main);
    }
}
