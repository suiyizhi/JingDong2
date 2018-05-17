package com.example.jingdong.app;

import android.app.Application;

import com.dash.zxinglibrary.activity.ZXingLibrary;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

public class MyApp extends Application {

    {
        PlatformConfig.setQQZone("1106791541", "4cjDBYUvtHJlkML0");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);

        UMConfigure.init(this, "5ae06696f29d9864d80000f6"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

    }
}
