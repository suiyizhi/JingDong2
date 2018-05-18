package com.example.jingdong.net;

import com.example.jingdong.bean.BaseBean;
import com.example.jingdong.bean.GoodsListBean;
import com.example.jingdong.bean.ShopCarBean;
import com.example.jingdong.bean.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NetApiService {

    //获取商品列表
    @FormUrlEncoded
    @POST("product/getProducts")
    Observable<GoodsListBean> getGoodsList(@Field("pscid") String pscid);

    //登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<UserBean> login(@Field("mobile") String mobile, @Field("password") String password);

    //添加购物车
    @FormUrlEncoded
    @POST("product/addCart")
    Observable<BaseBean> addCar(@Field("Uid") String uid, @Field("Pid") String pid, @Field("Token") String token);

    //查询购物车
    @FormUrlEncoded
    @POST("product/getCarts")
    Observable<ShopCarBean> getCar(@Field("uid") String uid, @Field("token") String token);

    //更新购物车
    @FormUrlEncoded
    @POST("product/updateCarts")
    Observable<BaseBean> updateCar(@Field("uid") String uid,
                                   @Field("sellerid") String sellerid,
                                   @Field("pid") String pid,
                                   @Field("num") String num,
                                   @Field("selected") String selected,
                                   @Field("token") String token);

    //删除购物车
    @FormUrlEncoded
    @POST("product/deleteCart")
    Observable<BaseBean> delCar(@Field("uid") String uid, @Field("pid") String pid, @Field("token") String token);
}
