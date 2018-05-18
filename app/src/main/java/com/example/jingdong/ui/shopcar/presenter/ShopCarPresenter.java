package com.example.jingdong.ui.shopcar.presenter;

import com.example.jingdong.bean.BaseBean;
import com.example.jingdong.bean.SellerBean;
import com.example.jingdong.bean.ShopCarBean;
import com.example.jingdong.net.NetApi;
import com.example.jingdong.net.NetApiService;
import com.example.jingdong.ui.base.BasePresenter;
import com.example.jingdong.ui.shopcar.contract.ShopCarContract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ShopCarPresenter extends BasePresenter<ShopCarContract.View> implements ShopCarContract.Presenter{

    private NetApi netApi;
    @Inject
    public ShopCarPresenter(NetApi netApi) {
        this.netApi = netApi;
    }

    @Override
    public void getShopCar(String uid, String token) {
        netApi.getCar(uid, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ShopCarBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShopCarBean shopCarBean) {
                        if (mView!=null){
                            //用于封装一级列表
                            List<SellerBean> groupList = new ArrayList<>();
                            //用于封装二级列表
                            List<List<ShopCarBean.DataBean.ListBean>> childList = new ArrayList<>();
                            //获取数据
                            List<ShopCarBean.DataBean> data = shopCarBean.getData();
                            //循环获取数据
                            for (int i = 0; i < data.size(); i++) {
                                ShopCarBean.DataBean dataBean = data.get(i);
                                //添加父集合
                                SellerBean sellerBean = new SellerBean();
                                sellerBean.setSellerName(dataBean.getSellerName());
                                sellerBean.setSellerid(dataBean.getSellerid());
                                //设置是否全部选中
                                sellerBean.setSelected(isSellerProductAllSeleect(dataBean));
                                groupList.add(sellerBean);
                                //添加子集合
                                childList.add(data.get(i).getList());
                            }
                            //展示数据
                            mView.showShopCar(groupList,childList);
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

    @Override
    public void updateShopCar(String uid, String sellerid, String pid, String num, String selected, String token) {
        netApi.updateCar(uid, sellerid, pid, num, selected, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        mView.updateSuccess(baseBean);
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
    public void delShopCar(String uid, String pid, String token) {
        netApi.delCar(uid, pid, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        mView.delSuccess(baseBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //判断该商家下面的商品是否全选
    private boolean isSellerProductAllSeleect(ShopCarBean.DataBean dataBean){
        //获取该商家下的所有商品
        List<ShopCarBean.DataBean.ListBean> list = dataBean.getList();
        for (int i = 0; i < list.size(); i++) {
            ShopCarBean.DataBean.ListBean listBean = list.get(i);
            if (0==listBean.getSelected()){
                return false;
            }
        }
        return true;
    }
}
