package com.example.jingdong.ui.shopcar.contract;

import com.example.jingdong.bean.BaseBean;
import com.example.jingdong.ui.base.BaseContract;

public interface SubmitOrderContract {

    interface View extends BaseContract.BaseView{
        void submitSuccess(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void submitOrder(String uid,String price);
    }

}
