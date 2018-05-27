package com.example.jingdong.ui.shopcar.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jingdong.R;
import com.example.jingdong.bean.SellerBean;
import com.example.jingdong.bean.ShopCarBean;
import com.example.jingdong.ui.shopcar.presenter.ShopCarPresenter;
import com.example.jingdong.ui.widget.AddSubView;
import com.example.jingdong.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

public class ElvShopCarAdapter extends BaseExpandableListAdapter{

    private List<SellerBean> groupList;
    private List<List<ShopCarBean.DataBean.ListBean>> childList;
    private Context context;
    private ShopCarPresenter shopCarPresenter;
    private final LayoutInflater inflater;
    private ProgressDialog progressDialog;
    private int productIndex;
    private int groupPosition;
    private static final int GETCARS = 0;//查询购物车
    private static final int UPDATE_PRODUCT = 1;//更新商品
    private static final int UPDATE_SELLER = 2;//更新卖家
    private static int state = GETCARS;
    private final String uid;
    private final String token;
    private boolean checked;
    private boolean allChecked;

    public ElvShopCarAdapter(List<SellerBean> groupList, List<List<ShopCarBean.DataBean.ListBean>> childList, Context context, ShopCarPresenter shopCarPresenter, ProgressDialog progressDialog) {
        this.groupList = groupList;
        this.childList = childList;
        this.context = context;
        this.shopCarPresenter = shopCarPresenter;
        this.progressDialog = progressDialog;
        inflater=LayoutInflater.from(context);
        //获取uid和token
        uid = SpUtil.getString(context, "uid", "");
        token = SpUtil.getString(context, "token", "");
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.elv_shopcarseller_item, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        //设置是否全部选中
        groupViewHolder.ck_seller.setChecked(groupList.get(groupPosition).isSelected());
        //设置数据
        groupViewHolder.tv_sellername.setText(groupList.get(groupPosition).getSellerName());

        //给商家的CheckBox设置点击事件
        groupViewHolder.ck_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = UPDATE_PRODUCT;
                //显示进度条
                progressDialog.show();
                //默认从第一个商品开始更新购物车状态
                productIndex = 0;
                //全局记录一下当前更新的商家
                ElvShopCarAdapter.this.groupPosition = groupPosition;
                //该商家是否选中
                checked = groupViewHolder.ck_seller.isChecked();
                //更新商家下的商品状态
                updateProductInSeller();
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.elv_shopcarsellerpro_item, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        //获取单个子商品
        final ShopCarBean.DataBean.ListBean listBean = childList.get(groupPosition).get(childPosition);
        //根据服务器返回的select值，给CheckBox设置是否选中
        childViewHolder.ck_product.setChecked(listBean.getSelected() == 1 ? true : false);
        //设置数据
        childViewHolder.tv_producttitle.setText(listBean.getTitle());
        childViewHolder.tv_productprice.setText("￥" + listBean.getPrice() + "");
        Glide.with(context).load(listBean.getImages().split("\\|")[0]).into(childViewHolder.img_product);
        //设置数量
        childViewHolder.addsubcar.setNum(listBean.getNum() + "");
        //给子条目的CheckBox设置点击事件
        childViewHolder.ck_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                state = GETCARS;
                ElvShopCarAdapter.this.groupPosition = groupPosition;
                //获取sellerid
                String sellerid = groupList.get(groupPosition).getSellerid();
                //获取pid
                String pid = listBean.getPid() + "";
                String num = listBean.getNum() + "";
                //是否选中
                boolean childChecked = childViewHolder.ck_product.isChecked();
                //更新购物车
                shopCarPresenter.updateShopCar(uid, sellerid, pid, num, childChecked ? "1" : "0", token);
            }
        });

        //给加号设置点击事件
        childViewHolder.addsubcar.setAddOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                state = GETCARS;
                //获取sellerid
                String sellerid = groupList.get(groupPosition).getSellerid();
                //获取pid
                int pid = listBean.getPid();
                //获取数量
                int num = listBean.getNum();
                num++;
                //是否选中
                String isChecked = childViewHolder.ck_product.isChecked() ? "1" : "0";
                //调用更新购物车的接口
                shopCarPresenter.updateShopCar(uid, sellerid, pid + "", num + "", isChecked, token);
            }
        });

        //给减号设置点击事件
        childViewHolder.addsubcar.setSubOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                state = GETCARS;
                //获取当前商品的数量
                int num = listBean.getNum();
                if (num <= 1) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "数量不能小于1!", Toast.LENGTH_SHORT).show();
                } else {
                    num--;
                    //获取sellerid
                    String sellerid = groupList.get(groupPosition).getSellerid();
                    //获取pid
                    String pid = listBean.getPid() + "";
                    //是否选中
                    String isChecked = childViewHolder.ck_product.isChecked() ? "1" : "0";
                    //更新服务器上购物车状态
                    shopCarPresenter.updateShopCar(uid, sellerid, pid, num + "", isChecked, token);
                }
            }
        });

        //给删除设置点击事件
        childViewHolder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                state = GETCARS;
                //获取商品的pid
                int pid = listBean.getPid();
                //开始删除购物车里的选项
                shopCarPresenter.delShopCar(uid, pid + "", token);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    //父控件的viewholder
    public static class GroupViewHolder {
        public View rootView;
        public CheckBox ck_seller;
        public TextView tv_sellername;

        public GroupViewHolder(View rootView) {
            this.rootView = rootView;
            this.ck_seller = (CheckBox) rootView.findViewById(R.id.ck_seller);
            this.tv_sellername = (TextView) rootView.findViewById(R.id.tv_sellername);
        }
    }

    //子控件的viewholder
    public static class ChildViewHolder {
        public View rootView;
        public CheckBox ck_product;
        public TextView tv_del;
        public ImageView img_product;
        public TextView tv_producttitle;
        public TextView tv_productprice;
        public AddSubView addsubcar;

        public ChildViewHolder(View rootView) {
            this.rootView = rootView;
            this.ck_product = (CheckBox) rootView.findViewById(R.id.ck_product);
            this.tv_del = (TextView) rootView.findViewById(R.id.tv_del);
            this.img_product = (ImageView) rootView.findViewById(R.id.img_product);
            this.tv_producttitle = (TextView) rootView.findViewById(R.id.tv_producttitle);
            this.tv_productprice = (TextView) rootView.findViewById(R.id.tv_productprice);
            this.addsubcar = (AddSubView) rootView.findViewById(R.id.addsubcar);
        }
    }

    //更新购物车成功的回调
    public void updateSuccess() {
        switch (state) {
            case GETCARS:
                //更新成功后调用查询购物车接口
                productIndex = 0;
                groupPosition = 0;
                shopCarPresenter.getShopCar(uid, token);
                break;
            case UPDATE_PRODUCT:
                //更新成功一个商品以后，再接着更新该商家下的其他商品，直到没有商品为止
                productIndex++;
                //判断下标是否越界
                if (productIndex < childList.get(groupPosition).size()) {
                    //可以继续更新商品
                    updateProductInSeller();
                } else {
                    //商品已经全部更新完查询购物车
                    state = GETCARS;
                    updateSuccess();
                }
                break;
            case UPDATE_SELLER:
                //遍历所有商家下的商品，并更新状态
                productIndex++;
                //下标是否越界
                if (productIndex<childList.get(groupPosition).size()){
                    //可以继续更新商品
                    updateProductInSeller(allChecked);
                }else {
                    //第一个商家的商品已经全部更新完，请查询购物车
                    productIndex=0;
                    groupPosition++;
                    if (groupPosition<groupList.size()){
                        //可以继续更新商品
                        updateProductInSeller(allChecked);
                    }else {
                        //所有商家的所有商品已经全部更新完，查询购物车
                        state=GETCARS;
                        updateSuccess();
                    }
                }
                break;
        }
    }

    //删除购物车成功的回调
    public void delSuccess() {
        //查询购物车
        shopCarPresenter.getShopCar(uid, token);
    }

    //更新商家下的商品状态
    private void updateProductInSeller() {
        //获取sellerid
        SellerBean sellerBean = groupList.get(groupPosition);
        String sellerid = sellerBean.getSellerid();
        //获取pid
        ShopCarBean.DataBean.ListBean listBean = childList.get(groupPosition).get(productIndex);
        int pid = listBean.getPid();
        int num = listBean.getNum();
        //开始更新
        shopCarPresenter.updateShopCar(uid, sellerid, pid + "", num + "", checked ? "1" : "0", token);
    }

    private void updateProductInSeller(boolean bool) {
        //获取sellerid
        SellerBean sellerBean = groupList.get(groupPosition);
        String sellerid = sellerBean.getSellerid();
        //获取pid
        ShopCarBean.DataBean.ListBean listBean = childList.get(groupPosition).get(productIndex);
        int pid = listBean.getPid();
        int num = listBean.getNum();
        //开始更新
        shopCarPresenter.updateShopCar(uid, sellerid, pid + "", num + "", bool ? "1" : "0", token);
    }

    //计算数量和价钱
    public String[] computeMoneyAndNum() {
        double sum = 0;
        int num = 0;
        for (int i = 0; i < groupList.size(); i++) {
            for (int j = 0; j < childList.get(i).size(); j++) {
                //判断商品是否选中
                ShopCarBean.DataBean.ListBean listBean = childList.get(i).get(j);
                if (listBean.getSelected() == 1) {
                    //该商品为选中状态
                    sum += listBean.getPrice() * listBean.getNum();
                    num += listBean.getNum();
                }
            }
        }
        return new String[]{sum + "", num + ""};
    }

    //全选复选框更新状态
    public void changeAllState(boolean bool) {
        this.allChecked = bool;
        state = UPDATE_SELLER;
        //遍历商家下的商品修改状态
        updateProductInSeller(bool);
    }

    //获取选中的grouplist
    public List<SellerBean> getGroupList(){
        List<SellerBean> glist = new ArrayList<>();
        for (int i = 0; i < groupList.size(); i++) {
            for (int j = 0; j < childList.get(i).size(); j++) {
                if (childList.get(i).get(j).getSelected()==1){
                    if (!glist.contains(groupList.get(i))){
                        glist.add(groupList.get(i));
                    }
                }
            }
        }
        return glist;
    }

    //获取选中的子列表
    public List<List<ShopCarBean.DataBean.ListBean>> getChildList(){

        List<List<ShopCarBean.DataBean.ListBean>> clist = new ArrayList<>();
        for (int i = 0; i < groupList.size(); i++) {
            List<ShopCarBean.DataBean.ListBean> listBeans = new ArrayList<>();
            for (int j = 0; j < childList.get(i).size(); j++) {
                if (childList.get(i).get(j).getSelected()==1){
                    listBeans.add(childList.get(i).get(j));
                }
            }
            if (listBeans.size()>0){
                clist.add(listBeans);
            }
        }
        return clist;
    }

}
