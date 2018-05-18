package com.example.jingdong.ui.classify.contract;

import com.example.jingdong.bean.BaseBean;
import com.example.jingdong.ui.base.BaseContract;

public interface AddCarContract {

    interface View extends BaseContract.BaseView{
        void addSuccess(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void addCar(String uid, String pid, String token);
    }

}
