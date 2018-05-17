package com.example.jingdong.ui.classify.contract;

import com.example.jingdong.bean.GoodsListBean;
import com.example.jingdong.ui.base.BaseContract;

public interface GoodsListContract {
    interface View extends BaseContract.BaseView{
        void showGoodsList(GoodsListBean goodsListBean);
    }
    interface Presenter extends BaseContract.BasePresenter<View>{
        void getGoodsList(String pscid);
    }
}
