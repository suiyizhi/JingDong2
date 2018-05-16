package com.example.jingdong.ui.base;

public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T>{

    protected T mView;

    @Override
    public void attachView(T mView) {
        this.mView=mView;
    }

    @Override
    public void detachView() {
        if (mView!=null){
            mView=null;
        }
    }
}
