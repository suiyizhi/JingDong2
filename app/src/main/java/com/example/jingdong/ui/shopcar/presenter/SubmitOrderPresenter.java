package com.example.jingdong.ui.shopcar.presenter;

import com.example.jingdong.bean.BaseBean;
import com.example.jingdong.net.NetApi;
import com.example.jingdong.ui.base.BasePresenter;
import com.example.jingdong.ui.shopcar.contract.SubmitOrderContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SubmitOrderPresenter extends BasePresenter<SubmitOrderContract.View> implements SubmitOrderContract.Presenter {

    private NetApi netApi;

    @Inject
    public SubmitOrderPresenter(NetApi netApi) {
        this.netApi = netApi;
    }

    @Override
    public void submitOrder(String uid, String price) {
        netApi.submitOrder(uid, price)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        mView.submitSuccess(baseBean);
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
