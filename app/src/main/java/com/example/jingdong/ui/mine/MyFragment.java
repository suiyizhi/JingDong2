package com.example.jingdong.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jingdong.R;
import com.example.jingdong.activity.LoginActivity;
import com.example.jingdong.activity.UserInfoActivity;
import com.example.jingdong.util.SpUtil;

public class MyFragment extends Fragment{

    private LinearLayout my_linear_login;
    private RelativeLayout login_back_pic;
    private ImageView my_user_icon;
    private TextView my_user_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_mine, null);
        my_linear_login = view.findViewById(R.id.my_linear_login);
        login_back_pic = view.findViewById(R.id.login_back_pic);
        my_user_icon = view.findViewById(R.id.my_user_icon);
        my_user_name = view.findViewById(R.id.my_user_name);
        //设置监听
        setListener();
        return view;
    }

    private void setListener() {
        my_linear_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = SpUtil.getString(getActivity(), "uid", "");
                if (TextUtils.isEmpty(uid)){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        String username = SpUtil.getString(getActivity(), "username", "");
        String uid = SpUtil.getString(getActivity(), "uid", "");
        String icon = SpUtil.getString(getActivity(), "icon", "");

        if (TextUtils.isEmpty(uid)){
            login_back_pic.setBackgroundResource(R.drawable.normal_regbg);
        }else {
            login_back_pic.setBackgroundResource(R.drawable.reg_bg);
        }

        if (!TextUtils.isEmpty(username)){
            my_user_name.setText(username);
        }else {
            my_user_name.setText("登录/注册 >");
        }

        if (!TextUtils.isEmpty(icon)){
            RequestOptions requestOptions = RequestOptions.circleCropTransform();
            Glide.with(this).load(icon).apply(requestOptions).into(my_user_icon);
        }else {
            my_user_icon.setImageResource(R.drawable.user);
        }
    }
}
