package com.example.jingdong.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jingdong.R;
import com.example.jingdong.ui.shopcar.ShopCarFragment;

public class ShopCarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_car);
        ShopCarFragment shopCarFragment = new ShopCarFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,shopCarFragment).commit();

    }
}
