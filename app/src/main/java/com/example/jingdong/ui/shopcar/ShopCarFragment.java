package com.example.jingdong.ui.shopcar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.jingdong.R;
import com.example.jingdong.activity.ConfirmOrderActivity;
import com.example.jingdong.bean.BaseBean;
import com.example.jingdong.bean.JieSuanBean;
import com.example.jingdong.bean.SellerBean;
import com.example.jingdong.bean.ShopCarBean;
import com.example.jingdong.component.DaggerHttpComponent;
import com.example.jingdong.ui.base.BaseFragment;
import com.example.jingdong.ui.shopcar.adapter.ElvShopCarAdapter;
import com.example.jingdong.ui.shopcar.contract.ShopCarContract;
import com.example.jingdong.ui.shopcar.presenter.ShopCarPresenter;
import com.example.jingdong.util.DialogUtil;
import com.example.jingdong.util.SpUtil;

import java.util.List;

public class ShopCarFragment extends BaseFragment<ShopCarPresenter> implements ShopCarContract.View {

    private ExpandableListView elv_shopcar;
    /**
     * 全选
     */
    private CheckBox ck_all;
    /**
     * 合计：
     */
    private TextView tv_Money;
    /**
     * 去结算：
     */
    private TextView tv_Total;
    private ProgressDialog progressDialog;
    private ElvShopCarAdapter elvShopCarAdapter;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_shopcar;
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .build()
                .inject(this);
    }

    @Override
    public void initView(View view) {
        elv_shopcar = (ExpandableListView) view.findViewById(R.id.elv_shopcar);
        ck_all = (CheckBox) view.findViewById(R.id.ck_all);
        tv_Money = (TextView) view.findViewById(R.id.tv_Money);
        tv_Total = (TextView) view.findViewById(R.id.tv_Total);
        //初始化进度显示
        progressDialog = DialogUtil.getProgressDialog(getActivity());
        //获取uid和token值
        String uid = SpUtil.getString(getActivity(), "uid", "");
        String token = SpUtil.getString(getActivity(), "token", "");
        //获取数据
        if (!("").equals(uid) && !("").equals(token)){
            mPresenter.getShopCar(uid,token);
        }

        //点击全选按钮的监听
        ck_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (elvShopCarAdapter!=null){
                    progressDialog.show();
                    elvShopCarAdapter.changeAllState(ck_all.isChecked());
                }
            }
        });
        //点击去结算的监听
        tv_Total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings = elvShopCarAdapter.computeMoneyAndNum();
                List<SellerBean> gList = elvShopCarAdapter.getGroupList();
                List<List<ShopCarBean.DataBean.ListBean>> cList = elvShopCarAdapter.getChildList();
                JieSuanBean jieSuanBean = new JieSuanBean(gList, cList,strings[0]);
                Intent intent = new Intent(getActivity(), ConfirmOrderActivity.class);
                intent.putExtra("bean",jieSuanBean);
                startActivity(intent);
                Log.i("===", "onClick: 123456");
            }
        });
    }

    @Override
    public void showShopCar(List<SellerBean> groupList, List<List<ShopCarBean.DataBean.ListBean>> childList) {
        //判断所有商家是否全部选中
        ck_all.setChecked(isSellerAllSelected(groupList));
        //设置适配器
        elvShopCarAdapter = new ElvShopCarAdapter(groupList, childList, getActivity(), mPresenter, progressDialog);
        elv_shopcar.setAdapter(elvShopCarAdapter);

        //获取商品总价和数量
        String[] strings = elvShopCarAdapter.computeMoneyAndNum();
        tv_Money.setText("总计:" + strings[0] + "元");
        tv_Total.setText("去结算" + strings[1] + "个");

        //默认全部展开
        for (int i = 0; i < groupList.size(); i++) {
            elv_shopcar.expandGroup(i);
        }

        //关闭进度条
        progressDialog.dismiss();
    }

    @Override
    public void updateSuccess(BaseBean baseBean) {
        elvShopCarAdapter.updateSuccess();
    }

    @Override
    public void delSuccess(BaseBean baseBean) {
        elvShopCarAdapter.delSuccess();
    }


    private boolean isSellerAllSelected(List<SellerBean> groupList){
        for (int i = 0; i < groupList.size(); i++) {
            SellerBean sellerBean = groupList.get(i);
            if (!sellerBean.isSelected()){
                return false;
            }
        }
        return true;
    }



}
