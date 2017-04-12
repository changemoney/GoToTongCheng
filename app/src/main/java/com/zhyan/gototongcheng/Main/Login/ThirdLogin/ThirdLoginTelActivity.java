package com.zhyan.gototongcheng.Main.Login.ThirdLogin;

import android.app.Activity;
import android.os.Bundle;

import com.zhyan.gototongcheng.R;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by admin on 2017/4/10.
 */

public class ThirdLoginTelActivity extends Activity{


    private ThirdLoginTelActivityController thirdLoginTelActivityController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_login_thirdlogin_lly);

        init();
    }
    private void init(){
        thirdLoginTelActivityController = new ThirdLoginTelActivityController(this);
    }
}
