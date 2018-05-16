package com.example.jingdong.ui.classify.contract;

import com.example.jingdong.bean.CatagoryBean;
import com.example.jingdong.bean.ChildCataBean;
import com.example.jingdong.ui.base.BaseContract;

public interface ClassifyContract {

    interface View extends BaseContract.BaseView{
        void showCatagory(CatagoryBean catagoryBean);
        void showChildCatagory(ChildCataBean childCataBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void getCatagory();
        void getChildCatagory(String cid);
    }

}
