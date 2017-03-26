package com.zhyan.gototongcheng.Application;

import android.app.Activity;
import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

/**
 *非常好用的插件http://blog.csdn.net/liang5630/article/details/46366901/
 */
public class MyApplication extends Application {

    public static  MyApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;
        initBaiDuSDK();
        /*极光推送*/
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
       /*极光推送*/

/*        *//*友盟第三方登录*//*
        ShareSDK.initSDK(this);
        *//*友盟第三方登录*/
    }



    public  MyApplication getInstance() {
        return instance;
    }

    public  MyApplication getContext() {
        instance = getContext();
        return instance;
    }
    /*百度地图定位begin*/
    private void initBaiDuSDK(){
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }
}

