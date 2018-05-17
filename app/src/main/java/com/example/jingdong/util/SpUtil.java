package com.example.jingdong.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {
    public static String CONFIG = "config";//文件名
    public static SharedPreferences sp;
    //存布尔
    public static void saveBoolen(Context context,String key, boolean value){
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        //进行存值
        sp.edit().putBoolean(key, value).commit();
    }
    //取布尔
    public static boolean getBoolean(Context context,String key, boolean defValue){
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        //取值
        return sp.getBoolean(key, defValue);
    }
    //存字符串
    public static void saveString(Context context,String key, String value){
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        //进行存值
        sp.edit().putString(key, value).commit();
    }
    //取字符串
    public static String getString(Context context,String key, String defValue){
        if (sp == null) {
            sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        //取值
        return sp.getString(key, defValue);
    }
}
