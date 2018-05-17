package com.example.jingdong.ui.classify.presenter;

import com.example.jingdong.bean.GoodsListBean;
import com.example.jingdong.net.NetApi;
import com.example.jingdong.ui.base.BasePresenter;
import com.example.jingdong.ui.classify.contract.GoodsListContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GoodsListPresenter extends BasePresenter<GoodsListContract.View> implements GoodsListContract.Presenter {

    private NetApi netApi;
    @Inject
    public GoodsListPresenter(NetApi netApi) {
        this.netApi = netApi;
    }

    @Override
    public void getGoodsList(String pscid) {
        netApi.getGoodsList(pscid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GoodsListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GoodsListBean goodsListBean) {
                        if (mView!=null && goodsListBean!=null){
                            mView.showGoodsList(goodsListBean);
                        }
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
