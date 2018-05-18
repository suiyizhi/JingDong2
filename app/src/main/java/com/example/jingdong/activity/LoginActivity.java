package com.example.jingdong.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingdong.R;
import com.example.jingdong.bean.UserBean;
import com.example.jingdong.component.DaggerHttpComponent;
import com.example.jingdong.ui.base.BaseActivity;
import com.example.jingdong.ui.login.contract.LoginContract;
import com.example.jingdong.ui.login.presenter.LoginPresenter;
import com.example.jingdong.util.SpUtil;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    private RelativeLayout login_title_relative;
    /**
     * 请输入手机号
     */
    private EditText edit_phone;
    /**
     * 请输入密码
     */
    private EditText edit_pwd;
    /**
     * 手机快速注册
     */
    private TextView text_regist;
    private ImageView login_by_wechat;
    private ImageView login_by_qq;

    @Override
    public int getContentLayout() {
        return R.layout.activity_login;
    }

    private void initView() {
        login_title_relative = (RelativeLayout) findViewById(R.id.login_title_relative);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_pwd = (EditText) findViewById(R.id.edit_pwd);
        text_regist = (TextView) findViewById(R.id.text_regist);
        login_by_wechat = (ImageView) findViewById(R.id.login_by_wechat);
        login_by_qq = (ImageView) findViewById(R.id.login_by_qq);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .build()
                .inject(this);
    }

    //登录按钮的监听
    public void login(View view){
        String mobile = edit_phone.getText().toString().trim();
        String password = edit_pwd.getText().toString().trim();
        if (!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(password)){
            mPresenter.login(mobile,password);
        }else {
            Toast.makeText(this, "用户名或密码有误!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loginSuccess(UserBean userBean) {
        Toast.makeText(this, userBean.getMsg(), Toast.LENGTH_SHORT).show();
        //登录成功后把主要信息存入sp
        SpUtil.saveString(LoginActivity.this,"uid",userBean.getData().getUid()+"");
        SpUtil.saveString(LoginActivity.this,"username",userBean.getData().getUsername());
        SpUtil.saveString(LoginActivity.this,"token",userBean.getData().getToken());
        SpUtil.saveString(LoginActivity.this,"mobile",userBean.getData().getMobile());
        SpUtil.saveString(LoginActivity.this,"icon",userBean.getData().getIcon()+"");
        //关闭页面
        this.finish();
    }


}
