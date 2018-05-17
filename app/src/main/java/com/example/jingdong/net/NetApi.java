package com.example.jingdong.net;

import com.example.jingdong.bean.GoodsListBean;

import io.reactivex.Observable;

public class NetApi {
    private static NetApi netApi;
    private NetApiService netApiService;

    private NetApi(NetApiService netApiService) {
        this.netApiService = netApiService;
    }

    public static NetApi getNetApi(NetApiService netApiService){
        if (netApi==null){
            netApi=new NetApi(netApiService);
        }
        return netApi;
    }
    //获取商品列表
    public Observable<GoodsListBean> getGoodsList(String pscid){
        return netApiService.getGoodsList(pscid);
    }

}
