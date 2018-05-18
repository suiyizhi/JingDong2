package com.example.jingdong.ui.shopcar.contract;

import com.example.jingdong.bean.BaseBean;
import com.example.jingdong.bean.SellerBean;
import com.example.jingdong.bean.ShopCarBean;
import com.example.jingdong.ui.base.BaseContract;

import java.util.List;

public interface ShopCarContract {
    interface View extends BaseContract.BaseView{
        void showShopCar(List<SellerBean> groupList, List<List<ShopCarBean.DataBean.ListBean>> childList);
        void updateSuccess(BaseBean baseBean);
        void delSuccess(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void getShopCar(String uid, String token);
        void updateShopCar(String uid, String sellerid, String pid, String num, String selected, String token);
        void delShopCar(String uid, String pid, String token);
    }

}
