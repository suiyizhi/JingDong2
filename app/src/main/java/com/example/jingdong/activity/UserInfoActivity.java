package com.example.jingdong.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.jingdong.R;
import com.example.jingdong.bean.BaseBean;
import com.example.jingdong.bean.UserInfoBean;
import com.example.jingdong.component.DaggerHttpComponent;
import com.example.jingdong.ui.base.BaseActivity;
import com.example.jingdong.ui.mine.contract.UploadHeaderContract;
import com.example.jingdong.ui.mine.presenter.UploadHeaderPresenter;
import com.example.jingdong.util.SpUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserInfoActivity extends BaseActivity<UploadHeaderPresenter> implements UploadHeaderContract.View, View.OnClickListener {

    private ImageView img_tou;
    private TextView tv_name;
    /**
     * 退出登录
     */
    private Button btn_tui;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private String imgPath;
    private File imgFile;
    private PopupWindow popupWindow;
    private LinearLayout ll2;
    private Bitmap photo;
    private String imgcropPath;
    private File imgcropFile;

    @Override
    public int getContentLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        imgPath = getExternalCacheDir() + File.separator + "header.jpg";
        imgcropPath = getExternalCacheDir() + File.separator + "header_crop.JPEG";
        imgFile = new File(imgPath);
        imgcropFile = new File(imgcropPath);

        String username = SpUtil.getString(this, "username", "");
        String icon = SpUtil.getString(this, "icon", "");

        tv_name.setText(username);
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(this).load(icon).apply(requestOptions).into(img_tou);
    }

    private void initView() {
        img_tou = (ImageView) findViewById(R.id.img_tou);
        tv_name = (TextView) findViewById(R.id.tv_name);
        btn_tui = (Button) findViewById(R.id.btn_tui);
        ll2 = findViewById(R.id.ll2);
        img_tou.setOnClickListener(this);
        btn_tui.setOnClickListener(this);
    }

    @Override
    public void inject() {
        DaggerHttpComponent.builder()
                .build()
                .inject(this);
    }

    //上传成功的回调
    @Override
    public void uploadSuccess(BaseBean baseBean) {
        if (baseBean==null){
            Toast.makeText(this, "错误", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, baseBean.getMsg(), Toast.LENGTH_SHORT).show();
        //上传头像后刷新用户信息
        mPresenter.getUserInfo(SpUtil.getString(UserInfoActivity.this,"uid",""));
    }

    //获取用户信息成功的回调
    @Override
    public void getUserInfoSuccess(UserInfoBean userInfoBean) {
        //先清空
        SpUtil.clearSp(UserInfoActivity.this);
        //登录成功后把主要信息存入sp
        SpUtil.saveString(UserInfoActivity.this,"uid",userInfoBean.getData().getUid()+"");
        SpUtil.saveString(UserInfoActivity.this,"username",userInfoBean.getData().getUsername());
        SpUtil.saveString(UserInfoActivity.this,"token",userInfoBean.getData().getToken());
        SpUtil.saveString(UserInfoActivity.this,"mobile",userInfoBean.getData().getMobile());
        SpUtil.saveString(UserInfoActivity.this,"icon",userInfoBean.getData().getIcon()+"");

        //重新设置本页的头像3
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        Glide.with(this).load(SpUtil.getString(UserInfoActivity.this,"icon","")).apply(requestOptions).into(img_tou);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tui:
                SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
                sp.edit().clear().commit();
                UserInfoActivity.this.finish();
                break;

            case R.id.img_tou:
                //在底部弹出PopupWindow
                initPopupWindow();
                popupWindow.showAtLocation(ll2, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_cancel:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.btn_take_photo:
                //拍照拍照
                takePhoto();
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.btn_pick_photo:
                takePictureFromAlum();
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
    }

    //使用隐式意图打开系统相册
    private void takePictureFromAlum() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        ComponentName componentName = intent.resolveActivity(getPackageManager());
//        if (componentName != null) {
//            startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
//        }

        Intent intent = new Intent();
        //指定选择/获取的动作...PICK获取,拿
        intent.setAction(Intent.ACTION_PICK);
        //指定获取的数据的类型
        intent.setType("image/*");

        startActivityForResult(intent,PHOTO_REQUEST_GALLERY);

    }


    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    //截取图片
                    startPhotoZoom(Uri.fromFile(imgFile));
                }
                break;

            case PHOTO_REQUEST_GALLERY:
                if (resultCode== Activity.RESULT_OK){
                    Uri data1 = data.getData();
                    startPhotoZoom(data1);
                }
                break;

            case PHOTO_REQUEST_CUT:
                //截图图片成功
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    photo = bundle.getParcelable("data");
                    //设置修改后的头像
//                    img_tou.setImageBitmap(photo);
                    try {
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imgcropFile));
                        photo.compress(Bitmap.CompressFormat.JPEG, 20, bos);
                        bos.flush();
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //上传头像
                    mPresenter.uploadHeaders(SpUtil.getString(UserInfoActivity.this, "uid", ""), imgcropPath);
                }
                break;
        }
    }


    private void initPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_item, null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        //点击PopupWindow外部可以取消
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f; //0.0-1.0
                getWindow().setAttributes(lp);
            }
        });

        Button btn_take_photo = view.findViewById(R.id.btn_take_photo);
        Button btn_pick_photo = view.findViewById(R.id.btn_pick_photo);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(this);
        btn_pick_photo.setOnClickListener(this);
        btn_take_photo.setOnClickListener(this);
    }

}
