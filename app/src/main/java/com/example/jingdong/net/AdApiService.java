package com.example.jingdong.net;

import com.example.jingdong.bean.AdBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface AdApiService {
    @GET("ad/getAd")
    Observable<AdBean> getAd();

}
