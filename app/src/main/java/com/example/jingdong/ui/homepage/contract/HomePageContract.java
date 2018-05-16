package com.example.jingdong.ui.homepage.contract;

import com.example.jingdong.bean.AdBean;
import com.example.jingdong.bean.CatagoryBean;
import com.example.jingdong.ui.base.BaseContract;

public interface HomePageContract {

    interface View extends BaseContract.BaseView{
        void showAd(AdBean adBean);
        void showCatagory(CatagoryBean catagoryBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void getAd();
        void getCatagory();
    }
}
