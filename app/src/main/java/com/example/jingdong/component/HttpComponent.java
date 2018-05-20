package com.example.jingdong.component;

import com.example.jingdong.activity.GoodsListActivity;
import com.example.jingdong.activity.ListDetailsActivity;
import com.example.jingdong.activity.LoginActivity;
import com.example.jingdong.activity.SearchListActivity;
import com.example.jingdong.module.HttpModule;
import com.example.jingdong.ui.classify.ClassifyFragment;
import com.example.jingdong.ui.homepage.HomePageFragment;
import com.example.jingdong.ui.shopcar.ShopCarFragment;

import dagger.Component;

@Component(modules = HttpModule.class)
public interface HttpComponent {

    void inject(HomePageFragment homePageFragment);

    void inject(ClassifyFragment classifyFragment);

    void inject(GoodsListActivity goodsListActivity);

    void inject(LoginActivity loginActivity);

    void inject(ListDetailsActivity listDetailsActivity);

    void inject(ShopCarFragment shopCarFragment);

    void inject(SearchListActivity searchListActivity);

}
