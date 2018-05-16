package com.example.jingdong.ui.classify.presenter;

import com.example.jingdong.bean.CatagoryBean;
import com.example.jingdong.bean.ChildCataBean;
import com.example.jingdong.net.CatagoryApi;
import com.example.jingdong.net.ChildCatagoryApi;
import com.example.jingdong.ui.base.BasePresenter;
import com.example.jingdong.ui.classify.contract.ClassifyContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClassifyPresenter extends BasePresenter<ClassifyContract.View> implements ClassifyContract.Presenter {

    private CatagoryApi catagoryApi;
    private ChildCatagoryApi childCatagoryApi;
    @Inject
    public ClassifyPresenter(CatagoryApi catagoryApi, ChildCatagoryApi childCatagoryApi) {
        this.catagoryApi = catagoryApi;
        this.childCatagoryApi = childCatagoryApi;
    }

    @Override
    public void getCatagory() {
        catagoryApi.getCatagory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CatagoryBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CatagoryBean catagoryBean) {
                        mView.showCatagory(catagoryBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getChildCatagory(String cid) {
        childCatagoryApi.getChildCatagory(cid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ChildCataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ChildCataBean childCataBean) {
                        mView.showChildCatagory(childCataBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
