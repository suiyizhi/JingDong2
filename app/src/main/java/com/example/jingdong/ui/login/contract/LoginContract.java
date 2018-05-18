package com.example.jingdong.ui.login.contract;

import com.example.jingdong.bean.UserBean;
import com.example.jingdong.ui.base.BaseContract;

public interface LoginContract {
    interface View extends BaseContract.BaseView{
        void loginSuccess(UserBean userBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void login(String mobile,String password);
    }

}
