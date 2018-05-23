package com.example.jingdong.ui.mine.contract;

import com.example.jingdong.bean.BaseBean;
import com.example.jingdong.ui.base.BaseContract;

public interface UploadHeaderContract {

    interface View extends BaseContract.BaseView{
        void uploadSuccess(BaseBean baseBean);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void uploadHeaders(String uid, String filePath);
    }

}
