package com.zhyan.gototongcheng.Main.Login.ThirdLogin;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zhyan.gototongcheng.Main.BaseController;
import com.zhyan.gototongcheng.Main.Login.ThirdLogin.ThirdLoginTelIdentify.ThirdLoginTelIdentifyActivity;
import com.zhyan.gototongcheng.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gototongcheng.zhyan.com.library.Common.XCCacheSavename;
import gototongcheng.zhyan.com.library.DBCache.XCCacheManager.xccache.XCCacheManager;
import gototongcheng.zhyan.com.library.Utils.PhoneFormatCheckUtils;

/**
 * Created by admin on 2017/4/10.
 */

public class ThirdLoginTelActivityController extends BaseController{


    @BindView(R.id.rly_main_login_thirdlogin_topbar_leftmenu_back)
    RelativeLayout rlyMainLoginThirdLoginTopBarLeftMenuBack;
    @OnClick(R.id.rly_main_login_thirdlogin_topbar_leftmenu_back)
    public void rlyMainLoginThirdLoginTopBarLeftMenuBackOnclick(){
        activity.finish();
    }

    @BindView(R.id.et_main_login_thirdlogin_content_tel)
    EditText etMainLoginThirdLoginContentTel;
    @BindView(R.id.rly_main_login_thirdlogin_content_nextstep)
    RelativeLayout rlyMainLoginThirdLoginContentNextStep;
    @OnClick(R.id.rly_main_login_thirdlogin_content_nextstep)
    public void rlyMainLoginThirdLoginContentNextStepOnclick(){
        getTelSubmitTelNextStep();
    }



    public ThirdLoginTelActivityController(Activity activity1){
        activity = activity1;
        init();
    }

    @Override
    public void init() {
        ButterKnife.bind(this,activity);
    }


    private void getTelSubmitTelNextStep(){
        String tel = etMainLoginThirdLoginContentTel.getText().toString();
        PhoneFormatCheckUtils phoneFormatCheckUtils = new PhoneFormatCheckUtils();
        if(!tel.isEmpty()&&(phoneFormatCheckUtils.isNumber(tel))&&(tel.length() == 11)){
            XCCacheManager xcCacheManager = XCCacheManager.getInstance(activity);
            XCCacheSavename xcCacheSavename = new XCCacheSavename();
            xcCacheManager.writeCache(xcCacheSavename.thirdLoginRegTel,tel);
            Intent intent = new Intent(activity, ThirdLoginTelIdentifyActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }else {
            Toast.makeText(activity,"请正确的输入手机号码",Toast.LENGTH_LONG).show();
        }
    }
}
