package com.example.jingdong.net;

import com.example.jingdong.bean.BaseBean;
import com.example.jingdong.bean.GoodsListBean;
import com.example.jingdong.bean.ShopCarBean;
import com.example.jingdong.bean.UserBean;
import com.example.jingdong.bean.UserInfoBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.Part;

public class NetApi {
    private static NetApi netApi;
    private NetApiService netApiService;

    private NetApi(NetApiService netApiService) {
        this.netApiService = netApiService;
    }

    public static NetApi getNetApi(NetApiService netApiService) {
        if (netApi == null) {
            netApi = new NetApi(netApiService);
        }
        return netApi;
    }

    //获取商品列表
    public Observable<GoodsListBean> getGoodsList(String pscid) {
        return netApiService.getGoodsList(pscid);
    }

    //实现登录
    public Observable<UserBean> login(String mobile, String password) {
        return netApiService.login(mobile, password);
    }

    //添加购物车
    public Observable<BaseBean> addCar(String uid, String pid, String token) {
        return netApiService.addCar(uid, pid, token);
    }

    //查询购物车
    public Observable<ShopCarBean> getCar(String uid, String token) {
        return netApiService.getCar(uid, token);
    }

    //更新购物车
    public Observable<BaseBean> updateCar(String uid, String sellerid, String pid, String num, String selected, String token) {
        return netApiService.updateCar(uid, sellerid, pid, num, selected, token);
    }

    //删除购物车
    public Observable<BaseBean> delCar(String uid, String pid, String token) {
        return netApiService.delCar(uid, pid, token);
    }

    //首页搜索
    public Observable<GoodsListBean> search(String keywords, String page) {
        return netApiService.search(keywords, page);
    }

    //上传头像
    public Observable<BaseBean> uploadHeader(RequestBody uid, MultipartBody.Part file) {
        return netApiService.uploadHeader(uid, file);
    }

    //获取用户信息
    public Observable<UserInfoBean> getUserInfo(String uid){
        return netApiService.getUserInfo(uid);
    }

}
