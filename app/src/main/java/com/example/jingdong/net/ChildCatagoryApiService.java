package com.example.jingdong.net;

import com.example.jingdong.bean.ChildCataBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ChildCatagoryApiService {

    @FormUrlEncoded
    @POST("product/getProductCatagory")
    Observable<ChildCataBean> getChildCatagory(@Field("cid") String cid);

}
