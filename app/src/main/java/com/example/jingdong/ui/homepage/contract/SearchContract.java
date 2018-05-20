package com.example.jingdong.ui.homepage.contract;

import com.example.jingdong.bean.GoodsListBean;
import com.example.jingdong.ui.base.BaseContract;

public interface SearchContract {

    interface View extends BaseContract.BaseView{
        void showSearch(GoodsListBean goodsListBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void search(String keyword);
    }

}
