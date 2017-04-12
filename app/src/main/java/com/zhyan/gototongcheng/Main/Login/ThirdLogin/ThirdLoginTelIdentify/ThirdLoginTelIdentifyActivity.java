package com.zhyan.gototongcheng.Main.Login.ThirdLogin.ThirdLoginTelIdentify;

import android.app.Activity;
import android.os.Bundle;

import com.zhyan.gototongcheng.R;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by admin on 2017/4/10.
 */

public class ThirdLoginTelIdentifyActivity extends Activity {

    private ThirdLoginTelIdentifyActivityController thirdLoginTelIdentifyActivityController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_login_thirdlogin_tel_identify_lly);
        init();
    }
    private void init(){
        thirdLoginTelIdentifyActivityController = new ThirdLoginTelIdentifyActivityController(this);
    }
}
