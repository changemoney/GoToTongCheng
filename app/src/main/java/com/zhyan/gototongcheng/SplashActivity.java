package com.zhyan.gototongcheng;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;

/**
 * http://www.open-open.com/lib/view/open1474962270182.html
 * Created by admin on 2017/3/23.
 * 过渡页
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_lly);
        init();
        // Example of a call to a native method

    }



    //Handler处理事物
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if(splashController != null) {
                        splashController.startMainActivity();
                    }
                    finish();
                default:
            }
        }
    };

    SplashController splashController;


     public void init(){
        ButterKnife.bind(this);

        splashController = new SplashController(this);

        splashController.isFirstLoad(handler);

    }


    protected void onPause(){
        super.onPause();
        finish();
    }
    protected void onDestroy(){
        super.onDestroy();
        if(null != handler) {
            handler = null;


        }
    }
}
