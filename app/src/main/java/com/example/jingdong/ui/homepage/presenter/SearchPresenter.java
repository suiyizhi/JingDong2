package com.example.jingdong.ui.homepage.presenter;

import com.example.jingdong.bean.GoodsListBean;
import com.example.jingdong.net.NetApi;
import com.example.jingdong.ui.base.BasePresenter;
import com.example.jingdong.ui.homepage.contract.SearchContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    private NetApi netApi;
    @Inject
    public SearchPresenter(NetApi netApi) {
        this.netApi = netApi;
    }

    @Override
    public void search(String keyword) {
        netApi.search(keyword,"1")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GoodsListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GoodsListBean goodsListBean) {
                        mView.showSearch(goodsListBean);
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
