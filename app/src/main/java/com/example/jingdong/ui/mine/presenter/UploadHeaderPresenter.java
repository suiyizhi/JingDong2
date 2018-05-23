package com.example.jingdong.ui.mine.presenter;

import com.example.jingdong.bean.BaseBean;
import com.example.jingdong.net.NetApi;
import com.example.jingdong.ui.base.BasePresenter;
import com.example.jingdong.ui.mine.contract.UploadHeaderContract;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadHeaderPresenter extends BasePresenter<UploadHeaderContract.View> implements UploadHeaderContract.Presenter{

    private NetApi netApi;
    @Inject
    public UploadHeaderPresenter(NetApi netApi) {
        this.netApi = netApi;
    }

    @Override
    public void uploadHeaders(String uid, String filePath) {

        int i = filePath.lastIndexOf("/");
        String fileName = filePath.substring(i + 1);
        RequestBody file = RequestBody.create(MediaType.parse("application/octet-stream"), new File(filePath));
        MediaType textType = MediaType.parse("text/plain");
        RequestBody u = RequestBody.create(textType, uid);
        MultipartBody.Part f = MultipartBody.Part.createFormData("file", fileName, file);
        netApi.uploadHeader(u,f)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        mView.uploadSuccess(baseBean);
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
