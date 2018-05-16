package com.example.jingdong.net;

import com.example.jingdong.bean.ChildCataBean;

import io.reactivex.Observable;

public class ChildCatagoryApi {

    private ChildCatagoryApiService childCatagoryApiService;
    private static ChildCatagoryApi childCatagoryApi;

    private ChildCatagoryApi(ChildCatagoryApiService childCatagoryApiService) {
        this.childCatagoryApiService = childCatagoryApiService;
    }

    public static ChildCatagoryApi getChildCatagoryApi(ChildCatagoryApiService childCatagoryApiService){
        if (childCatagoryApi==null){
            childCatagoryApi=new ChildCatagoryApi(childCatagoryApiService);
        }
        return childCatagoryApi;
    }

    public Observable<ChildCataBean> getChildCatagory(String cid){
        return childCatagoryApiService.getChildCatagory(cid);
    }

}
