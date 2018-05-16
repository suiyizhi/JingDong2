package com.example.jingdong.ui.homepage.presenter;

import com.example.jingdong.bean.AdBean;
import com.example.jingdong.bean.CatagoryBean;
import com.example.jingdong.net.AdApi;
import com.example.jingdong.net.CatagoryApi;
import com.example.jingdong.ui.base.BasePresenter;
import com.example.jingdong.ui.homepage.contract.HomePageContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePagePresenter extends BasePresenter<HomePageContract.View> implements HomePageContract.Presenter{

    private AdApi adApi;
    private CatagoryApi catagoryApi;
    @Inject
    public HomePagePresenter(AdApi adApi,CatagoryApi catagoryApi) {
        this.adApi = adApi;
        this.catagoryApi=catagoryApi;
    }

    @Override
    public void getAd() {
        adApi.getAd()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AdBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AdBean adBean) {
                        mView.showAd(adBean);
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
    public void getCatagory() {
        catagoryApi.getCatagory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
}
