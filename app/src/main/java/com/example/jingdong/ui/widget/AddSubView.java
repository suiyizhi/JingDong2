package com.example.jingdong.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jingdong.R;

public class AddSubView extends LinearLayout {
    private TextView tv_productSub;
    private TextView tv_productNum;
    private TextView tv_productAdd;

    public AddSubView(Context context) {
        this(context, null);
    }

    public AddSubView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.add_sub_view, this);
        tv_productSub=view.findViewById(R.id.tv_productSub);
        tv_productNum=view.findViewById(R.id.tv_productNum);
        tv_productAdd=view.findViewById(R.id.tv_productAdd);
    }

    public String getNum() {
        return tv_productNum.getText().toString();
    }

    public void setNum(String str) {
        tv_productNum.setText(str);
    }

    //给加号设置点击事件
    public void setAddOnclickListener(OnClickListener onclickListener){
        tv_productAdd.setOnClickListener(onclickListener);
    }

    //给减号设置点击事件
    public void setSubOnclickListener(OnClickListener onclickListener){
        tv_productSub.setOnClickListener(onclickListener);
    }
}
