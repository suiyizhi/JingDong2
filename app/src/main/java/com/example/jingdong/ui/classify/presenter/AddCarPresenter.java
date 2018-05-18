package com.example.jingdong.ui.classify.presenter;

import com.example.jingdong.bean.BaseBean;
import com.example.jingdong.net.NetApi;
import com.example.jingdong.ui.base.BasePresenter;
import com.example.jingdong.ui.classify.contract.AddCarContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddCarPresenter extends BasePresenter<AddCarContract.View> implements AddCarContract.Presenter{

    private NetApi netApi;
    @Inject
    public AddCarPresenter(NetApi netApi) {
        this.netApi = netApi;
    }

    @Override
    public void addCar(String uid, String pid, String token) {
        netApi.addCar(uid, pid, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        mView.addSuccess(baseBean);
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
