package com.example.jingdong.bean;

import java.io.Serializable;
import java.util.List;

public class JieSuanBean implements Serializable {
    private List<SellerBean> gList;
    private List<List<ShopCarBean.DataBean.ListBean>> cList;
    private String sum;

    public JieSuanBean(List<SellerBean> gList, List<List<ShopCarBean.DataBean.ListBean>> cList, String sum) {
        this.gList = gList;
        this.cList = cList;
        this.sum = sum;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public List<SellerBean> getgList() {
        return gList;
    }

    public void setgList(List<SellerBean> gList) {
        this.gList = gList;
    }

    public List<List<ShopCarBean.DataBean.ListBean>> getcList() {
        return cList;
    }

    public void setcList(List<List<ShopCarBean.DataBean.ListBean>> cList) {
        this.cList = cList;
    }
}
