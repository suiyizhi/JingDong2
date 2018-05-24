package com.example.jingdong.ui.mine.contract;

import com.example.jingdong.bean.BaseBean;
import com.example.jingdong.bean.UserInfoBean;
import com.example.jingdong.ui.base.BaseContract;

public interface UploadHeaderContract {

    interface View extends BaseContract.BaseView {
        void uploadSuccess(BaseBean baseBean);
        void getUserInfoSuccess(UserInfoBean userInfoBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void uploadHeaders(String uid, String filePath);
        void getUserInfo(String uid);
    }

}
