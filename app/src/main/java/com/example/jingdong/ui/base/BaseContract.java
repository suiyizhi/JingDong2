package com.example.jingdong.ui.base;

public interface BaseContract {
    interface BaseView{
        void showLoading();
        void dismissLoading();
    }

    interface BasePresenter<T extends BaseView>{
        void attachView(T mView);
        void detachView();
    }

}
