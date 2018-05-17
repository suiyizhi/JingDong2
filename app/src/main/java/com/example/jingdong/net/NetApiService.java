package com.example.jingdong.net;

import com.example.jingdong.bean.GoodsListBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NetApiService {

    @FormUrlEncoded
    @POST("product/getProducts")
    Observable<GoodsListBean> getGoodsList(@Field("pscid") String pscid);

}
